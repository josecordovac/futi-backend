package com.futi.app.service

import com.futi.app.domain.Response
import com.futi.app.domain.User
import com.futi.app.domain.Version

interface VersionService {

    fun getVersions(): Response<Version?>

}