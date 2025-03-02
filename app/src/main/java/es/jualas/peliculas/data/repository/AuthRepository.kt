package es.jualas.peliculas.data.repository

import com.google.firebase.auth.FirebaseAuth
import es.jualas.peliculas.data.model.User
import kotlinx.coroutines.tasks.await

/**
 * Repositorio que gestiona la autenticación de usuarios mediante Firebase Authentication.
 * 
 * Esta clase proporciona métodos para realizar operaciones comunes de autenticación como
 * inicio de sesión, registro, recuperación de contraseña, cierre de sesión y obtención
 * del usuario actual.
 */
class AuthRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()

    /**
     * Inicia sesión con email y contraseña.
     *
     * @param email Dirección de correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @return [Result] que contiene un objeto [User] si la autenticación es exitosa,
     *         o un error encapsulado si falla.
     */
    suspend fun login(email: String, password: String): Result<User> {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val user = authResult.user?.let {
                User(it.uid, it.email ?: "")
            } ?: throw Exception("Usuario no encontrado")
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Registra un nuevo usuario con email, contraseña y nombre.
     *
     * @param email Dirección de correo electrónico para el nuevo usuario.
     * @param password Contraseña para el nuevo usuario.
     * @param name Nombre del usuario.
     * @return [Result] que contiene un objeto [User] si el registro es exitoso,
     *         o un error encapsulado si falla.
     */
    suspend fun register(email: String, password: String, name: String): Result<User> {
        return try {
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = authResult.user?.let {
                User(it.uid, it.email ?: "", name)
            } ?: throw Exception("Error al crear usuario")
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Envía un correo electrónico para restablecer la contraseña.
     *
     * @param email Dirección de correo electrónico del usuario que desea restablecer su contraseña.
     * @return [Result] que contiene [Unit] si el correo se envía correctamente,
     *         o un error encapsulado si falla.
     */
    suspend fun recoverPassword(email: String): Result<Unit> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Cierra la sesión del usuario actual.
     */
    fun logout() {
        firebaseAuth.signOut()
    }

    /**
     * Obtiene el usuario actualmente autenticado.
     *
     * @return Objeto [User] con la información del usuario actual, o null si no hay ningún usuario autenticado.
     */
    fun getCurrentUser(): User? {
        return firebaseAuth.currentUser?.let {
            User(it.uid, it.email ?: "")
        }
    }
}