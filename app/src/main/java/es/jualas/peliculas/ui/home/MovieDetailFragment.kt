package es.jualas.peliculas.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import es.jualas.peliculas.databinding.FragmentMovieDetailBinding
import es.jualas.peliculas.viewmodel.MovieViewModel
import android.widget.Toast

/**
 * Fragmento que muestra los detalles completos de una película seleccionada.
 * 
 * Este fragmento recibe el ID de una película a través de argumentos o del ViewModel compartido
 * y muestra información detallada como título, descripción, año de lanzamiento, calificación
 * e imagen de portada. También indica si la película está marcada como favorita.
 */
class MovieDetailFragment : Fragment() {

    /**
     * Binding para acceder a las vistas del layout del fragmento.
     * Se inicializa en [onCreateView] y se limpia en [onDestroyView].
     */
    private var _binding: FragmentMovieDetailBinding? = null
    
    /**
     * Propiedad delegada que proporciona acceso seguro al binding.
     * Lanza una excepción si se accede después de que [_binding] se ha establecido a null.
     */
    private val binding get() = _binding!!
    
    /**
     * ViewModel compartido que contiene la lógica de negocio y datos relacionados con las películas.
     * Se comparte con otros fragmentos para mantener el estado consistente en toda la aplicación.
     */
    private val movieViewModel: MovieViewModel by activityViewModels()

    /**
     * Crea y configura la vista del fragmento.
     *
     * @param inflater El inflador de layout utilizado para inflar la vista.
     * @param container El contenedor padre donde se adjuntará la vista.
     * @param savedInstanceState Estado guardado del fragmento, si existe.
     * @return La vista raíz del fragmento.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Llamado después de que la vista ha sido creada.
     * Configura los observadores para los LiveData y carga los detalles de la película.
     *
     * @param view La vista raíz del fragmento.
     * @param savedInstanceState Estado guardado del fragmento, si existe.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        /**
         * Obtiene el ID de la película desde los argumentos del fragmento o desde el ViewModel.
         * Prioriza los argumentos sobre el valor almacenado en el ViewModel.
         */
        val movieId = arguments?.getString("movieId") ?: movieViewModel.selectedMovieId.value
        
        // Verificar que tenemos un ID válido
        if (!movieId.isNullOrEmpty()) {
            // Cargar los detalles de la película usando el ID
            movieViewModel.getMovieById(movieId)
        } else {
            Toast.makeText(context, "No se pudo obtener el ID de la película", Toast.LENGTH_SHORT).show()
        }
        
        /**
         * Observa cambios en la película seleccionada y actualiza la interfaz de usuario
         * cuando se reciben nuevos datos.
         */
        movieViewModel.selectedMovie.observe(viewLifecycleOwner) { movie ->
            movie?.let {
                // Actualizar la UI con los detalles de la película
                binding.apply {
                    // Actualiza estos campos según tu layout real
                    movieTitle.text = it.title
                    movieDescription.text = it.description
                    movieReleaseYear.text = it.releaseYear.toString()
                    movieRating.text = String.format("%.1f/10", it.rating)
                    
                    /**
                     * Muestra u oculta el indicador de favorito según el estado de la película.
                     * Visible si la película está marcada como favorita, oculto en caso contrario.
                     */
                    favoriteIndicator.visibility = if (it.isFavorite) View.VISIBLE else View.GONE
                    
                    /**
                     * Carga la imagen de la película utilizando Glide si hay una URL disponible.
                     * Glide maneja automáticamente la carga asíncrona y el caché de imágenes.
                     */
                    if (it.imageUrl.isNotEmpty()) {
                        Glide.with(requireContext())
                            .load(it.imageUrl)
                            .into(movieImage)
                    }
                }
            }
        }
        
        // Observar errores
        /**
         * Observa el LiveData de errores del ViewModel.
         * Cuando se produce un error, muestra un mensaje Toast al usuario
         * y limpia el estado de error en el ViewModel para evitar mostrar
         * el mismo error múltiples veces.
         */
        movieViewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                movieViewModel.clearError()
            }
        }
        
        /**
         * Observa el estado de carga de datos desde el ViewModel.
         * Muestra u oculta un indicador de progreso en la interfaz de usuario
         * dependiendo de si hay una operación de carga en curso.
         */
        movieViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    /**
     * Llamado cuando la vista asociada a este fragmento está siendo destruida.
     * Limpia la referencia al binding para evitar fugas de memoria.
     * Es importante establecer _binding a null para permitir que el recolector
     * de basura libere los recursos asociados con la vista.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}