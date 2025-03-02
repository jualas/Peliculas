package es.jualas.peliculas.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import es.jualas.peliculas.databinding.FragmentContactBinding

/**
 * Fragmento que muestra la información de contacto de la aplicación.
 * Utiliza el enlace de datos para vincular la vista con el ViewModel.
 *
 * @author jualas
 * @since 1.0.0
 */
class ContactFragment : Fragment() {

    /**
     * Referencia a la instancia de [FragmentContactBinding] que se utiliza para enlazar la vista con el ViewModel.
     * Se inicializa en [onCreateView] y se establece en nulo en [onDestroyView] para evitar pérdidas de memoria.
     */
    private var _binding: FragmentContactBinding? = null

    /**
     * Devuelve la instancia de [FragmentContactBinding] inicializada.
     * Se utiliza la propiedad de extensión para evitar el desencadenamiento de llamadas nulas.
     */
    private val binding get() = _binding!!

    /**
     * Se llama cuando el fragmento se crea por primera vez y se asocia con la vista.
     * Infla la vista y devuelve la raíz de la vista.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Se llama después de que el fragmento se ha asociado con la vista y todos los elementos de la vista están disponibles.
     * Se utiliza para inicializar la vista y configurar los listeners.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Inicializar la vista y configurar listeners
    }

    /**
     * Se llama cuando el fragmento se está destruyendo y se debe liberar cualquier recurso.
     * Se utiliza para liberar la referencia a [FragmentContactBinding] para evitar pérdidas de memoria.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}