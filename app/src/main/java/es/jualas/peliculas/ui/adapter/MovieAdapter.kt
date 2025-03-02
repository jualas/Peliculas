package es.jualas.peliculas.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import es.jualas.peliculas.R
import es.jualas.peliculas.data.model.Movie
import es.jualas.peliculas.databinding.ItemMovieBinding

/**
 * Adaptador para mostrar una lista de películas en un RecyclerView.
 *
 * Este adaptador utiliza el patrón ListAdapter para manejar eficientemente los cambios
 * en la lista de películas mediante DiffUtil, optimizando las actualizaciones de la UI.
 * Permite interacciones como seleccionar una película o marcarla como favorita.
 *
 * @property onMovieClick Función lambda que se ejecuta cuando el usuario hace clic en una película
 * @property onFavoriteClick Función lambda que se ejecuta cuando el usuario marca/desmarca una película como favorita
 */
class MovieAdapter(
    private val onMovieClick: (Movie) -> Unit,
    private val onFavoriteClick: (Movie) -> Unit
) : ListAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieDiffCallback()) {

    /**
     * Crea y devuelve un nuevo ViewHolder para representar un elemento de la lista.
     *
     * @param parent El ViewGroup en el que se añadirá la nueva vista
     * @param viewType El tipo de vista del nuevo elemento
     * @return Un nuevo MovieViewHolder que contiene la vista del elemento
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    /**
     * Actualiza el contenido de un ViewHolder para mostrar el elemento en la posición dada.
     *
     * @param holder El ViewHolder que debe actualizarse
     * @param position La posición del elemento en el conjunto de datos
     */
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    /**
     * ViewHolder que contiene y gestiona la vista de un elemento de película individual.
     *
     * Maneja la vinculación de datos de la película a los elementos de la UI
     * y configura los listeners para las interacciones del usuario.
     *
     * @property binding El objeto de vinculación que contiene referencias a las vistas del elemento
     */
    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Inicializa los listeners para las interacciones del usuario con el elemento.
         */
        init {
            /**
             * Configura el listener para cuando el usuario hace clic en el elemento completo.
             * Invoca el callback onMovieClick con la película seleccionada.
             */
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onMovieClick(getItem(position))
                }
            }

            /**
             * Configura el listener para cuando el usuario hace clic en el botón de favorito.
             * Invoca el callback onFavoriteClick con la película seleccionada.
             */
            binding.ivFavorite.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onFavoriteClick(getItem(position))
                }
            }
        }

        /**
         * Vincula los datos de una película a los elementos de la UI.
         *
         * Actualiza todos los elementos visuales del ViewHolder con la información
         * de la película proporcionada, incluyendo título, año, puntuación, imagen
         * y estado de favorito.
         *
         * @param movie La película cuyos datos se mostrarán
         */
        fun bind(movie: Movie) {
            binding.apply {
                // Establecer el título de la película
                tvTitle.text = movie.title
                
                // Establecer el año de lanzamiento
                tvYear.text = movie.releaseYear.toString()
                
                // Establecer el género (vacío en este caso)
                tvGenre.text = "" // Ajustar según necesidad

                // Establecer la puntuación (convertir de escala 10 a escala 5)
                val ratingOutOf5 = movie.rating / 2
                ratingBar.rating = ratingOutOf5
                
                // Mostrar el texto de puntuación
                tvRating.text = String.format("%.1f/5.0", ratingOutOf5)

                // Cargar la imagen del póster usando Glide
                Glide.with(ivPoster.context)
                    .load(movie.imageUrl)
                    .placeholder(R.drawable.placeholder_image) // Imagen de carga
                    .error(R.drawable.error_image) // Imagen de error
                    .centerCrop()
                    .into(ivPoster)

                // Establecer el icono de favorito según el estado de la película
                val favoriteIcon = if (movie.isFavorite) {
                    R.drawable.ic_favorite
                } else {
                    R.drawable.ic_favorite_border
                }
                ivFavorite.setImageResource(favoriteIcon)
            }
        }
    }

    /**
     * Implementación de DiffUtil.ItemCallback para calcular las diferencias entre listas
     * y optimizar las actualizaciones del RecyclerView.
     *
     * Permite que el ListAdapter determine qué elementos han cambiado, se han añadido
     * o se han eliminado, minimizando las operaciones de actualización de la UI.
     */
    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        /**
         * Determina si dos objetos representan el mismo elemento.
         *
         * @param oldItem El elemento en la lista antigua
         * @param newItem El elemento en la lista nueva
         * @return true si los elementos representan el mismo objeto basándose en su ID
         */
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        /**
         * Comprueba si dos elementos tienen el mismo contenido.
         *
         * @param oldItem El elemento en la lista antigua
         * @param newItem El elemento en la lista nueva
         * @return true si los elementos tienen exactamente el mismo contenido
         */
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }
}