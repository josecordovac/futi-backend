package com.futi.app.impl

import com.futi.app.dao.InductionMapper
import com.futi.app.dao.VersionMapper
import com.futi.app.domain.Configuration
import com.futi.app.domain.Response
import com.futi.app.domain.Version
import com.futi.app.service.VersionService
import com.futi.app.utils.Constants
import com.futi.app.utils.ResponseFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service("versionrService")
class VersionServiceImpl : VersionService{

    val className = this.javaClass.simpleName ?: "ConfigurationServiceImpl"

    @Autowired
    lateinit var versionMapper: VersionMapper

    override fun getVersions(): Response<Version?> {
        return try {
            ResponseFactory.succesfull(versionMapper.getVersions())
        }catch (e: Exception){
            ResponseFactory.error(className, e.message)
        }
    }
}