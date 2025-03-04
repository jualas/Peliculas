package es.jualas.peliculas.ui.auth

import androidx.navigation.fragment.findNavController
import android.util.Patterns
import android.text.TextWatcher
import es.jualas.peliculas.databinding.FragmentLoginBinding
import android.view.LayoutInflater
import es.jualas.peliculas.viewmodel.AuthViewModel
import android.view.View
import android.text.Editable
import android.widget.Toast
import es.jualas.peliculas.R
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import es.jualas.peliculas.data.model.AuthState

/**
 * Fragmento que gestiona la pantalla de inicio de sesión de la aplicación.
 */
class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    /**
     * ViewModel compartido que gestiona la lógica de autenticación.
     * Se usa activityViewModels() para compartir la instancia entre fragmentos.
     */
    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Verificar si el usuario ya está autenticado
        viewModel.getCurrentUser()?.let {
            // Usuario ya autenticado, navegar directamente a la app
            findNavController().navigate(R.id.homeFragment)
            return
        }
        
        binding.loginButton.setOnClickListener {
            val email = binding.usernameInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()
            
            if (!validateEmail(email)) {
                return@setOnClickListener
            }
            
            if (!validatePassword(password)) {
                return@setOnClickListener
            }
            
            // Realizar login
            viewModel.login(email, password)
        }
        
        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        
        binding.forgotPasswordButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_recoverFragment)
        }
        
        binding.signGoogle.setOnClickListener {
            Toast.makeText(requireContext(), "Inicio de sesión con Google no implementado", Toast.LENGTH_SHORT).show()
        }
        
        // Observar el estado de inicio de sesión
        viewModel.loginState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is AuthState.Idle -> {
                    // Estado inicial, no hacer nada
                }
                is AuthState.Loading -> {
                    showLoading(true)
                }
                is AuthState.Success -> {
                    showLoading(false)
                    Toast.makeText(requireContext(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.homeFragment)
                    // Importante: resetear el estado después de consumirlo
                    viewModel.resetLoginState()
                }
                is AuthState.Error -> {
                    showLoading(false)
                    val errorMessage = when {
                        state.message?.contains("password") == true -> "Contraseña incorrecta"
                        state.message?.contains("user") == true -> "Usuario no encontrado"
                        state.message?.contains("network") == true -> "Error de conexión"
                        else -> state.message ?: "Error en el inicio de sesión"
                    }
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                    // Importante: resetear el estado después de consumirlo
                    viewModel.resetLoginState()
                }
            }
        }
        
        // Configurar TextWatchers para validar los campos
        setupTextWatchers()
    }

    /**
     * Valida el formato del correo electrónico.
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
     * Valida la contraseña.
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
     * Muestra u oculta el indicador de carga y actualiza el estado de los botones.
     *
     * @param isLoading true para mostrar el indicador de carga, false para ocultarlo
     */
    private fun showLoading(isLoading: Boolean) {
        binding.loginButton.isEnabled = !isLoading
        binding.registerButton.isEnabled = !isLoading
        binding.forgotPasswordButton.isEnabled = !isLoading
        binding.signGoogle.isEnabled = !isLoading
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    /**
     * Configura los TextWatchers para validar los campos en tiempo real.
     */
    private fun setupTextWatchers() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No se requiere implementación
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No se requiere implementación
            }

            override fun afterTextChanged(s: Editable?) {
                validateInputs()
            }
        }

        binding.usernameInput.addTextChangedListener(textWatcher)
        binding.passwordInput.addTextChangedListener(textWatcher)
    }

    /**
     * Valida los campos de entrada y actualiza el estado del botón de inicio de sesión.
     *
     * Verifica que tanto el correo como la contraseña cumplan con los requisitos mínimos
     * para habilitar el botón de inicio de sesión.
     */
    private fun validateInputs() {
        val email = binding.usernameInput.text.toString().trim()
        val password = binding.passwordInput.text.toString()
        
        val isEmailValid = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isPasswordValid = password.isNotEmpty() && password.length >= 6
        
        // Habilitar el botón solo si ambos campos son válidos
        binding.loginButton.isEnabled = isEmailValid && isPasswordValid
    }

    /**
     * Limpia los recursos cuando la vista del fragmento es destruida.
     *
     * Establece el binding a null para evitar fugas de memoria y permitir
     * que el recolector de basura libere los recursos.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}