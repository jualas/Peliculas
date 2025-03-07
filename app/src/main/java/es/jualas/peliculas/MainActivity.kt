package es.jualas.peliculas

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import es.jualas.peliculas.databinding.ActivityMainBinding
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import es.jualas.peliculas.data.repository.MovieRepository

/**
 * Actividad principal de la aplicación que gestiona la navegación y la interfaz de usuario.
 *
 * Esta actividad implementa:
 * - Menú lateral (Navigation Drawer)
 * - Barra de navegación inferior
 * - Botón de acción flotante
 * - Autenticación con Firebase
 * - Navegación entre fragmentos
 *
 * @property binding Objeto de vinculación para acceder a las vistas de la actividad
 * @property navController Controlador de navegación para gestionar la navegación entre fragmentos
 * @property appBarConfiguration Configuración de la barra de acción para la navegación
 * @property toggle Botón de alternancia para abrir/cerrar el menú lateral
 * @property auth Instancia de FirebaseAuth para gestionar la autenticación de usuarios
 */
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    // Variables para manejar la interfaz y la navegación
    private lateinit var binding: ActivityMainBinding // Binding para acceder a las vistas
    private lateinit var navController: NavController // Controlador de navegación
    private lateinit var appBarConfiguration: AppBarConfiguration // Configuración de la barra superior
    private lateinit var toggle: ActionBarDrawerToggle // Botón hamburguesa para el menú lateral
    private lateinit var auth: FirebaseAuth // Autenticación de Firebase

    /**
     * Inicializa la actividad, configura la interfaz de usuario y establece los listeners.
     *
     * Este método realiza las siguientes operaciones:
     * - Muestra la pantalla de splash
     * - Inicializa la base de datos en la primera ejecución
     * - Configura la autenticación de Firebase
     * - Configura la barra de herramientas
     * - Configura el menú lateral y la navegación
     * - Establece los comportamientos de navegación
     * - Verifica el estado de autenticación del usuario
     *
     * @param savedInstanceState Estado guardado de la actividad, puede ser nulo
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        // Aplicar la pantalla de splash antes de crear la actividad
        installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar la base de datos solo la primera vez que se ejecuta la app
        val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val isFirstRun = prefs.getBoolean("is_first_run", true)

        if (isFirstRun) {
            initializeDatabase() // Cargar películas en la base de datos
            prefs.edit().putBoolean("is_first_run", false)
                .apply() // Marcar que ya no es primera ejecución
        }

        // Inicializar el servicio de autenticación de Firebase
        auth = FirebaseAuth.getInstance()

        // Configurar la barra de herramientas como ActionBar
        setSupportActionBar(binding.toolbar)

        // Obtener el controlador de navegación del fragmento anfitrión
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Configurar el menú lateral con el botón de hamburguesa
        toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Definir los destinos de nivel superior (sin botón de retroceso)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.loginFragment, // Pantalla de login
                R.id.homeFragment,  // Pantalla principal
                R.id.favoritesFragment, // Pantalla de favoritos
                R.id.profileFragment // Pantalla de perfil
            ),
            binding.drawerLayout
        )

        // Vincular la ActionBar con el controlador de navegación
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Configurar el menú lateral con el controlador de navegación
        binding.navigation.setupWithNavController(navController)
        binding.navigation.setNavigationItemSelectedListener(this)

        // Vincular la navegación inferior con el controlador de navegación
        binding.bottomNavigation.setupWithNavController(navController)

        // Añadir comportamiento al botón flotante para añadir películas
        binding.floatingActionButton.setOnClickListener {
            navController.navigate(R.id.addMovieFragment)
        }

        /**
         * Configura la visibilidad de los elementos de la interfaz según el destino de navegación.
         *
         * Ajusta la visibilidad de:
         * - Barra de acción
         * - Navegación inferior
         * - Botón flotante
         * - Menú lateral
         */
                /**
         * Configura el comportamiento de la UI según el destino de navegación.
         *
         * Ajusta la visibilidad de:
         * - Barra de acción
         * - Navegación inferior
         * - Botón flotante
         * - Menú lateral
         */
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // Configurar la visibilidad de elementos según el destino actual
            when (destination.id) {
                R.id.loginFragment, R.id.registerFragment, R.id.recoverFragment -> {
                    supportActionBar?.hide() // Ocultar barra superior en pantallas de autenticación
                    binding.bottomNavigation.visibility =
                        android.view.View.GONE // Ocultar navegación inferior
                    binding.floatingActionButton.visibility =
                        android.view.View.GONE // Ocultar botón flotante
                    binding.drawerLayout.setDrawerLockMode(androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED) // Bloquear menú lateral
                }

                R.id.movieDetailFragment -> {
                    supportActionBar?.show() // Mostrar barra superior
                    binding.bottomNavigation.visibility =
                        android.view.View.VISIBLE // Mostrar navegación inferior
                    binding.floatingActionButton.visibility =
                        android.view.View.GONE // Ocultar botón flotante en detalle de película
                }

                else -> {
                    supportActionBar?.show() // Mostrar barra superior en resto de pantallas
                    binding.bottomNavigation.visibility =
                        android.view.View.VISIBLE // Mostrar navegación inferior
                    binding.floatingActionButton.visibility =
                        android.view.View.VISIBLE // Mostrar botón flotante
                    binding.drawerLayout.setDrawerLockMode(androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED) // Desbloquear menú lateral
                }
            }
        }

        // Verificar si el usuario está autenticado al iniciar la app
        checkUserAuthentication()
    }

    /**
     * Verifica si el usuario está autenticado en Firebase.
     *
     * Este método comprueba si hay un usuario con sesión activa:
     * - Si no hay usuario autenticado, redirige a la pantalla de login
     * - Si hay un usuario autenticado, actualiza la información en el menú lateral
     */
    private fun checkUserAuthentication() {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            // Redirigir a login si no hay sesión activa
            navController.navigate(R.id.loginFragment)
        } else {
            // Actualizar información del usuario en el menú lateral
            updateNavigationHeader(currentUser)
        }
    }

    /**
     * Actualiza la información del usuario en el encabezado del menú lateral.
     *
     * Este método:
     * - Obtiene la vista del encabezado del menú lateral
     * - Actualiza el nombre de usuario mostrado
     * - Carga la foto de perfil del usuario usando Glide
     * - Aplica transformación circular a la imagen
     * - Muestra una imagen predeterminada si no hay foto disponible
     *
     * @param user El objeto FirebaseUser que contiene la información del usuario autenticado
     */
    private fun updateNavigationHeader(user: com.google.firebase.auth.FirebaseUser) {
        // Obtener vista del encabezado del menú lateral
        val headerView = binding.navigation.getHeaderView(0)
        val tvUserName = headerView.findViewById<android.widget.TextView>(R.id.textViewName)
        val ivUserPhoto = headerView.findViewById<android.widget.ImageView>(R.id.imageViewProfile)

        // Actualizar nombre de usuario en el encabezado
        tvUserName.text = user.displayName ?: "Usuario"

        // Cargar la foto de perfil con Glide si existe
        user.photoUrl?.let { uri ->
            com.bumptech.glide.Glide.with(this)
                .load(uri)
                .circleCrop() // Recortar en círculo
                .placeholder(R.drawable.ic_user) // Imagen temporal mientras carga
                .into(ivUserPhoto)
        } ?: run {
            // Usar imagen predeterminada si no hay foto
            ivUserPhoto.setImageResource(R.drawable.ic_user)
        }
    }

    /**
     * Crea el menú de opciones en la barra de herramientas.
     *
     * Este método infla el layout del menú de la barra de herramientas
     * definido en el archivo menu/toolbar_menu.xml.
     *
     * @param menu El objeto Menu donde se añadirán los elementos
     * @return true para que el menú se muestre
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflar el menú de la barra de herramientas
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    /**
     * Maneja las selecciones de elementos en el menú de opciones.
     *
     * Este método gestiona las acciones cuando el usuario selecciona:
     * - Botón de búsqueda: Configura el SearchView y maneja las consultas
     * - Botón de configuración: Navega al fragmento de configuración
     * - Otros elementos: Delega al comportamiento predeterminado
     *
     * @param item El elemento del menú seleccionado
     * @return true si la acción fue manejada, false en caso contrario
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                // Configurar el comportamiento de búsqueda
                val searchView = item.actionView as androidx.appcompat.widget.SearchView
                searchView.setOnQueryTextListener(object :
                    androidx.appcompat.widget.SearchView.OnQueryTextListener {
                    /**
                     * Se llama cuando el usuario envía la consulta de búsqueda.
                     *
                     * @param query El texto de búsqueda ingresado por el usuario
                     * @return true para indicar que la acción fue manejada
                     */
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        // Navegar a resultados de búsqueda al enviar consulta
                        query?.let {
                            val bundle = Bundle().apply {
                                putString("searchQuery", it)
                            }
                            navController.navigate(R.id.searchResultsFragment, bundle)
                        }
                        return true
                    }

                    /**
                     * Se llama cuando cambia el texto en el campo de búsqueda.
                     *
                     * Actualmente no implementa búsqueda en tiempo real, pero
                     * podría utilizarse para sugerencias o filtrado dinámico.
                     *
                     * @param newText El nuevo texto en el campo de búsqueda
                     * @return true para indicar que la acción fue manejada
                     */
                    override fun onQueryTextChange(newText: String?): Boolean {
                        // Para implementar búsqueda en tiempo real (no implementado)
                        return true
                    }
                })
                true
            }

            R.id.action_settings -> {
                // Navegar a la pantalla de configuración
                navController.navigate(R.id.settingsFragment)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Maneja la selección de elementos en el menú de navegación lateral.
     *
     * Este método gestiona la navegación cuando el usuario selecciona opciones del menú lateral:
     * - Inicio: Navega al fragmento principal
     * - Favoritos: Navega al fragmento de películas favoritas
     * - Perfil: Navega al fragmento de perfil de usuario
     * - Configuración: Navega al fragmento de ajustes
     * - Cerrar sesión: Muestra un diálogo de confirmación y cierra la sesión del usuario
     *
     * @param item El elemento del menú seleccionado por el usuario
     * @return true para indicar que la acción fue manejada
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Manejar los clics en elementos del menú lateral
        when (item.itemId) {
            R.id.nav_home -> navController.navigate(R.id.homeFragment) // Ir a inicio
            R.id.nav_favorites -> navController.navigate(R.id.favoritesFragment) // Ir a favoritos
            R.id.nav_profile -> navController.navigate(R.id.profileFragment) // Ir a perfil
            R.id.nav_settings -> navController.navigate(R.id.settingsFragment) // Ir a configuración
            R.id.nav_logout -> {
                // Mostrar diálogo de confirmación para cerrar sesión
                androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle(R.string.logout_title)
                    .setMessage(R.string.logout_message)
                    .setPositiveButton(R.string.yes) { _, _ ->
                        auth.signOut() // Cerrar sesión en Firebase
                        navController.navigate(R.id.loginFragment) // Volver a login
                    }
                    .setNegativeButton(R.string.no, null)
                    .show()
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START) // Cerrar el menú lateral tras selección
        return true
    }

    /**
     * Gestiona el comportamiento del botón "Atrás" del dispositivo.
     *
     * Este método personaliza el comportamiento del botón físico o virtual "Atrás":
     * - Si el menú lateral está abierto, lo cierra
     * - Si el menú lateral está cerrado, ejecuta el comportamiento predeterminado
     *   (retroceder en la pila de navegación o salir de la aplicación)
     */
    override fun onBackPressed() {
        // Gestionar el botón atrás: cerrar menú si está abierto o comportamiento normal
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    /**
     * Gestiona la navegación hacia arriba (botón de flecha en la barra de acción).
     *
     * Este método se invoca cuando el usuario presiona el botón de navegación hacia arriba
     * en la barra de acción. Utiliza la configuración de la barra de aplicación para
     * determinar el comportamiento adecuado según el destino actual.
     *
     * @return true si la navegación fue manejada por el controlador de navegación,
     *         false si se debe utilizar el comportamiento predeterminado
     */
    override fun onSupportNavigateUp(): Boolean {
        // Gestionar la navegación hacia arriba con el botón de la barra superior
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    /**
     * Actualiza la información del perfil de usuario en la interfaz.
     *
     * Este método público permite a otros componentes de la aplicación solicitar
     * una actualización de la información del usuario mostrada en el menú lateral,
     * por ejemplo, después de que el usuario actualice su perfil.
     *
     * Si hay un usuario autenticado, actualiza el encabezado del menú lateral
     * con la información más reciente del usuario.
     */
    fun refreshUserProfile() {
        // Actualizar información del encabezado con datos actuales del usuario
        auth.currentUser?.let { updateNavigationHeader(it) }
    }

    /**
     * Inicializa la base de datos de películas con datos predeterminados.
     *
     * Este método se ejecuta solo la primera vez que se inicia la aplicación y:
     * - Lanza una corrutina en el ámbito del ciclo de vida de la actividad
     * - Crea una instancia del repositorio de películas
     * - Llama al método para inicializar la base de datos con películas predefinidas
     * - Maneja el resultado usando un patrón Result (éxito/fracaso)
     * - Registra mensajes de depuración o error según corresponda
     */
    private fun initializeDatabase() {
        lifecycleScope.launch {
            try {
                val movieRepository = MovieRepository()
                val result = movieRepository.initializeMoviesDatabase()

                result.fold(
                    onSuccess = { success ->
                        Log.d("MainActivity", "Base de datos inicializada correctamente")
                    },
                    onFailure = { error ->
                        Log.e(
                            "MainActivity",
                            "Error al inicializar la base de datos: ${error.message}"
                        )
                    }
                )
            } catch (e: Exception) {
                Log.e("MainActivity", "Error al inicializar la base de datos: ${e.message}")
            }
        }
    }
}