package es.jualas.peliculas.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import es.jualas.peliculas.databinding.FragmentFavoritesBinding

/**
 * Fragmento que muestra las películas marcadas como favoritas por el usuario.
 * 
 * Este fragmento es responsable de:
 * - Mostrar la lista de películas favoritas del usuario
 * - Permitir interacciones con los elementos de la lista
 * - Gestionar el estado de la vista cuando no hay favoritos
 */
class FavoritesFragment : Fragment() {

    /**
     * Referencia nullable al binding de la vista.
     * Se establece en [onCreateView] y se limpia en [onDestroyView]
     * para evitar fugas de memoria.
     */
    private var _binding: FragmentFavoritesBinding? = null
    
    /**
     * Propiedad de acceso al binding que garantiza acceso no-null.
     * Solo debe ser accedida entre [onCreateView] y [onDestroyView].
     * @throws IllegalStateException si se accede después de [onDestroyView]
     */
    private val binding get() = _binding!!

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
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Llamado inmediatamente después de que [onCreateView] haya retornado,
     * pero antes de que cualquier estado guardado sea restaurado en la vista.
     * 
     * Este es el lugar ideal para inicializar componentes de UI, configurar
     * adaptadores, listeners y observadores de LiveData.
     *
     * @param view La vista retornada por [onCreateView]
     * @param savedInstanceState Estado previamente guardado del fragmento
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Inicializar la vista y configurar listeners
    }

    /**
     * Limpia recursos cuando la vista del fragmento es destruida.
     * 
     * Establece el binding a null para evitar fugas de memoria
     * cuando el fragmento está en segundo plano pero aún adjunto
     * a la actividad.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}