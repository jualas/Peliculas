package es.jualas.peliculas.data.model

/**
 * Modelo de datos que representa una película en la aplicación.
 *
 * Esta clase contiene toda la información relevante sobre una película,
 * incluyendo sus metadatos básicos como título, descripción, año de lanzamiento,
 * así como información relacionada con la interfaz de usuario como la URL de la imagen
 * y el estado de favorito para el usuario actual.
 *
 * @property id Identificador único de la película.
 * @property title Título de la película.
 * @property description Sinopsis o descripción de la trama de la película.
 * @property imageUrl URL de la imagen del póster o carátula de la película.
 * @property releaseYear Año de lanzamiento de la película.
 * @property rating Puntuación de la película en una escala de 0 a 10.
 * @property isFavorite Indica si la película está marcada como favorita por el usuario actual.
 */
data class Movie(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val releaseYear: Int = 0,
    val rating: Float = 0f,
    val isFavorite: Boolean = false
)