package es.jualas.peliculas.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import es.jualas.peliculas.R
import es.jualas.peliculas.data.model.Movie
import es.jualas.peliculas.databinding.FragmentSearchResultsBinding
import es.jualas.peliculas.ui.adapter.MovieAdapter
import es.jualas.peliculas.ui.search.SearchViewModel

/**
 * Fragmento que muestra los resultados de búsqueda de películas.
 * 
 * Este fragmento recibe una consulta de búsqueda a través de los argumentos
 * y muestra los resultados correspondientes en un RecyclerView.
 * También permite actualizar los resultados mediante un gesto de deslizamiento
 * hacia abajo (pull-to-refresh).
 */
class SearchResultsFragment : Fragment() {

    /**
     * Binding para acceder a las vistas del fragmento.
     * Se inicializa en [onCreateView] y se limpia en [onDestroyView].
     */
    private var _binding: FragmentSearchResultsBinding? = null
    
    /**
     * Propiedad delegada que proporciona acceso seguro al binding.
     * Lanza una excepción si se accede después de que la vista ha sido destruida.
     */
    private val binding get() = _binding!!

    /**
     * ViewModel que gestiona la lógica de búsqueda y los datos de las películas.
     */
    private lateinit var searchViewModel: SearchViewModel
    
    /**
     * Adaptador para mostrar la lista de películas en el RecyclerView.
     */
    private lateinit var movieAdapter: MovieAdapter

    /**
     * Crea y configura la vista del fragmento.
     *
     * @param inflater El LayoutInflater para inflar la vista
     * @param container El ViewGroup contenedor
     * @param savedInstanceState Estado guardado del fragmento
     * @return La vista raíz del fragmento
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchResultsBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Configura la vista después de que ha sido creada.
     * 
     * Inicializa el ViewModel, configura el RecyclerView, establece los observadores
     * para los datos y realiza la búsqueda inicial basada en la consulta recibida.
     *
     * @param view La vista raíz del fragmento
     * @param savedInstanceState Estado guardado del fragmento
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar ViewModel
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        // Configurar RecyclerView
        setupRecyclerView()

        // Observar resultados de búsqueda
        observeSearchResults()

        // Obtener la consulta de búsqueda de los argumentos
        arguments?.getString("searchQuery")?.let { query ->
            // Actualizar título con la consulta
            activity?.title = getString(R.string.search_results_for, query)

            // Realizar la búsqueda
            searchViewModel.searchMovies(query)
        }

        // Configurar SwipeRefreshLayout
        binding.swipeRefresh.setOnRefreshListener {
            arguments?.getString("searchQuery")?.let { query ->
                searchViewModel.searchMovies(query)
            }
        }
    }

        /**
     * Configura el RecyclerView para mostrar la lista de películas.
     * 
     * Esta función inicializa el adaptador de películas con los callbacks necesarios
     * para manejar los clics en las películas y en los botones de favoritos.
     * También configura el RecyclerView con un LinearLayoutManager y otras propiedades.
     */
    private fun setupRecyclerView() {
        movieAdapter = MovieAdapter(
            onMovieClick = { movie ->
                navigateToMovieDetail(movie)
            },
            onFavoriteClick = { movie ->
                // Implementa aquí la lógica para manejar el clic en favoritos
                // Por ejemplo:
                searchViewModel.toggleFavorite(movie)
            }
        )

        binding.recyclerViewSearchResults.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = movieAdapter
            setHasFixedSize(true)
        }
    }

    /**
     * Configura los observadores para los LiveData del ViewModel.
     * 
     * Esta función establece observadores para:
     * - El estado de carga, actualizando la visibilidad del indicador de progreso
     * - Los resultados de búsqueda, actualizando el adaptador y la visibilidad de las vistas
     * - Los mensajes de error, mostrándolos como Toast
     */
    private fun observeSearchResults() {
        // Observar estado de carga
        searchViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.swipeRefresh.isRefreshing = isLoading
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Observar resultados
        searchViewModel.searchResults.observe(viewLifecycleOwner) { movies ->
            movieAdapter.submitList(movies)

            // Mostrar mensaje si no hay resultados
            if (movies.isEmpty()) {
                binding.textNoResults.visibility = View.VISIBLE
                binding.recyclerViewSearchResults.visibility = View.GONE
            } else {
                binding.textNoResults.visibility = View.GONE
                binding.recyclerViewSearchResults.visibility = View.VISIBLE
            }
        }

        // Observar errores
        searchViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                searchViewModel.clearError()
            }
        }
    }

    /**
     * Navega a la pantalla de detalle de una película.
     * 
     * Esta función crea un Bundle con el ID de la película seleccionada
     * y utiliza el NavController para navegar al fragmento de detalle.
     * 
     * @param movie La película seleccionada para ver sus detalles
     */
    private fun navigateToMovieDetail(movie: Movie) {
        // Navegación usando el enfoque estándar con Bundle
        val bundle = Bundle().apply {
            putString("movieId", movie.id)
        }
        findNavController().navigate(R.id.movieDetailFragment, bundle)
    }

    /**
     * Limpia los recursos cuando la vista del fragmento es destruida.
     * 
     * Esta función se llama cuando la vista asociada a este fragmento
     * está siendo liberada. Aquí se limpia la referencia al binding
     * para evitar fugas de memoria.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}