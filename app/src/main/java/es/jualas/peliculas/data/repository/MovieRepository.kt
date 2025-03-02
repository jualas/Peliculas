package es.jualas.peliculas.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import es.jualas.peliculas.data.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.tasks.await

/**
 * Repositorio que gestiona las operaciones relacionadas con películas en Firebase Firestore.
 * 
 * Esta clase proporciona métodos para obtener, buscar y gestionar películas y favoritos
 * almacenados en la base de datos de Firestore.
 */
class MovieRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val moviesCollection = firestore.collection("movies")
    private val userFavoritesCollection = firestore.collection("user_favorites")

    /**
     * Obtiene todas las películas disponibles en la base de datos.
     *
     * @return [Result] que contiene una lista de objetos [Movie] en caso de éxito,
     *         o una excepción en caso de error.
     */
    suspend fun getMovies(): Result<List<Movie>> {
        return try {
            val snapshot = moviesCollection.get().await()
            val movies = snapshot.documents.mapNotNull { 
                it.toObject(Movie::class.java)?.copy(id = it.id) 
            }
            Result.success(movies)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Obtiene una película específica por su ID.
     *
     * @param movieId ID único de la película a buscar.
     * @return [Result] que contiene el objeto [Movie] en caso de éxito,
     *         o una excepción en caso de error o si la película no existe.
     */
    suspend fun getMovieById(movieId: String): Result<Movie> = withContext(Dispatchers.IO) {
        try {
            val movieDocument = firestore.collection("movies").document(movieId).get().await()
            
            if (movieDocument.exists()) {
                val movie = movieDocument.toObject(Movie::class.java)
                    ?: return@withContext Result.failure(Exception("Error al convertir documento"))
                
                // Usar copy para establecer el ID ya que es inmutable (val)
                val movieWithId = movie.copy(id = movieId)
                
                Result.success(movieWithId)
            } else {
                Result.failure(Exception("La película no existe"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Obtiene todas las películas marcadas como favoritas por un usuario específico.
     *
     * @param userId ID único del usuario cuyos favoritos se quieren obtener.
     * @return [Result] que contiene una lista de objetos [Movie] marcados como favoritos,
     *         o una lista vacía si no hay favoritos, o una excepción en caso de error.
     */
    suspend fun getFavoriteMovies(userId: String): Result<List<Movie>> {
        return try {
            // Obtener el documento de favoritos del usuario
            val userFavoritesDoc = userFavoritesCollection.document(userId).get().await()
            
            // Si el documento no existe, devolver lista vacía
            if (!userFavoritesDoc.exists()) {
                return Result.success(emptyList())
            }
            
            // Extraer los IDs de películas favoritas (donde el valor es true)
            val favoriteIds = userFavoritesDoc.data
                ?.filter { (_, value) -> value == true }
                ?.keys ?: emptyList()
            
            if (favoriteIds.isEmpty()) {
                return Result.success(emptyList())
            }
            
            // Obtener todas las películas favoritas
            // Firestore tiene un límite de 10 elementos en una consulta 'in',
            // así que dividimos la consulta si es necesario
            val favoriteMovies = mutableListOf<Movie>()
            favoriteIds.chunked(10).forEach { chunk ->
                val moviesSnapshot = moviesCollection.whereIn("id", chunk).get().await()
                val movies = moviesSnapshot.documents.mapNotNull { 
                    it.toObject(Movie::class.java)?.copy(id = it.id, isFavorite = true) 
                }
                favoriteMovies.addAll(movies)
            }
            
            Result.success(favoriteMovies)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Añade o elimina una película de los favoritos de un usuario.
     *
     * @param userId ID único del usuario.
     * @param movieId ID único de la película a marcar/desmarcar como favorita.
     * @param isFavorite `true` para marcar como favorita, `false` para desmarcar.
     * @return [Result] que contiene un booleano indicando el nuevo estado de favorito,
     *         o una excepción en caso de error.
     */
    suspend fun toggleFavorite(userId: String, movieId: String, isFavorite: Boolean): Result<Boolean> {
        return try {
            val userFavoritesRef = userFavoritesCollection.document(userId)
            
            // Verificar si el documento existe y crearlo si no
            val userDoc = userFavoritesRef.get().await()
            if (!userDoc.exists()) {
                userFavoritesRef.set(mapOf<String, Any>()).await()
            }
            
            // Actualizar el campo específico para la película
            if (isFavorite) {
                userFavoritesRef.update(movieId, true).await()
            } else {
                userFavoritesRef.update(movieId, false).await()
                // Alternativa: eliminar el campo
                // userFavoritesRef.update(movieId, FieldValue.delete()).await()
            }
            
            Result.success(isFavorite)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Busca películas que coincidan con un término de búsqueda en el título o descripción.
     *
     * Este método realiza una búsqueda insensible a mayúsculas/minúsculas en los campos
     * de título y descripción de las películas.
     *
     * @param query Término de búsqueda.
     * @return Lista de objetos [Movie] que coinciden con la búsqueda.
     * @throws Exception Si ocurre un error durante la búsqueda.
     */
    suspend fun searchMovies(query: String): List<Movie> {
        return try {
            // Normalizar la consulta para búsqueda insensible a mayúsculas/minúsculas
            val normalizedQuery = query.lowercase().trim()

            // Buscar en Firestore usando consultas compuestas
            val snapshot = firestore.collection("movies")
                .whereGreaterThanOrEqualTo("title", normalizedQuery)
                .whereLessThanOrEqualTo("title", normalizedQuery + '\uf8ff')
                .get()
                .await()

            // Mapear los resultados
            val titleMatches = snapshot.documents.mapNotNull { document ->
                document.toObject(Movie::class.java)?.copy(id = document.id)
            }

            // También buscar en descripción (esto requiere descargar más datos)
            val descSnapshot = firestore.collection("movies")
                .get()
                .await()

            val descriptionMatches = descSnapshot.documents.mapNotNull { document ->
                val movie = document.toObject(Movie::class.java)
                val movieWithId = movie?.copy(id = document.id)

                if (movieWithId != null &&
                    movieWithId.description?.lowercase()?.contains(normalizedQuery) == true &&
                    !titleMatches.any { it.id == movieWithId.id }
                ) {
                    movieWithId
                } else {
                    null
                }
            }

            // Combinar resultados
            titleMatches + descriptionMatches
        } catch (e: Exception) {
            throw Exception("Error al buscar películas: ${e.message}")
        }
    }

    /**
     * Inicializa la base de datos con una colección predefinida de películas populares.
     *
     * Este método crea un conjunto inicial de datos para la aplicación, insertando varias
     * películas conocidas en la colección "movies" de Firestore. Utiliza operaciones por lotes
     * para realizar todas las inserciones en una sola transacción, mejorando la eficiencia
     * y garantizando la consistencia de los datos.
     *
     * Las películas incluidas son clásicos y éxitos de taquilla como Inception, The Dark Knight,
     * Interstellar, The Matrix, entre otras. Cada película se inicializa con su información básica
     * como título, descripción, URL de imagen, año de lanzamiento y calificación.
     *
     * Este método es útil para:
     * - Configuración inicial de la aplicación
     * - Restauración de datos predeterminados
     * - Pruebas y demostraciones
     *
     * @return [Result] que contiene un booleano `true` si la inicialización fue exitosa,
     *         o una excepción en caso de error durante la operación de escritura en Firestore.
     */
    
    suspend fun initializeMoviesDatabase(): Result<Boolean> {
        return try {
            val moviesList = listOf(
                Movie(
                    id = "inception",
                    title = "Inception",
                    description = "Un ladrón que roba secretos corporativos a través del uso de la tecnología de compartir sueños, se le da la tarea inversa de plantar una idea en la mente de un CEO.",
                    imageUrl = "https://image.tmdb.org/t/p/original/qmDpIHrmpJINaRKAfWQfftjCdyi.jpg",
                    releaseYear = 2010,
                    rating = 8.8f,
                    isFavorite = false
                ),
                Movie(
                    id = "the_dark_knight",
                    title = "The Dark Knight",
                    description = "Batman se enfrenta a la amenaza más grande que ha enfrentado Gotham hasta ahora: el Joker, un criminal que busca sumir la ciudad en el caos.",
                    imageUrl = "https://image.tmdb.org/t/p/original/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
                    releaseYear = 2008,
                    rating = 9.0f,
                    isFavorite = false
                ),
                Movie(
                    id = "interstellar",
                    title = "Interstellar",
                    description = "Un equipo de exploradores viaja a través de un agujero de gusano en el espacio en un intento de asegurar la supervivencia de la humanidad.",
                    imageUrl = "https://image.tmdb.org/t/p/original/rAiYTfKGqDCRIIqo664sY9XZIvQ.jpg",
                    releaseYear = 2014,
                    rating = 8.6f,
                    isFavorite = false
                ),
                Movie(
                    id = "the_matrix",
                    title = "The Matrix",
                    description = "Un hacker informático aprende de misteriosos rebeldes sobre la verdadera naturaleza de su realidad y su papel en la guerra contra sus controladores.",
                    imageUrl = "https://image.tmdb.org/t/p/original/f89U3ADr1oiB1s9GkdPOEpXUk5H.jpg",
                    releaseYear = 1999,
                    rating = 8.7f,
                    isFavorite = false
                ),
                Movie(
                    id = "pulp_fiction",
                    title = "Pulp Fiction",
                    description = "Las vidas de dos sicarios, un boxeador, la esposa de un gángster y un par de bandidos se entrelazan en cuatro historias de violencia y redención.",
                    imageUrl = "https://image.tmdb.org/t/p/original/tptjnB6XQW7hk2Q3mAkPmVnHjIv.jpg",
                    releaseYear = 1994,
                    rating = 8.9f,
                    isFavorite = false
                ),
                Movie(
                    id = "the_shawshank_redemption",
                    title = "The Shawshank Redemption",
                    description = "Un banquero es condenado a cadena perpetua por el asesinato de su esposa y su amante, a pesar de sus afirmaciones de inocencia.",
                    imageUrl = "https://image.tmdb.org/t/p/original/q6y0Go1tsGEsmtFryDOJo3dEmqu.jpg",
                    releaseYear = 1994,
                    rating = 9.3f,
                    isFavorite = false
                ),
                Movie(
                    id = "forrest_gump",
                    title = "Forrest Gump",
                    description = "Las presidencias de Kennedy y Johnson, la guerra de Vietnam, el escándalo Watergate y otros eventos históricos se desarrollan desde la perspectiva de un hombre de Alabama con un coeficiente intelectual de 75.",
                    imageUrl = "https://image.tmdb.org/t/p/original/arw2vcBveWOVZr6pxd9XTd1TdQa.jpg",
                    releaseYear = 1994,
                    rating = 8.8f,
                    isFavorite = false
                ),
                Movie(
                    id = "fight_club",
                    title = "Fight Club",
                    description = "Un oficinista insomne y un fabricante de jabón despreocupado forman un club de lucha clandestino que evoluciona en algo mucho más peligroso.",
                    imageUrl = "https://image.tmdb.org/t/p/original/bptfVGEQuv6vDTIMVCHjJ9Dz8PX.jpg",
                    releaseYear = 1999,
                    rating = 8.8f,
                    isFavorite = false
                ),
                Movie(
                    id = "the_godfather",
                    title = "The Godfather",
                    description = "El patriarca envejecido de una dinastía del crimen organizado transfiere el control de su imperio clandestino a su reacio hijo.",
                    imageUrl = "https://image.tmdb.org/t/p/original/3bhkrj58Vtu7enYsRolD1fZdja1.jpg",
                    releaseYear = 1972,
                    rating = 9.2f,
                    isFavorite = false
                ),
                Movie(
                    id = "gladiator",
                    title = "Gladiator",
                    description = "Un general romano traicionado se convierte en gladiador y busca venganza contra el emperador corrupto que asesinó a su familia y lo envió a la esclavitud.",
                    imageUrl = "https://image.tmdb.org/t/p/original/ty8TGRuvJLPUmAR1H1nRIsgwvim.jpg",
                    releaseYear = 2000,
                    rating = 8.5f,
                    isFavorite = false
                )
            )

            // Crear un lote para insertar todas las películas de una vez
            val batch = firestore.batch()
            moviesList.forEach { movie ->
                val docRef = moviesCollection.document(movie.id)
                batch.set(docRef, movie)
            }
            batch.commit().await()

            Result.success(true)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}