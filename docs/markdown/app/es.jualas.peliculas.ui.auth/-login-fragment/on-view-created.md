//[app](../../../index.md)/[es.jualas.peliculas.ui.auth](../index.md)/[LoginFragment](index.md)/[onViewCreated](on-view-created.md)

# onViewCreated

[androidJvm]\
open override fun [onViewCreated](on-view-created.md)(view: [View](https://developer.android.com/reference/kotlin/android/view/View.html), savedInstanceState: [Bundle](https://developer.android.com/reference/kotlin/android/os/Bundle.html)?)

Configura la lógica de la UI después de que la vista ha sido creada.

Este método:

- 
   Inicializa el ViewModel
- 
   Verifica si existe un usuario autenticado
- 
   Configura los listeners para los botones
- 
   Establece los observadores para los resultados de autenticación

#### Parameters

androidJvm

| | |
|---|---|
| view | La vista raíz del fragmento |
| savedInstanceState | Estado guardado del fragmento |
