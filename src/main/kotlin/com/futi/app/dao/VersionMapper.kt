package com.futi.app.dao

import com.futi.app.domain.User
import com.futi.app.domain.Version
import org.apache.ibatis.annotations.Select

interface VersionMapper {

    /** SELECTS */
    @Select("SELECT v.app_version AS appVersion, IFNULL((SELECT mandatory FROM upgrade WHERE app_version = v.app_version),0) AS mandatory, v.setting as configuration, v.enclosure FROM versions v")
    fun getVersions(): Version?




}