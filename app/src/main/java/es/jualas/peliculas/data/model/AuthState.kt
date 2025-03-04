package es.jualas.peliculas.data.model

/**
 * Clase sealed que representa los diferentes estados de las operaciones de autenticación.
 * Permite un manejo consistente de estados en toda la aplicación.
 */
sealed class AuthState<out T> {
    /**
     * Estado inicial o de reposo, antes de iniciar cualquier operación.
     */
    object Idle : AuthState<Nothing>()
    
    /**
     * Estado de carga, indica que una operación está en progreso.
     */
    object Loading : AuthState<Nothing>()
    
    /**
     * Estado de éxito, contiene los datos resultantes de la operación.
     * @param data Los datos resultantes de la operación.
     */
    data class Success<T>(val data: T) : AuthState<T>()
    
    /**
     * Estado de error, contiene información sobre el error ocurrido.
     * @param exception La excepción que causó el error.
     * @param message Un mensaje descriptivo del error.
     */
    data class Error(val exception: Throwable? = null, val message: String? = null) : AuthState<Nothing>()
}