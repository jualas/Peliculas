package es.jualas.peliculas.ui.auth

import androidx.navigation.fragment.findNavController
import android.util.Patterns
import android.text.TextWatcher
import es.jualas.peliculas.databinding.FragmentLoginBinding
import android.view.LayoutInflater
import android.view.View
import android.text.Editable
import android.widget.Toast
import es.jualas.peliculas.R
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import es.jualas.peliculas.data.model.AuthState
import es.jualas.peliculas.viewmodel.LoginViewModel
import android.app.Activity
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.common.api.ApiException

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
    private val viewModel: LoginViewModel by viewModels()

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

    // Configurar Google Sign In
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

    // Asegurar que el botón de Google esté siempre habilitado
    binding.signGoogle.isEnabled = true

    // Launcher para el intent de inicio de sesión con Google
    val signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        Log.d("LoginFragment", "Google Sign-In result: ${result.resultCode}")
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                Log.d("LoginFragment", "Google Sign-In successful, email: ${account.email}")
                account.idToken?.let { token ->
                    Log.d("LoginFragment", "Authenticating with Firebase using token")
                    viewModel.loginWithGoogle(token)
                } ?: run {
                    Log.e("LoginFragment", "Error: No se pudo obtener el token")
                    Toast.makeText(requireContext(), "Error: No se pudo obtener el token", Toast.LENGTH_SHORT).show()
                }
            } catch (e: ApiException) {
                Log.e("LoginFragment", "Google Sign-In failed: ${e.statusCode}", e)
                Toast.makeText(requireContext(), "Error de Google Sign In: ${e.statusCode}", Toast.LENGTH_SHORT).show()
            }
        } else {
            Log.e("LoginFragment", "Google Sign-In canceled or failed")
        }
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
        signInLauncher.launch(googleSignInClient.signInIntent)
    }

    // Observar el estado de inicio de sesión
    viewModel.loginState.observe(viewLifecycleOwner) { state ->
        Log.d("LoginFragment", "Login state changed to: $state")
        when (state) {
            is AuthState.Idle -> {
                // No hacer nada
            }
            is AuthState.Loading -> {
                showLoading(true)
            }
            is AuthState.Success -> {
                showLoading(false)
                Toast.makeText(requireContext(), "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                Log.d("LoginFragment", "Navigating to home fragment")
                findNavController().navigate(R.id.homeFragment)
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
                Log.e("LoginFragment", "Login error: $errorMessage")
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                viewModel.resetLoginState()
            }
        }
    }

    // Configurar validación de campos
    setupInputValidation()
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
        // Deshabilitar todos los botones durante la carga
        binding.loginButton.isEnabled = !isLoading
        binding.registerButton.isEnabled = !isLoading
        binding.forgotPasswordButton.isEnabled = !isLoading
        binding.signGoogle.isEnabled = !isLoading // Este sí se deshabilita durante la carga

        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

        // Si terminamos de cargar, restauramos el estado correcto del botón de inicio de sesión
        if (!isLoading) {
            validateInputs() // Esto reestablecerá el estado correcto del botón de login
            binding.signGoogle.isEnabled = true // Aseguramos que el botón de Google esté habilitado
        }
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


private fun setupInputValidation() {
    // TextWatcher para validar en tiempo real
    val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            validateInputs()
        }
    }

    binding.usernameInput.addTextChangedListener(textWatcher)
    binding.passwordInput.addTextChangedListener(textWatcher)

    // Validación inicial
    validateInputs()
}

private fun validateInputs() {
    val email = binding.usernameInput.text.toString().trim()
    val password = binding.passwordInput.text.toString()

    val isEmailValid = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    val isPasswordValid = password.isNotEmpty() && password.length >= 6

    // Solo habilitar el botón de login si ambos campos son válidos
    binding.loginButton.isEnabled = isEmailValid && isPasswordValid

    // Asegurar que el botón de Google siempre esté habilitado
    binding.signGoogle.isEnabled = true
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