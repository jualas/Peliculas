package es.jualas.peliculas.viewmodel

import AuthRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.jualas.peliculas.data.model.User
import kotlinx.coroutines.launch

/**
 * ViewModel responsable de gestionar la lógica de registro de usuarios.
 * 
 * Esta clase maneja el proceso de registro de nuevos usuarios en la aplicación,
 * incluyendo la validación de datos, comunicación con el repositorio de autenticación
 * y la notificación de resultados a la UI.
 */
class RegisterViewModel : ViewModel() {
    
    /**
     * Repositorio que maneja las operaciones de autenticación.
     */
    private val repository = AuthRepository()
    
    /**
     * LiveData mutable interno que contiene el resultado del proceso de registro.
     * 
     * El resultado puede ser exitoso ([Result.success]) con un objeto [User],
     * o fallido ([Result.failure]) con la excepción correspondiente.
     */
    private val _registrationResult = MutableLiveData<Result<User>>()
    
    /**
     * LiveData público que expone el resultado del proceso de registro.
     * 
     * Este LiveData es observado por la UI para reaccionar a los cambios
     * en el estado del registro.
     */
    val registrationResult: LiveData<Result<User>> = _registrationResult
    
    /**
     * LiveData mutable interno que indica si hay una operación de registro en curso.
     */
    private val _isLoading = MutableLiveData<Boolean>()
    
    /**
     * LiveData público que expone el estado de carga durante el proceso de registro.
     * 
     * Este LiveData es observado por la UI para mostrar indicadores de progreso.
     */
    val isLoading: LiveData<Boolean> = _isLoading
    
    /**
     * Inicia el proceso de registro de un nuevo usuario.
     * 
     * @param email Dirección de correo electrónico del usuario.
     * @param password Contraseña elegida por el usuario.
     * @param birthDate Fecha de nacimiento del usuario en formato String.
     *                  Actualmente no se utiliza en el proceso de registro básico,
     *                  pero está disponible para futuras implementaciones.
     */
    fun register(email: String, password: String, birthDate: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                // Asumiendo que el nombre de usuario será el email por ahora
                val name = email.substringBefore("@")
                
                // Registrar al usuario y guardar el resultado
                val result = repository.register(email, password, name)
                
                // Actualizar el resultado del registro
                _registrationResult.value = result
                
                // Si el registro es exitoso, podríamos hacer algo adicional aquí
                // Por ejemplo, guardar la fecha de nacimiento en Firestore
                result.onSuccess { user ->
                    // Aquí podrías implementar la lógica para guardar datos adicionales
                    // Por ejemplo, usando otro repositorio para guardar el perfil completo
                }
                
            } catch (e: Exception) {
                _registrationResult.value = Result.failure(e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}