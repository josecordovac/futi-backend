package com.futi.app.service

import com.futi.app.domain.Configuration
import com.futi.app.domain.Response

interface ConfigurationService {
    fun getConfiguration(): Response<Configuration?>
    fun getClientConfiguration(): Response<Configuration?>
}