//[app](../../../index.md)/[es.jualas.peliculas](../index.md)/[MainActivity](index.md)/[onNavigationItemSelected](on-navigation-item-selected.md)

# onNavigationItemSelected

[androidJvm]\
open override fun [onNavigationItemSelected](on-navigation-item-selected.md)(item: [MenuItem](https://developer.android.com/reference/kotlin/android/view/MenuItem.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html)

Maneja la selección de elementos en el menú de navegación lateral.

Este método gestiona la navegación cuando el usuario selecciona opciones del menú lateral:

- 
   Inicio: Navega al fragmento principal
- 
   Favoritos: Navega al fragmento de películas favoritas
- 
   Perfil: Navega al fragmento de perfil de usuario
- 
   Configuración: Navega al fragmento de ajustes
- 
   Cerrar sesión: Muestra un diálogo de confirmación y cierra la sesión del usuario

#### Return

true para indicar que la acción fue manejada

#### Parameters

androidJvm

| | |
|---|---|
| item | El elemento del menú seleccionado por el usuario |
