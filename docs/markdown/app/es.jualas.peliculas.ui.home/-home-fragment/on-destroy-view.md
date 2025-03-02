//[app](../../../index.md)/[es.jualas.peliculas.ui.home](../index.md)/[HomeFragment](index.md)/[onDestroyView](on-destroy-view.md)

# onDestroyView

[androidJvm]\
open override fun [onDestroyView](on-destroy-view.md)()

Limpia los recursos cuando la vista del fragmento es destruida.

Establece el binding a null para evitar memory leaks, ya que la vista del fragmento ya no existe pero la referencia al binding podría mantenerse.
