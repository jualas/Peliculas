package es.jualas.peliculas.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import es.jualas.peliculas.data.model.AuthState
import kotlinx.coroutines.launch

/**
 * ViewModel específico para la funcionalidad de inicio de sesión.
 * Hereda de AuthViewModel para reutilizar la funcionalidad común de autenticación.
 */
class LoginViewModel : AuthViewModel() {
    
    /** LiveData mutable interno para el estado del inicio de sesión */
    private val _loginState = MutableLiveData<AuthState>(AuthState.Idle)
    /** LiveData público que expone el estado del inicio de sesión */
    val loginState: LiveData<AuthState> = _loginState
    
    /**
     * Inicia sesión con las credenciales proporcionadas.
     *
     * @param email El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     */
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = AuthState.Loading
            
            performLogin(email, password)
                .onSuccess { user ->
                    _loginState.value = AuthState.Success(user)
                    updateCurrentUser()
                }
                .onFailure { exception ->
                    _loginState.value = AuthState.Error(exception.message)
                }
        }
    }

    /**
     * Inicia sesión con credenciales de Google.
     *
     * @param idToken El token de ID obtenido del proceso de inicio de sesión de Google
     */
    fun loginWithGoogle(idToken: String) {
        viewModelScope.launch {
            _loginState.value = AuthState.Loading

            Log.d("LoginViewModel", "Starting Google authentication with Firebase")
            try {
                val result = repository.loginWithGoogle(idToken)
                result.onSuccess { user ->
                    Log.d("LoginViewModel", "Google auth success: ${user.email}")
                    _loginState.value = AuthState.Success(user)
                    updateCurrentUser()
                }.onFailure { exception ->
                    Log.e("LoginViewModel", "Google auth failed", exception)
                    _loginState.value = AuthState.Error(exception.message)
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Exception during Google auth", e)
                _loginState.value = AuthState.Error(e.message)
            }
        }
    }
    
    /**
     * Restablece el estado de inicio de sesión a su valor inicial (Idle).
     * Debe llamarse después de consumir un estado de éxito o error para evitar
     * que se procese nuevamente al reconstruir la vista.
     */
    fun resetLoginState() {
        _loginState.value = AuthState.Idle
    }
}