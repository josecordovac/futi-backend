package com.futi.app.impl

import com.futi.app.config.security.Authorization
import com.futi.app.dao.FavouriteMapper
import com.futi.app.domain.Response
import com.futi.app.service.FavouriteService
import com.futi.app.utils.Messages
import com.futi.app.utils.ResponseFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service("favouriteService")
class FavouriteServiceImpl : FavouriteService{

    val className = this.javaClass.simpleName ?: "FavouriteServiceImpl"

    @Autowired
    lateinit var favouriteMapper: FavouriteMapper

    @Autowired
    lateinit var authorization: Authorization

    override fun addFavourite(enclosureId: Int): Response<Boolean?> {
        try {
            authorization.getUser()?.id?.let{
                favouriteMapper.addFavourite(it, enclosureId)
                return ResponseFactory.succesfull(true)
            }
            return ResponseFactory.failure(Messages.FAILURE_ADD_FROM_FAVOURITE)
        }catch (e: Exception){
            return ResponseFactory.error(className, e.message)
        }
    }

    override fun removeFavourite(enclosureId: Int): Response<Boolean?> {
        try {
            authorization.getUser()?.id?.let{
                favouriteMapper.removeFavourite(it, enclosureId)
                return ResponseFactory.succesfull(true)
            }
            return ResponseFactory.failure(Messages.FAILURE_REMOVE_FROM_FAVOURITE)
        }catch (e: Exception){
            return ResponseFactory.error(className, e.message)
        }
    }

}