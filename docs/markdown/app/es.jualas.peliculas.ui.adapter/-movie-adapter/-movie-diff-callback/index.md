//[app](../../../../index.md)/[es.jualas.peliculas.ui.adapter](../../index.md)/[MovieAdapter](../index.md)/[MovieDiffCallback](index.md)

# MovieDiffCallback

[androidJvm]\
class [MovieDiffCallback](index.md) : [DiffUtil.ItemCallback](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/DiffUtil.ItemCallback.html)&lt;[Movie](../../../es.jualas.peliculas.data.model/-movie/index.md)&gt; 

Implementación de DiffUtil.ItemCallback para calcular las diferencias entre listas y optimizar las actualizaciones del RecyclerView.

Permite que el ListAdapter determine qué elementos han cambiado, se han añadido o se han eliminado, minimizando las operaciones de actualización de la UI.

## Constructors

| | |
|---|---|
| [MovieDiffCallback](-movie-diff-callback.md) | [androidJvm]<br>constructor() |

## Functions

| Name | Summary |
|---|---|
| [areContentsTheSame](are-contents-the-same.md) | [androidJvm]<br>open override fun [areContentsTheSame](are-contents-the-same.md)(oldItem: [Movie](../../../es.jualas.peliculas.data.model/-movie/index.md), newItem: [Movie](../../../es.jualas.peliculas.data.model/-movie/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html)<br>Comprueba si dos elementos tienen el mismo contenido. |
| [areItemsTheSame](are-items-the-same.md) | [androidJvm]<br>open override fun [areItemsTheSame](are-items-the-same.md)(oldItem: [Movie](../../../es.jualas.peliculas.data.model/-movie/index.md), newItem: [Movie](../../../es.jualas.peliculas.data.model/-movie/index.md)): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html)<br>Determina si dos objetos representan el mismo elemento. |
| [getChangePayload](index.md#1433659584%2FFunctions%2F-912451524) | [androidJvm]<br>@[Nullable](https://developer.android.com/reference/kotlin/androidx/annotation/Nullable.html)<br>open fun [getChangePayload](index.md#1433659584%2FFunctions%2F-912451524)(@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p0: [Movie](../../../es.jualas.peliculas.data.model/-movie/index.md), @[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)p1: [Movie](../../../es.jualas.peliculas.data.model/-movie/index.md)): [Any](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-any/index.html)? |
