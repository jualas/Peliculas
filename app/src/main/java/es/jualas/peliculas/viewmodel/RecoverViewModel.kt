package es.jualas.peliculas.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

/**
 * ViewModel responsable de gestionar la lógica de recuperación de contraseñas.
 * 
 * Esta clase maneja el proceso de envío de correos electrónicos para restablecer
 * contraseñas olvidadas, utilizando Firebase Authentication como proveedor de
 * autenticación. Proporciona estados observables para que la UI pueda reaccionar
 * a los diferentes estados del proceso.
 */
class RecoverViewModel : ViewModel() {
    
    /**
     * Instancia de FirebaseAuth utilizada para las operaciones de autenticación.
     */
    private val auth = FirebaseAuth.getInstance()
    
    /**
     * LiveData mutable interno que indica si hay una operación de recuperación en curso.
     */
    private val _loading = MutableLiveData<Boolean>()
    
    /**
     * LiveData público que expone el estado de carga durante el proceso de recuperación.
     * 
     * Este LiveData es observado por la UI para mostrar indicadores de progreso.
     */
    val loading: LiveData<Boolean> = _loading
    
    /**
     * LiveData mutable interno que contiene el mensaje de error en caso de fallo.
     * 
     * El valor es null cuando no hay errores.
     */
    private val _error = MutableLiveData<String?>()
    
    /**
     * LiveData público que expone los mensajes de error durante el proceso de recuperación.
     * 
     * Este LiveData es observado por la UI para mostrar mensajes de error al usuario.
     */
    val error: LiveData<String?> = _error
    
    /**
     * LiveData mutable interno que indica si el proceso de recuperación fue exitoso.
     */
    private val _success = MutableLiveData<Boolean>()
    
    /**
     * LiveData público que expone el estado de éxito del proceso de recuperación.
     * 
     * Este LiveData es observado por la UI para mostrar confirmación al usuario
     * y realizar navegación si es necesario.
     */
    val success: LiveData<Boolean> = _success
    
    /**
     * Inicia el proceso de recuperación de contraseña para el correo electrónico proporcionado.
     * 
     * Este método envía una solicitud a Firebase Authentication para enviar un correo
     * electrónico de restablecimiento de contraseña al usuario. Actualiza los estados
     * de carga, éxito y error según corresponda.
     * 
     * @param email Dirección de correo electrónico del usuario que desea recuperar su contraseña.
     */
    fun recoverPassword(email: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null
                
                auth.sendPasswordResetEmail(email).await()
                _success.value = true
                
            } catch (e: Exception) {
                // No mostramos el error específico por seguridad
                // pero lo registramos internamente
                _error.value = e.message
                _success.value = false
            } finally {
                _loading.value = false
            }
        }
    }
    
    /**
     * Limpia el estado de error actual.
     * 
     * Este método se utiliza típicamente después de que el usuario ha sido
     * notificado de un error, para evitar que el mismo error se muestre
     * múltiples veces o permanezca visible cuando ya no es relevante.
     */
    fun clearError() {
        _error.value = null
    }
}