//[app](../../../../index.md)/[es.jualas.peliculas.ui.adapter](../../index.md)/[MovieAdapter](../index.md)/[MovieViewHolder](index.md)

# MovieViewHolder

[androidJvm]\
inner class [MovieViewHolder](index.md)(binding: &lt;Error class: unknown class&gt;) : [RecyclerView.ViewHolder](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.ViewHolder.html)

ViewHolder que contiene y gestiona la vista de un elemento de película individual.

Maneja la vinculación de datos de la película a los elementos de la UI y configura los listeners para las interacciones del usuario.

## Constructors

| | |
|---|---|
| [MovieViewHolder](-movie-view-holder.md) | [androidJvm]<br>constructor(binding: &lt;Error class: unknown class&gt;) |

## Properties

| Name | Summary |
|---|---|
| [itemView](index.md#29975211%2FProperties%2F-912451524) | [androidJvm]<br>@[NonNull](https://developer.android.com/reference/kotlin/androidx/annotation/NonNull.html)<br>val [itemView](index.md#29975211%2FProperties%2F-912451524): [View](https://developer.android.com/reference/kotlin/android/view/View.html) |

## Functions

| Name | Summary |
|---|---|
| [bind](bind.md) | [androidJvm]<br>fun [bind](bind.md)(movie: [Movie](../../../es.jualas.peliculas.data.model/-movie/index.md))<br>Vincula los datos de una película a los elementos de la UI. |
| [getAbsoluteAdapterPosition](index.md#358648312%2FFunctions%2F-912451524) | [androidJvm]<br>fun [getAbsoluteAdapterPosition](index.md#358648312%2FFunctions%2F-912451524)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-int/index.html) |
| [getAdapterPosition](index.md#644519777%2FFunctions%2F-912451524) | [androidJvm]<br>fun [~~getAdapterPosition~~](index.md#644519777%2FFunctions%2F-912451524)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-int/index.html) |
| [getBindingAdapter](index.md#-646392777%2FFunctions%2F-912451524) | [androidJvm]<br>@[Nullable](https://developer.android.com/reference/kotlin/androidx/annotation/Nullable.html)<br>fun [getBindingAdapter](index.md#-646392777%2FFunctions%2F-912451524)(): [RecyclerView.Adapter](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.Adapter.html)&lt;out [RecyclerView.ViewHolder](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.ViewHolder.html)&gt;? |
| [getBindingAdapterPosition](index.md#1427640590%2FFunctions%2F-912451524) | [androidJvm]<br>fun [getBindingAdapterPosition](index.md#1427640590%2FFunctions%2F-912451524)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-int/index.html) |
| [getItemId](index.md#1378485811%2FFunctions%2F-912451524) | [androidJvm]<br>fun [getItemId](index.md#1378485811%2FFunctions%2F-912451524)(): [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-long/index.html) |
| [getItemViewType](index.md#-1649344625%2FFunctions%2F-912451524) | [androidJvm]<br>fun [getItemViewType](index.md#-1649344625%2FFunctions%2F-912451524)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-int/index.html) |
| [getLayoutPosition](index.md#-1407255826%2FFunctions%2F-912451524) | [androidJvm]<br>fun [getLayoutPosition](index.md#-1407255826%2FFunctions%2F-912451524)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-int/index.html) |
| [getOldPosition](index.md#-1203059319%2FFunctions%2F-912451524) | [androidJvm]<br>fun [getOldPosition](index.md#-1203059319%2FFunctions%2F-912451524)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-int/index.html) |
| [getPosition](index.md#-1155470344%2FFunctions%2F-912451524) | [androidJvm]<br>fun [~~getPosition~~](index.md#-1155470344%2FFunctions%2F-912451524)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-int/index.html) |
| [isRecyclable](index.md#-1703443315%2FFunctions%2F-912451524) | [androidJvm]<br>fun [isRecyclable](index.md#-1703443315%2FFunctions%2F-912451524)(): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) |
| [setIsRecyclable](index.md#-1860912636%2FFunctions%2F-912451524) | [androidJvm]<br>fun [setIsRecyclable](index.md#-1860912636%2FFunctions%2F-912451524)(p0: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html)) |
| [toString](index.md#-1200015593%2FFunctions%2F-912451524) | [androidJvm]<br>open override fun [toString](index.md#-1200015593%2FFunctions%2F-912451524)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html) |
