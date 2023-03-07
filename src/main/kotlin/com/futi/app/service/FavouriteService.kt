package com.futi.app.service

import com.futi.app.domain.Response

interface FavouriteService {

    fun addFavourite(enclosureId: Int): Response<Boolean?>
    fun removeFavourite(enclosureId: Int): Response<Boolean?>

}