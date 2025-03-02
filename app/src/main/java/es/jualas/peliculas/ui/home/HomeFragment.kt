package es.jualas.peliculas.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import es.jualas.peliculas.R
import es.jualas.peliculas.databinding.FragmentHomeBinding
import es.jualas.peliculas.data.model.Movie
import es.jualas.peliculas.ui.adapter.MovieAdapter
import es.jualas.peliculas.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * Fragmento que muestra la pantalla principal de la aplicación con un listado de películas.
 * 
 * Este fragmento es responsable de:
 * - Mostrar un grid de películas disponibles
 * - Permitir la navegación al detalle de cada película
 * - Gestionar la funcionalidad de marcar/desmarcar películas como favoritas
 */
class HomeFragment : Fragment() {

    /**
     * Referencia al binding de la vista del fragmento.
     * Se inicializa en [onCreateView] y se limpia en [onDestroyView] para evitar memory leaks.
     */
    private var _binding: FragmentHomeBinding? = null
    
    /**
     * Propiedad delegada que proporciona acceso seguro al binding.
     * Lanza una excepción si se accede después de que [_binding] se ha establecido a null.
     */
    private val binding get() = _binding!!

    /**
     * ViewModel que gestiona la lógica de negocio y los datos para este fragmento.
     * Maneja la carga de películas, gestión de favoritos y estados de UI.
     */
    private lateinit var homeViewModel: HomeViewModel
    
    /**
     * Adaptador para el RecyclerView que muestra la lista de películas.
     * Gestiona la representación visual de cada película y los eventos de clic.
     */
    private lateinit var movieAdapter: MovieAdapter

    /**
     * Crea y configura la vista del fragmento.
     *
     * @param inflater El LayoutInflater que puede ser usado para inflar vistas
     * @param container El ViewGroup padre al que la vista debería adjuntarse
     * @param savedInstanceState Estado previamente guardado del fragmento
     * @return La vista raíz del fragmento
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Llamado inmediatamente después de que [onCreateView] ha retornado y la vista ha sido creada.
     * Inicializa el ViewModel, configura el RecyclerView y establece los observadores.
     *
     * @param view La vista raíz del fragmento
     * @param savedInstanceState Estado previamente guardado del fragmento
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar ViewModel
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        // Configurar RecyclerView
        setupRecyclerView()

        // Observar cambios en la lista de películas
        observeMovies()
    }

    /**
     * Configura el RecyclerView con su adaptador y layout manager.
     * 
     * Inicializa el adaptador con dos callbacks:
     * - onMovieClick: Para navegar al detalle de la película seleccionada
     * - onFavoriteClick: Para marcar/desmarcar una película como favorita
     */
    private fun setupRecyclerView() {
        // Inicializar el adaptador con dos lambdas para manejar clics
        movieAdapter = MovieAdapter(
            onMovieClick = { movie ->
                navigateToMovieDetail(movie)
            },
            onFavoriteClick = { movie ->
                // Aquí puedes manejar el clic en el botón de favoritos
                homeViewModel.toggleFavorite(movie)
            }
        )

        // Configurar el RecyclerView
        binding.moviesRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = movieAdapter
        }
    }
        /**
     * Configura los observadores para los LiveData del ViewModel.
     * 
     * Este método establece tres observadores principales:
     * 1. Estado de carga: Muestra u oculta el indicador de progreso
     * 2. Errores: Muestra mensajes de error mediante Snackbar
     * 3. Lista de películas: Actualiza el RecyclerView cuando hay nuevos datos
     * 
     * Finalmente, inicia la carga de películas desde el ViewModel.
     */
    private fun observeMovies() {
        // Observar estado de carga
        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.loadingProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Observar errores
        homeViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage != null && errorMessage.isNotEmpty()) {
                Snackbar.make(
                    binding.root,
                    errorMessage,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        // Observar películas
        // Observar películas
        homeViewModel.movies.observe(viewLifecycleOwner) { movies ->
            if (movies.isNotEmpty()) {
                movieAdapter.submitList(movies)
                binding.moviesRecyclerView.visibility = View.VISIBLE
            } else {
                Snackbar.make(
                    binding.root,
                    "No hay películas disponibles",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        // Cargar las películas
        homeViewModel.loadMovies()
    }

   /**
    * Navega a la pantalla de detalle de una película.
    * 
    * Utiliza el sistema de navegación de Android Jetpack para transicionar al fragmento
    * de detalle, pasando el ID de la película como argumento a través de un Bundle.
    * 
    * @param movie La película seleccionada cuyo detalle se quiere mostrar
    */
   private fun navigateToMovieDetail(movie: Movie) {
        // Crear un bundle con el ID de la película
        val bundle = Bundle().apply {
            putString("movieId", movie.id)
        }

        // Usar la acción de navegación ya definida en el grafo
        findNavController().navigate(R.id.action_homeFragment_to_movieDetailFragment, bundle)
    }

    /**
     * Limpia los recursos cuando la vista del fragmento es destruida.
     * 
     * Establece el binding a null para evitar memory leaks, ya que la vista
     * del fragmento ya no existe pero la referencia al binding podría mantenerse.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}