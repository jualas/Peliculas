package es.jualas.peliculas.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.BuildConfig
import es.jualas.peliculas.R
import es.jualas.peliculas.databinding.FragmentSettingsBinding

/**
 * Fragmento que gestiona la configuración de la aplicación.
 * 
 * Este fragmento permite al usuario personalizar diferentes aspectos de la aplicación como:
 * - Configuración del tema (modo oscuro o seguir sistema)
 * - Preferencias de notificaciones
 * - Calidad de descarga de contenido
 * - Opciones de limpieza de caché
 * - Acceso a la política de privacidad
 */
class SettingsFragment : Fragment() {

    /**
     * Binding para acceder a las vistas del fragmento.
     * Se inicializa en [onCreateView] y se limpia en [onDestroyView].
     */
    private var _binding: FragmentSettingsBinding? = null
    
    /**
     * Propiedad delegada que proporciona acceso seguro al binding.
     * Lanza una excepción si se accede después de [onDestroyView].
     */
    private val binding get() = _binding!!
    
    /**
     * SharedPreferences para almacenar y recuperar las configuraciones del usuario.
     * Se inicializa en [onViewCreated].
     */
    private lateinit var sharedPreferences: SharedPreferences
    
    /**
     * Objeto compañero que contiene las constantes utilizadas en este fragmento.
     */
    companion object {
        /** Nombre del archivo de preferencias compartidas */
        private const val PREFS_NAME = "app_settings"
        
        /** Clave para la preferencia de modo oscuro */
        private const val KEY_DARK_MODE = "dark_mode"
        
        /** Clave para la preferencia de seguir el tema del sistema */
        private const val KEY_FOLLOW_SYSTEM = "follow_system"
        
        /** Clave para la preferencia de notificaciones de nuevas películas */
        private const val KEY_NEW_MOVIES_NOTIFICATIONS = "new_movies_notifications"
        
        /** Clave para la preferencia de notificaciones de actualizaciones de la app */
        private const val KEY_APP_UPDATES_NOTIFICATIONS = "app_updates_notifications"
        
        /** Clave para la preferencia de calidad de descarga */
        private const val KEY_DOWNLOAD_QUALITY = "download_quality"
    }

    /**
     * Crea y configura la vista del fragmento.
     *
     * @param inflater El inflador de layout utilizado para inflar la vista
     * @param container El contenedor padre donde se debe adjuntar la vista (si no es null)
     * @param savedInstanceState Estado previamente guardado del fragmento (si existe)
     * @return La vista raíz del fragmento
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Llamado inmediatamente después de que [onCreateView] haya retornado y la vista del fragmento
     * haya sido creada. Inicializa las preferencias, configura la versión de la app,
     * carga las configuraciones guardadas y configura los listeners de eventos.
     *
     * @param view La vista raíz del fragmento
     * @param savedInstanceState Estado previamente guardado del fragmento (si existe)
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Inicializar SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        
        // Configurar la versión de la app
        binding.versionText.text = BuildConfig.VERSION_NAME
        
        // Cargar configuraciones guardadas
        loadSettings()
        
        // Configurar listeners
        setupListeners()
    }
    
       /**
     * Carga las configuraciones guardadas desde SharedPreferences y las aplica a la interfaz de usuario.
     * 
     * Este método recupera las preferencias del usuario almacenadas previamente y actualiza
     * los controles de la interfaz para reflejar estas configuraciones. Si una preferencia
     * no existe, se utilizan valores predeterminados.
     * 
     * Las configuraciones cargadas incluyen:
     * - Preferencias de tema (modo oscuro y seguir sistema)
     * - Configuraciones de notificaciones (nuevas películas y actualizaciones)
     * - Calidad de descarga seleccionada por el usuario
     */
    private fun loadSettings() {
        // Cargar configuraciones de tema
        binding.darkModeSwitch.isChecked = sharedPreferences.getBoolean(KEY_DARK_MODE, false)
        binding.followSystemSwitch.isChecked = sharedPreferences.getBoolean(KEY_FOLLOW_SYSTEM, true)
        
        // Cargar configuraciones de notificaciones
        binding.newMoviesSwitch.isChecked = sharedPreferences.getBoolean(KEY_NEW_MOVIES_NOTIFICATIONS, true)
        binding.appUpdatesSwitch.isChecked = sharedPreferences.getBoolean(KEY_APP_UPDATES_NOTIFICATIONS, true)
        
        // Cargar calidad de descarga
        binding.downloadQualityText.text = sharedPreferences.getString(KEY_DOWNLOAD_QUALITY, "Alta")
    }
    
