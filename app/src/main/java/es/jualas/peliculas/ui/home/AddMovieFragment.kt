package es.jualas.peliculas.ui.home

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import es.jualas.peliculas.R
import es.jualas.peliculas.databinding.FragmentAddMovieBinding
import java.util.UUID

/**
 * Fragmento que permite al usuario añadir una nueva película a la base de datos.
 * 
 * Este fragmento proporciona una interfaz para que los usuarios introduzcan información
 * sobre una película, incluyendo título, año, precio, estado y una imagen opcional.
 * La información se guarda en Firebase Firestore y la imagen en Firebase Storage.
 */
class AddMovieFragment : Fragment() {

    /**
     * Binding para acceder a las vistas del fragmento.
     * Se inicializa en [onCreateView] y se limpia en [onDestroyView].
     */
    private var _binding: FragmentAddMovieBinding? = null
    
    /**
     * Propiedad delegada que proporciona acceso seguro al binding.
     * Lanza una excepción si se accede después de que [_binding] se haya limpiado.
     */
    private val binding get() = _binding!!
    
    /**
     * Instancia de Firestore para interactuar con la base de datos.
     * Se utiliza para guardar la información de las películas.
     */
    private val db = FirebaseFirestore.getInstance()
    
    /**
     * Instancia de Firebase Storage para almacenar imágenes.
     * Se utiliza para subir las imágenes de las películas.
     */
    private val storage = FirebaseStorage.getInstance()
    
    /**
     * Instancia de Firebase Auth para obtener información del usuario actual.
     * Se utiliza para asociar la película con el usuario que la añade.
     */
    private val auth = FirebaseAuth.getInstance()
    
    /**
     * URI de la imagen seleccionada por el usuario.
     * Es null si el usuario no ha seleccionado ninguna imagen.
     */
    private var selectedImageUri: Uri? = null
    
