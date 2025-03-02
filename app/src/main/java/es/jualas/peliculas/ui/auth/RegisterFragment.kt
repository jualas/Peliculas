package es.jualas.peliculas.ui.auth

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import es.jualas.peliculas.R
import es.jualas.peliculas.databinding.FragmentRegisterBinding
import es.jualas.peliculas.viewmodel.RegisterViewModel
import java.util.*

/**
 * Fragmento que maneja el proceso de registro de usuarios en la aplicación.
 * 
 * Este fragmento permite a los usuarios crear una nueva cuenta proporcionando
 * su correo electrónico, contraseña y fecha de nacimiento. Realiza validaciones
 * básicas de los campos antes de enviar la solicitud de registro.
 */
class RegisterFragment : Fragment(R.layout.fragment_register) {

    /**
     * Referencia al binding del fragmento que puede ser nula.
     * Se inicializa en [onCreateView] y se limpia en [onDestroyView].
     */
    private var _binding: FragmentRegisterBinding? = null
    
    /**
     * Propiedad delegada que proporciona acceso seguro al binding.
     * Lanza una excepción si se accede después de que [_binding] se haya establecido a null.
     */
    private val binding get() = _binding!!

    /**
     * ViewModel que maneja la lógica de negocio relacionada con el registro de usuarios.
     * Se inicializa en [onViewCreated].
     */
    private lateinit var viewModel: RegisterViewModel

    /**
     * Infla el layout del fragmento y inicializa el binding.
     *
     * @param inflater El LayoutInflater usado para inflar la vista.
     * @param container El ViewGroup contenedor donde se inflará la vista.
     * @param savedInstanceState Bundle que contiene el estado guardado del fragmento.
     * @return La vista raíz del fragmento.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Configura la interfaz de usuario y establece los listeners necesarios después
     * de que la vista ha sido creada.
     *
     * @param view La vista raíz del fragmento.
     * @param savedInstanceState Bundle que contiene el estado guardado del fragmento.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        /**
         * Configura el selector de fecha de nacimiento.
         * Muestra un DatePickerDialog cuando se hace clic en el TextView de fecha.
         */
        binding.tvBirthDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    val date = "$day/${month + 1}/$year"
                    binding.tvBirthDate.text = date
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        // Carga la imagen de perfil predeterminada
        Glide.with(this).load(R.drawable.ic_user).into(binding.ivProfile)

        /**
         * Configura el botón de registro para validar los campos y enviar
         * la solicitud de registro si todos los campos son válidos.
         */
        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()
            val confirmPassword = binding.etConfirmPassword.text.toString()
            val birthDate = binding.tvBirthDate.text.toString()

            // Validación del correo electrónico
            if (email.isEmpty()) {
                binding.tilEmail.error = "El correo es obligatorio"
            } else {
                binding.tilEmail.error = null
            }

            // Validación de la contraseña
            if (password.isEmpty()) {
                binding.tilPassword.error = "La contraseña es obligatoria"
            } else {
                binding.tilPassword.error = null
            }

            // Validación de la confirmación de contraseña
            if (confirmPassword.isEmpty() || password != confirmPassword) {
                binding.tilConfirmPassword.error = "Las contraseñas no coinciden"
            } else {
                binding.tilConfirmPassword.error = null
            }

            // Validación de la fecha de nacimiento
            if (birthDate == "Fecha de nacimiento") {
                Toast.makeText(
                    requireContext(),
                    "Selecciona tu fecha de nacimiento",
                    Toast.LENGTH_SHORT
                ).show()
            }

            // Si todos los campos son válidos, procede con el registro
            if (email.isNotEmpty() && password.isNotEmpty() && password == confirmPassword && birthDate != "Fecha de nacimiento") {
                viewModel.register(email, password, birthDate)
            }
        }

                /**
         * Observa el resultado del proceso de registro.
         * 
         * Maneja tanto el caso de éxito (redirigiendo al usuario al fragmento principal)
         * como el caso de error (mostrando un mensaje con la descripción del error).
         */
        viewModel.registrationResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess {
                Toast.makeText(requireContext(), "Registro exitoso", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
            }.onFailure { exception ->
                Toast.makeText(
                    requireContext(),
                    "Error en el registro: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        
        /**
         * Observa el estado de carga durante el proceso de registro.
         * 
         * Controla la visibilidad del indicador de progreso para proporcionar
         * retroalimentación visual al usuario mientras se procesa la solicitud.
         */
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

    }

    /**
     * Limpia los recursos cuando la vista del fragmento es destruida.
     * 
     * Establece el binding a null para evitar fugas de memoria
     * y permitir que el recolector de basura libere los recursos.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}