//[app](../../../index.md)/[es.jualas.peliculas.viewmodel](../index.md)/[HomeViewModel](index.md)/[toggleFavorite](toggle-favorite.md)

# toggleFavorite

[androidJvm]\
fun [toggleFavorite](toggle-favorite.md)(movie: [Movie](../../es.jualas.peliculas.data.model/-movie/index.md))

Cambia el estado de favorito de una película.

Este método actualiza el estado tanto en Firestore como en la lista local de películas, para mantener la UI sincronizada con la base de datos.

#### Parameters

androidJvm

| | |
|---|---|
| movie | La película cuyo estado de favorito se va a cambiar. |
