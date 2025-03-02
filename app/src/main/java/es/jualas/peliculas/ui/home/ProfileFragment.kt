package es.jualas.peliculas.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import es.jualas.peliculas.R
import es.jualas.peliculas.databinding.FragmentProfileBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Fragmento que muestra el perfil del usuario.
 * 
 * Este fragmento es responsable de mostrar la información del perfil del usuario actual,
 * incluyendo su nombre, correo electrónico, foto de perfil y estadísticas como
 * el número de películas favoritas y la fecha del último inicio de sesión.
 * 
 * La información se obtiene de Firebase Authentication y Firestore.
 */
class ProfileFragment : Fragment() {

    /** Binding para acceder a las vistas del fragmento. Se inicializa en onCreateView y se limpia en onDestroyView */
    private var _binding: FragmentProfileBinding? = null
    /** Propiedad delegada que proporciona acceso seguro al binding mientras el fragmento está en pantalla */
    private val binding get() = _binding!!
    
    /** Instancia de FirebaseAuth para gestionar la autenticación del usuario */
    private lateinit var auth: FirebaseAuth
    /** Instancia de FirebaseFirestore para acceder a la base de datos en la nube */
    private lateinit var firestore: FirebaseFirestore

    /**
     * Crea y devuelve la vista asociada con el fragmento.
     * 
     * @param inflater El LayoutInflater que se utilizará para inflar la vista.
     * @param container El ViewGroup padre al que se adjuntará la vista.
     * @param savedInstanceState Bundle que contiene el estado previamente guardado del fragmento.
     * @return La vista raíz del fragmento.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Se llama después de que onCreateView haya devuelto la vista, pero antes de que se haya
     * restaurado cualquier estado guardado en la vista.
     * 
     * Inicializa Firebase, configura la UI con los datos del usuario y establece los listeners.
     * 
     * @param view La vista devuelta por onCreateView.
     * @param savedInstanceState Bundle que contiene el estado previamente guardado del fragmento.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Inicializar Firebase
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        
        // Configurar la UI con los datos del usuario
        setupUserProfile()
        
        // Configurar listeners
        setupListeners()
    }
    
    /**
     * Configura la interfaz de usuario con los datos del perfil del usuario actual.
     * 
     * Si hay un usuario autenticado, muestra su información básica (nombre, correo electrónico)
     * y carga sus estadísticas desde Firestore. Si no hay usuario autenticado,
     * se podría redirigir a la pantalla de inicio de sesión (actualmente comentado).
     */
    private fun setupUserProfile() {
        val currentUser = auth.currentUser
        
        if (currentUser != null) {
            // Mostrar información básica del usuario
            binding.userNameText.text = currentUser.displayName ?: "Usuario"
            binding.userEmailText.text = currentUser.email
            
            // Cargar imagen de perfil si existe
            currentUser.photoUrl?.let { photoUrl ->
                // Aquí puedes usar una biblioteca como Glide o Picasso para cargar la imagen
                // Por ejemplo con Glide:
                // Glide.with(this).load(photoUrl).into(binding.profileImage)
            }
            
            // Obtener estadísticas del usuario desde Firestore
            loadUserStats(currentUser.uid)
        } else {
//            // Si no hay usuario autenticado, redirigir al login
//            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
    }
    
        /**
     * Carga las estadísticas del usuario desde Firestore.
     * 
     * Realiza una consulta a la colección "users" de Firestore para obtener información
     * específica del usuario como el contador de películas favoritas y la fecha del último inicio de sesión.
     * En caso de éxito, actualiza la UI con estos datos. En caso de error, establece valores predeterminados.
     * 
     * @param userId El identificador único del usuario en Firebase Authentication.
     */
    private fun loadUserStats(userId: String) {
        firestore.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    // Obtener el contador de favoritos
                    val favoritesCount = document.getLong("favoritesCount") ?: 0
                    binding.favoritesCount.text = favoritesCount.toString()
                    
                    // Obtener la fecha del último login
                    val lastLogin = document.getTimestamp("lastLogin")
                    if (lastLogin != null) {
                        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                        binding.lastLoginDate.text = dateFormat.format(lastLogin.toDate())
                    } else {
                        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                        binding.lastLoginDate.text = dateFormat.format(Date())
                    }
                }
            }
            .addOnFailureListener { e ->
                // Manejar el error
                binding.favoritesCount.text = "0"
                binding.lastLoginDate.text = "N/A"
            }
    }
    
    /**
     * Configura los listeners para los elementos interactivos de la interfaz de usuario.
     * 
     * Establece comportamientos para los botones y otros elementos interactivos del fragmento.
     * Actualmente configura el botón de editar perfil, mientras que el botón de cerrar sesión
     * está comentado ya que no está definido en el layout.
     */
    private fun setupListeners() {
        // Botón de editar perfil
        binding.editProfileButton.setOnClickListener {
            // Aquí puedes implementar la navegación a un fragmento de edición de perfil
            // findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

    }

    /**
     * Se llama cuando la vista asociada con este fragmento está siendo destruida.
     * 
     * Limpia el binding para evitar fugas de memoria y permitir que el fragmento
     * sea recolectado por el garbage collector cuando ya no se necesite.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}