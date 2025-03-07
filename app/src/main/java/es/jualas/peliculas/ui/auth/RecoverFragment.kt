package es.jualas.peliculas.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import es.jualas.peliculas.R
import es.jualas.peliculas.data.model.AuthState
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

        // Configurar el MenuProvider para desactivar los menús
        setupMenuProvider()
        
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
     * Configura el proveedor de menús para este fragmento.
     * En este caso, se utiliza para desactivar la visualización de menús.
     */
    private fun setupMenuProvider() {
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Dejamos el menú vacío para que no se muestre ninguna opción
                menu.clear()
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // No procesamos ningún ítem de menú ya que no hay menú
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
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
        recoverViewModel.recoverState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is AuthState.Idle -> {
                    // Estado inicial, no hacer nada
                }
                is AuthState.Loading -> {
                    // Mostrar indicador de carga
                    binding.btnRecover.isEnabled = false
                    binding.progressBar?.visibility = View.VISIBLE
                }
                is AuthState.Success -> {
                    // Ocultar carga y mostrar mensaje de éxito
                    binding.btnRecover.isEnabled = true
                    binding.progressBar?.visibility = View.GONE
                    Toast.makeText(requireContext(),
                        "Si el correo existe, se ha enviado la recuperación",
                        Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_recover_to_login)
                    // Importante: resetear el estado después de consumirlo
                    recoverViewModel.resetRecoverState()
                }
                is AuthState.Error -> {
                    // Ocultar carga y mostrar error
                    binding.btnRecover.isEnabled = true
                    binding.progressBar?.visibility = View.GONE
                    Toast.makeText(requireContext(),
                        "Error: ${state.message ?: "Error en la recuperación de contraseña"}",
                        Toast.LENGTH_SHORT).show()
                    // Importante: resetear el estado después de consumirlo
                    recoverViewModel.resetRecoverState()
                }
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