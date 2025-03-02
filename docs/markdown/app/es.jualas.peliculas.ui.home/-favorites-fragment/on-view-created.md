//[app](../../../index.md)/[es.jualas.peliculas.ui.home](../index.md)/[FavoritesFragment](index.md)/[onViewCreated](on-view-created.md)

# onViewCreated

[androidJvm]\
open override fun [onViewCreated](on-view-created.md)(view: [View](https://developer.android.com/reference/kotlin/android/view/View.html), savedInstanceState: [Bundle](https://developer.android.com/reference/kotlin/android/os/Bundle.html)?)

Llamado inmediatamente después de que [onCreateView](on-create-view.md) haya retornado, pero antes de que cualquier estado guardado sea restaurado en la vista.

Este es el lugar ideal para inicializar componentes de UI, configurar adaptadores, listeners y observadores de LiveData.

#### Parameters

androidJvm

| | |
|---|---|
| view | La vista retornada por [onCreateView](on-create-view.md) |
| savedInstanceState | Estado previamente guardado del fragmento |
