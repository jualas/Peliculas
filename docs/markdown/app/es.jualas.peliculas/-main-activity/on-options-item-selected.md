//[app](../../../index.md)/[es.jualas.peliculas](../index.md)/[MainActivity](index.md)/[onOptionsItemSelected](on-options-item-selected.md)

# onOptionsItemSelected

[androidJvm]\
open override fun [onOptionsItemSelected](on-options-item-selected.md)(item: [MenuItem](https://developer.android.com/reference/kotlin/android/view/MenuItem.html)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html)

Maneja las selecciones de elementos en el menú de opciones.

Este método gestiona las acciones cuando el usuario selecciona:

- 
   Botón de búsqueda: Configura el SearchView y maneja las consultas
- 
   Botón de configuración: Navega al fragmento de configuración
- 
   Otros elementos: Delega al comportamiento predeterminado

#### Return

true si la acción fue manejada, false en caso contrario

#### Parameters

androidJvm

| | |
|---|---|
| item | El elemento del menú seleccionado |
