package es.jualas.peliculas.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import es.jualas.peliculas.R
import es.jualas.peliculas.databinding.FragmentRecoverBinding
import es.jualas.peliculas.viewmodel.RecoverViewModel

/**
 * Fragmento que gestiona la recuperación de contraseñas de usuarios.
 *
 * Este fragmento proporciona una interfaz para que los usuarios puedan solicitar
 * el restablecimiento de su contraseña mediante el envío de un correo electrónico.
 * Utiliza [RecoverViewModel] para manejar la lógica de negocio relacionada con
 * la recuperación de contraseñas.
 */
class RecoverFragment : Fragment() {

    /**
     * Referencia al binding de la vista del fragmento.
     * Se establece como nula cuando la vista es destruida para evitar fugas de memoria.
     */
    private var _binding: FragmentRecoverBinding? = null
    
    /**
     * Propiedad delegada que proporciona acceso seguro al binding.
     * Lanza una excepción si se accede después de que la vista ha sido destruida.
     */
    private val binding get() = _binding!!

    /**
     * ViewModel que maneja la lógica de recuperación de contraseñas.
     * Se inicializa mediante delegación utilizando [viewModels].
     */
    private val recoverViewModel: RecoverViewModel by viewModels()

    /**
     * Crea y configura la vista del fragmento.
     *
     * @param inflater El inflador de layouts utilizado para inflar la vista.
     * @param container El contenedor padre donde se adjuntará la vista.
     * @param savedInstanceState Estado guardado de la instancia del fragmento.
     * @return La vista raíz del fragmento.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Llamado después de que la vista ha sido creada.
     * Configura los observadores y los listeners de la interfaz de usuario.
     *
     * @param view La vista raíz del fragmento.
     * @param savedInstanceState Estado guardado de la instancia del fragmento.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        
        /**
         * Configura el listener del botón de recuperación.
         * Valida que el campo de correo electrónico no esté vacío antes de
         * iniciar el proceso de recuperación de contraseña.
         */
        binding.btnRecover.setOnClickListener {
            val email = binding.etRecoverEmail.text.toString().trim()
            if (email.isEmpty()) {
                Toast.makeText(requireContext(), "Ingresa tu correo", Toast.LENGTH_SHORT).show()
            } else {
                recoverViewModel.recoverPassword(email)
            }
        }
    }
    
        /**
     * Configura los observadores para los LiveData del ViewModel.
     * 
     * Este método establece tres observadores principales:
     * 1. Para el estado de carga, actualizando la UI en consecuencia
     * 2. Para los mensajes de error, mostrándolos al usuario
     * 3. Para el estado de éxito, navegando de vuelta a la pantalla de login
     */
    private fun setupObservers() {
        /**
         * Observa el estado de carga para actualizar la interfaz de usuario.
         * Cuando está cargando, deshabilita el botón de recuperación y muestra
         * el indicador de progreso.
         */
        recoverViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.btnRecover.isEnabled = !isLoading
            binding.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        
        /**
         * Observa los mensajes de error del ViewModel.
         * Muestra un Toast con el mensaje de error y limpia el estado de error
         * para evitar que se muestre repetidamente.
         */
        recoverViewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            errorMsg?.let {
                Toast.makeText(requireContext(), "Error: $it", Toast.LENGTH_SHORT).show()
                recoverViewModel.clearError()
            }
        }
        
        /**
         * Observa el estado de éxito del proceso de recuperación.
         * Si es exitoso, muestra un mensaje informativo y navega de vuelta
         * a la pantalla de inicio de sesión.
         */
        recoverViewModel.success.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                Toast.makeText(requireContext(), "Si el correo existe, se ha enviado la recuperación", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_recover_to_login)
            }
        }
    }

    /**
     * Llamado cuando la vista del fragmento está siendo destruida.
     * Limpia la referencia al binding para evitar fugas de memoria.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}