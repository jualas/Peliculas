package es.jualas.peliculas.ui.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import es.jualas.peliculas.R
import es.jualas.peliculas.databinding.FragmentLoginBinding
import es.jualas.peliculas.viewmodel.AuthViewModel

/**
 * Fragmento que gestiona la pantalla de inicio de sesión de la aplicación.
 *
 * Este fragmento permite a los usuarios autenticarse en la aplicación mediante:
 * - Correo electrónico y contraseña
 * - Inicio de sesión con Google (no implementado)
 *
 * También proporciona opciones para:
 * - Registrar una nueva cuenta
 * - Recuperar contraseña olvidada
 *
 * El fragmento verifica automáticamente si hay un usuario ya autenticado
 * y redirecciona a la pantalla principal si es el caso.
 */
class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    /**
     * Referencia al binding de la vista que permite acceder a los elementos de la UI.
     * Solo es válido entre onCreateView y onDestroyView.
     */
    private val binding get() = _binding!!

    /**
     * ViewModel que gestiona la lógica de autenticación y mantiene el estado
     * durante los cambios de configuración.
     */
    private lateinit var viewModel: AuthViewModel

    /**
     * Infla el layout del fragmento y inicializa el binding.
     *
     * @param inflater El LayoutInflater para inflar la vista
     * @param container El ViewGroup contenedor donde se inflará la vista
     * @param savedInstanceState Estado guardado del fragmento
     * @return La vista raíz del fragmento
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Configura la lógica de la UI después de que la vista ha sido creada.
     *
     * Este método:
     * - Inicializa el ViewModel
     * - Verifica si existe un usuario autenticado
     * - Configura los listeners para los botones
     * - Establece los observadores para los resultados de autenticación
     *
     * @param view La vista raíz del fragmento
     * @param savedInstanceState Estado guardado del fragmento
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        
        // Verificar si el usuario ya está autenticado
        viewModel.getCurrentUser()?.let {
            // Usuario ya autenticado, navegar directamente a la app
            findNavController().navigate(R.id.homeFragment)
            return
        }
        
        /**
         * Configura el botón de inicio de sesión para validar las credenciales
         * y autenticar al usuario cuando se hace clic.
         */
        binding.loginButton.setOnClickListener {
            val email = binding.usernameInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()
            
            if (!validateEmail(email)) {
                return@setOnClickListener
            }
            
            if (!validatePassword(password)) {
                return@setOnClickListener
            }
            
            // Mostrar indicador de progreso
            showLoading(true)
            
            // Realizar login
            viewModel.login(email, password)
        }
        
        /**
         * Configura el botón de registro para navegar a la pantalla de registro
         * cuando se hace clic.
         */
        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        
        /**
         * Configura el botón de recuperación de contraseña para navegar a la pantalla
         * de recuperación cuando se hace clic.
         */
        binding.forgotPasswordButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_recoverFragment)
        }
        
        /**
         * Configura el botón de inicio de sesión con Google.
         * Actualmente muestra un mensaje indicando que la funcionalidad no está implementada.
         */
        binding.signGoogle.setOnClickListener {
            Toast.makeText(requireContext(), "Inicio de sesión con Google no implementado", Toast.LENGTH_SHORT).show()
            // Implementar la lógica de inicio de sesión con Google
        }
        
        /**
         * Observa el resultado del proceso de inicio de sesión y actúa en consecuencia:
         * - En caso de éxito: navega a la pantalla principal
         * - En caso de error: muestra un mensaje apropiado según el tipo de error
         */
        viewModel.loginResult.observe(viewLifecycleOwner) { result ->
            showLoading(false)
            when {
                result.isSuccess -> {
                    Toast.makeText(requireContext(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                    // Navegar al fragmento principal de la app
                    findNavController().navigate(R.id.homeFragment)
                }
                result.isFailure -> {
                    val exception = result.exceptionOrNull()
                    val errorMessage = when {
                        exception?.message?.contains("password") == true -> "Contraseña incorrecta"
                        exception?.message?.contains("user") == true -> "Usuario no encontrado"
                        exception?.message?.contains("network") == true -> "Error de conexión"
                        else -> exception?.message ?: "Error en el inicio de sesión"
                    }
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }
        // Configurar TextWatchers para validar los campos
        setupTextWatchers()
    }

    /**
     * Valida el formato del correo electrónico ingresado.
     *
     * Verifica que el campo de correo no esté vacío y que tenga un formato válido
     * según el patrón estándar de direcciones de correo electrónico.
     * En caso de error, muestra un mensaje apropiado en el campo de entrada.
     *
     * @param email El correo electrónico a validar
     * @return true si el correo es válido, false en caso contrario
     */
    private fun validateEmail(email: String): Boolean {
        return when {
            email.isEmpty() -> {
                binding.usernameInputLayout.error = "El correo es obligatorio"
                false
            }

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.usernameInputLayout.error = "Formato de correo inválido"
                false
            }

            else -> {
                binding.usernameInputLayout.error = null
                true
            }
        }
    }

    /**
     * Valida la contraseña ingresada por el usuario.
     *
     * Verifica que el campo de contraseña no esté vacío y que cumpla con los
     * requisitos mínimos de seguridad (al menos 6 caracteres).
     * En caso de error, muestra un mensaje apropiado en el campo de entrada.
     *
     * @param password La contraseña a validar
     * @return true si la contraseña es válida, false en caso contrario
     */
    private fun validatePassword(password: String): Boolean {
        return when {
            password.isEmpty() -> {
                binding.passwordInputLayout.error = "La contraseña es obligatoria"
                false
            }

            password.length < 6 -> {
                binding.passwordInputLayout.error = "La contraseña debe tener al menos 6 caracteres"
                false
            }

            else -> {
                binding.passwordInputLayout.error = null
                true
            }
        }
    }

    /**
     * Controla la visualización del estado de carga durante el proceso de autenticación.
     *
     * Habilita o deshabilita los botones de interacción y muestra u oculta
     * el indicador de progreso según el estado de carga.
     *
     * @param isLoading true si se está realizando una operación de carga, false en caso contrario
     */
    private fun showLoading(isLoading: Boolean) {
        binding.loginButton.isEnabled = !isLoading
        binding.registerButton.isEnabled = !isLoading
        binding.forgotPasswordButton.isEnabled = !isLoading
        binding.signGoogle.isEnabled = !isLoading
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    /**
     * Configura los observadores de texto para los campos de entrada.
     *
     * Añade TextWatchers a los campos de correo y contraseña para validar
     * en tiempo real los datos ingresados por el usuario.
     */
    private fun setupTextWatchers() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            /**
             * Se llama después de que el texto ha cambiado.
             * Valida los campos de entrada para habilitar o deshabilitar el botón de login.
             *
             * @param s El texto modificado
             */
            override fun afterTextChanged(s: Editable?) {
                validateInputs()
            }
        }

        binding.usernameInput.addTextChangedListener(textWatcher)
        binding.passwordInput.addTextChangedListener(textWatcher)
    }

    /**
     * Valida los campos de entrada para habilitar o deshabilitar el botón de login.
     *
     * El botón de login se habilita solo cuando ambos campos (correo y contraseña)
     * contienen algún texto, sin realizar una validación completa en este punto.
     */
    private fun validateInputs() {
        val email = binding.usernameInput.text.toString().trim()
        val password = binding.passwordInput.text.toString().trim()
        binding.loginButton.isEnabled = email.isNotEmpty() && password.isNotEmpty()
    }

    /**
     * Limpia los recursos cuando la vista del fragmento es destruida.
     *
     * Libera la referencia al binding para evitar fugas de memoria
     * cuando el fragmento ya no está visible.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}