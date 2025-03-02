package es.jualas.peliculas.data.model

/**
 * Modelo de datos que representa un usuario en la aplicación.
 *
 * Esta clase contiene la información básica de un usuario autenticado,
 * incluyendo su identificador único, correo electrónico, nombre y
 * una lista de películas favoritas.
 *
 * @property uid Identificador único del usuario proporcionado por Firebase Authentication.
 * @property email Dirección de correo electrónico del usuario.
 * @property name Nombre del usuario.
 * @property favoriteMovies Lista de identificadores de películas marcadas como favoritas por el usuario.
 */
data class User(
    val uid: String = "",
    val email: String = "",
    val name: String = "",
    val favoriteMovies: List<String> = listOf()
)