    /**
     * Registro para el selector de imágenes que maneja el resultado de la selección.
     * Actualiza [selectedImageUri] y muestra una vista previa de la imagen seleccionada.
     */
    private val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                selectedImageUri = uri
                binding.movieImagePreview.setImageURI(uri)
            }
        }
    }

    /**
     * Infla el layout del fragmento y inicializa el binding.
     *
     * @param inflater El LayoutInflater que se utiliza para inflar la vista.
     * @param container El ViewGroup que contendrá la vista inflada.
     * @param savedInstanceState Bundle que contiene el estado guardado del fragmento.
     * @return La vista raíz del fragmento.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Configura los listeners y el estado inicial de las vistas después de que la vista
     * del fragmento ha sido creada.
     *
     * @param view La vista raíz del fragmento.
     * @param savedInstanceState Bundle que contiene el estado guardado del fragmento.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Configurar el botón para seleccionar imagen
        binding.selectImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            getContent.launch(intent)
        }
        
        // Configurar el botón para guardar la película
        binding.saveMovieButton.setOnClickListener {
            saveMovie()
        }

        // Configurar estado por defecto
        binding.conditionGood.isChecked = true
    }
    
        /**
     * Valida y procesa los datos del formulario para guardar una nueva película.
     * 
     * Este método realiza las siguientes acciones:
     * 1. Extrae y valida los datos ingresados por el usuario (título, año, precio)
     * 2. Determina el estado de conservación seleccionado
     * 3. Activa el indicador de carga
     * 4. Inicia el proceso de guardado, que puede incluir:
     *    - Subir una imagen a Firebase Storage (si se seleccionó una)
     *    - Guardar los datos de la película en Firestore
     *
     * Si algún campo requerido está vacío, se muestra un mensaje de error
     * y se interrumpe el proceso de guardado.
     */
    private fun saveMovie() {
        // Validar campos
        val title = binding.movieTitleInput.text.toString().trim()
        val yearStr = binding.movieYearInput.text.toString().trim()
        val price = binding.moviePriceInput.text.toString().trim()
        
        if (title.isEmpty()) {
            binding.movieTitleLayout.error = "El título es obligatorio"
            return
        }
        
        if (yearStr.isEmpty()) {
            binding.movieYearLayout.error = "El año es obligatorio"
            return
        }
        
        if (price.isEmpty()) {
            binding.moviePriceLayout.error = "El precio es obligatorio"
            return
        }
        
        /**
         * Determina el estado de conservación de la película basado en
         * el RadioButton seleccionado en el grupo de opciones.
         */
        val condition = when (binding.conditionRadioGroup.checkedRadioButtonId) {
            R.id.conditionExcellent -> "Excelente"
            R.id.conditionGood -> "Bueno"
            R.id.conditionRegular -> "Regular"
            else -> "No especificado"
        }
        
        // Mostrar indicador de carga
        setLoadingState(true)

        /**
         * Flujo de guardado condicional:
         * - Si hay una imagen seleccionada, primero se sube a Firebase Storage
         *   y luego se guarda la película con la URL de la imagen
         * - Si no hay imagen, se guarda la película directamente sin URL de imagen
         */
        if (selectedImageUri != null) {
            uploadImageAndSaveMovie(title, yearStr.toInt(), condition, price.toDouble())
        } else {
            // Si no hay imagen, guardar la película directamente
            saveMovieToFirestore(title, yearStr.toInt(), condition, price.toDouble(), null)
        }
    }
    
        /**
     * Sube la imagen seleccionada a Firebase Storage y luego guarda la información de la película.
     *
     * Este método realiza el proceso de subida de la imagen a Firebase Storage utilizando
     * un identificador único (UUID) para evitar colisiones de nombres. Una vez que la imagen
     * se ha subido correctamente, obtiene la URL de descarga y procede a guardar todos los
     * datos de la película en Firestore.
     *
     * @param title El título de la película a guardar
     * @param year El año de lanzamiento de la película
     * @param condition El estado de conservación de la película (Excelente, Bueno, Regular)
     * @param price El precio de venta de la película
     */
    private fun uploadImageAndSaveMovie(title: String, year: Int, condition: String, price: Double) {
        val imageRef = storage.reference.child("movie_posters/${UUID.randomUUID()}")
        
        selectedImageUri?.let { uri ->
            imageRef.putFile(uri)
                .addOnSuccessListener {
                    // Obtener la URL de descarga
                    imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                        // Guardar la película con la URL de la imagen
                        saveMovieToFirestore(title, year, condition, price, downloadUri.toString())
                    }
                }
                .addOnFailureListener { e ->
                    setLoadingState(false)
                    showError("Error al subir la imagen: ${e.message}")
                }
        }
    }
    
    /**
     * Guarda la información de la película en la colección "movies" de Firestore.
     *
     * Este método crea un documento en Firestore con todos los datos de la película,
     * incluyendo la referencia al usuario que la ha añadido y una marca de tiempo
     * de creación. Si el proceso es exitoso, muestra un mensaje de confirmación y
     * navega de vuelta al fragmento anterior. En caso de error, muestra un mensaje
     * con la descripción del problema.
     *
     * @param title El título de la película a guardar
     * @param year El año de lanzamiento de la película
     * @param condition El estado de conservación de la película
     * @param price El precio de venta de la película
     * @param imageUrl La URL de la imagen de la película en Firebase Storage, o null si no hay imagen
     */
    private fun saveMovieToFirestore(title: String, year: Int, condition: String, price: Double, imageUrl: String?) {
        val userId = auth.currentUser?.uid ?: "anonymous"
        
        val movie = hashMapOf(
            "title" to title,
            "year" to year,
            "condition" to condition,
            "price" to price,
            "imageUrl" to imageUrl,
            "userId" to userId,
            "createdAt" to System.currentTimeMillis()
        )
        
        db.collection("movies")
            .add(movie)
            .addOnSuccessListener {
                setLoadingState(false)
                showSuccess("Película añadida correctamente")
                // Navegar de vuelta al fragmento de inicio
                findNavController().navigateUp()
            }
            .addOnFailureListener { e ->
                setLoadingState(false)
                showError("Error al guardar la película: ${e.message}")
            }
    }
    
    /**
     * Actualiza el estado de interacción de los elementos de la interfaz durante el proceso de guardado.
     *
     * Este método controla la habilitación/deshabilitación de los campos de entrada y botones
     * mientras se está realizando una operación de guardado, proporcionando retroalimentación visual
     * al usuario sobre el estado del proceso. También actualiza el texto del botón de guardar
     * para indicar que se está procesando la solicitud.
     *
     * @param isLoading Indica si hay una operación de guardado en curso (true) o no (false)
     */
    private fun setLoadingState(isLoading: Boolean) {
        binding.saveMovieButton.isEnabled = !isLoading
        binding.saveMovieButton.text = if (isLoading) "Guardando..." else "Guardar película"
        binding.selectImageButton.isEnabled = !isLoading
        binding.movieTitleInput.isEnabled = !isLoading
        binding.movieYearInput.isEnabled = !isLoading
        binding.moviePriceInput.isEnabled = !isLoading
        binding.conditionExcellent.isEnabled = !isLoading
        binding.conditionGood.isEnabled = !isLoading
        binding.conditionRegular.isEnabled = !isLoading
    }
    
    /**
     * Muestra un mensaje de éxito utilizando un Snackbar.
     *
     * Este método proporciona retroalimentación visual positiva al usuario
     * cuando una operación se ha completado correctamente.
     *
     * @param message El mensaje de éxito a mostrar
     */
    private fun showSuccess(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
    
    /**
     * Muestra un mensaje de error utilizando un Snackbar.
     *
     * Este método proporciona retroalimentación visual al usuario cuando
     * ha ocurrido un error durante alguna operación, permitiéndole
     * conocer la naturaleza del problema.
     *
     * @param message El mensaje de error a mostrar
     */
    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
    
    /**
     * Limpia los recursos cuando la vista del fragmento es destruida.
     *
     * Este método se encarga de liberar la referencia al binding cuando
     * el fragmento ya no está visible, evitando posibles fugas de memoria
     * y referencias a vistas que ya no existen.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}