    /**
     * Configura todos los listeners de eventos para los controles de la interfaz de usuario.
     * 
     * Este método establece los comportamientos interactivos de la pantalla de configuración,
     * incluyendo:
     * - Manejo del botón de guardar configuraciones
     * - Comportamiento mutuamente excluyente entre los switches de modo oscuro y seguir sistema
     * - Acciones para opciones como limpieza de caché y selección de calidad de descarga
     * - Navegación a la política de privacidad
     * 
     * Cada listener está diseñado para proporcionar retroalimentación inmediata al usuario
     * y garantizar una experiencia de configuración coherente.
     */
    private fun setupListeners() {
        // Listener para el botón de guardar
        binding.saveSettingsButton.setOnClickListener {
            saveSettings()
            Toast.makeText(requireContext(), "Configuración guardada", Toast.LENGTH_SHORT).show()
        }
        
        /**
         * Listener para el switch de modo oscuro.
         * Cuando se activa, desactiva automáticamente la opción de seguir sistema
         * para evitar configuraciones contradictorias.
         */
        binding.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.followSystemSwitch.isChecked = false
            }
        }
        
        /**
         * Listener para el switch de seguir sistema.
         * Cuando se activa, desactiva automáticamente la opción de modo oscuro
         * para evitar configuraciones contradictorias.
         */
        binding.followSystemSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.darkModeSwitch.isChecked = false
            }
        }
        
        /**
         * Listener para la opción de limpiar caché.
         * Muestra un diálogo de confirmación antes de realizar la acción.
         */
        binding.clearCacheLayout.setOnClickListener {
            showClearCacheConfirmation()
        }
        
        /**
         * Listener para la opción de calidad de descarga.
         * Muestra un diálogo de selección con las diferentes opciones disponibles.
         */
        binding.downloadQualityLayout.setOnClickListener {
            showDownloadQualityDialog()
        }
        
        /**
         * Listener para la opción de política de privacidad.
         * Actualmente muestra un mensaje Toast, pero podría implementarse para
         * abrir un navegador con la política de privacidad completa.
         */
        binding.privacyPolicyLayout.setOnClickListener {
            // Aquí podrías abrir un navegador con la política de privacidad
            Toast.makeText(requireContext(), "Política de privacidad", Toast.LENGTH_SHORT).show()
        }
    }
    
        /**
     * Guarda todas las configuraciones del usuario en SharedPreferences.
     * 
     * Este método recopila el estado actual de todos los controles de la interfaz
     * y los almacena de forma persistente en SharedPreferences. Las configuraciones
     * guardadas incluyen:
     * - Preferencias de tema (modo oscuro y seguir sistema)
     * - Configuraciones de notificaciones (nuevas películas y actualizaciones)
     * - Calidad de descarga seleccionada
     * 
     * Después de guardar las configuraciones, aplica inmediatamente los cambios
     * de tema llamando a [applyTheme].
     */
    private fun saveSettings() {
        val editor = sharedPreferences.edit()
        
        // Guardar configuraciones de tema
        editor.putBoolean(KEY_DARK_MODE, binding.darkModeSwitch.isChecked)
        editor.putBoolean(KEY_FOLLOW_SYSTEM, binding.followSystemSwitch.isChecked)
        
        // Guardar configuraciones de notificaciones
        editor.putBoolean(KEY_NEW_MOVIES_NOTIFICATIONS, binding.newMoviesSwitch.isChecked)
        editor.putBoolean(KEY_APP_UPDATES_NOTIFICATIONS, binding.appUpdatesSwitch.isChecked)
        
        // Guardar calidad de descarga
        editor.putString(KEY_DOWNLOAD_QUALITY, binding.downloadQualityText.text.toString())
        
        editor.apply()
        
        // Aplicar el tema
        applyTheme()
    }
    
    /**
     * Aplica la configuración de tema seleccionada por el usuario.
     * 
     * Este método configura el modo de noche de la aplicación según las preferencias
     * del usuario utilizando [AppCompatDelegate]. Las opciones posibles son:
     * - Seguir el tema del sistema
     * - Activar modo oscuro
     * - Desactivar modo oscuro (modo claro)
     * 
     * Los cambios se aplican inmediatamente y afectan a toda la aplicación.
     */
    private fun applyTheme() {
        when {
            binding.followSystemSwitch.isChecked -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
            binding.darkModeSwitch.isChecked -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
    
    /**
     * Muestra un diálogo de confirmación para limpiar la caché de la aplicación.
     * 
     * Este método crea y muestra un diálogo de alerta utilizando [MaterialAlertDialogBuilder]
     * que solicita al usuario confirmar la acción de limpieza de caché. El diálogo incluye:
     * - Un título y mensaje explicativo
     * - Un botón positivo para confirmar la acción
     * - Un botón negativo para cancelar la operación
     * 
     * Si el usuario confirma, se ejecutaría el código para limpiar la caché y se muestra
     * un mensaje Toast de confirmación.
     */
    private fun showClearCacheConfirmation() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Limpiar caché")
            .setMessage("¿Estás seguro de que quieres limpiar la caché de la aplicación?")
            .setPositiveButton("Limpiar") { _, _ ->
                // Aquí iría el código para limpiar la caché
                Toast.makeText(requireContext(), "Caché limpiada", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
    
    /**
     * Muestra un diálogo para seleccionar la calidad de descarga.
     * 
     * Este método crea y muestra un diálogo con opciones de selección única utilizando
     * [MaterialAlertDialogBuilder]. Permite al usuario elegir entre diferentes niveles
     * de calidad para la descarga de contenido (Baja, Media, Alta).
     * 
     * El diálogo:
     * - Preselecciona la opción actual del usuario
     * - Si no hay una opción válida seleccionada, usa "Alta" como valor predeterminado
     * - Actualiza inmediatamente el texto mostrado en la interfaz cuando el usuario selecciona una opción
     * - Se cierra automáticamente después de la selección
     */
    private fun showDownloadQualityDialog() {
        val qualities = arrayOf("Baja", "Media", "Alta")
        var selectedQuality = qualities.indexOf(binding.downloadQualityText.text.toString())
        if (selectedQuality < 0) selectedQuality = 2 // Por defecto Alta
        
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Calidad de descarga")
            .setSingleChoiceItems(qualities, selectedQuality) { dialog, which ->
                binding.downloadQualityText.text = qualities[which]
                dialog.dismiss()
            }
            .show()
    }

    /**
     * Limpia los recursos cuando la vista del fragmento es destruida.
     * 
     * Este método se llama cuando la vista asociada con este fragmento está siendo eliminada.
     * Establece el binding a null para evitar fugas de memoria y permitir que el recolector
     * de basura libere los recursos asociados con la vista.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}