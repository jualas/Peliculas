package es.jualas.peliculas.data.model

/**
 * Clase sellada que representa los diferentes estados de autenticación.
 */
sealed class AuthState {
    /**
     * Estado inicial o de reposo.
     */
    object Idle : AuthState()
    
    /**
     * Estado de carga, cuando se está procesando una operación de autenticación.
     */
    object Loading : AuthState()
    
    /**
     * Estado de éxito, cuando una operación de autenticación se completa correctamente.
     * 
     * @param data Datos opcionales asociados con el éxito de la operación.
     */
    data class Success(val data: Any? = null) : AuthState()
    
    /**
     * Estado de error, cuando una operación de autenticación falla.
     * 
     * @param message Mensaje descriptivo del error.
     * @param exception Excepción opcional que causó el error.
     */
    data class Error(val message: String? = null, val exception: Throwable? = null) : AuthState()
}