package com.futi.app

import com.futi.app.config.security.Authorization
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.tomcat.jdbc.pool.DataSource
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement

@EnableTransactionManagement
@MapperScan("com.futi.app.dao")
@SpringBootApplication

class AppApplication {

    // Connection with Futi database
    @Bean(name = ["dataSourceFuti"])
    @ConfigurationProperties(prefix = "spring.datasource")
    fun dataSource(): DataSource {
        return DataSource()
    }

    // Connection with OAuth database
    @Bean(name = ["dataSourceOauth"])
    @ConfigurationProperties(prefix = "oauth.datasource")
    fun dataSourceOauth(): DataSource {
        return DataSource()
    }

    // User data from Oauth Token
    @Bean(name = ["authorization"])
    fun authorization(): Authorization {
        return Authorization()
    }

    @Bean
    @Throws(Exception::class)
    fun sqlSessionFactoryBean(): SqlSessionFactory? {
        return SqlSessionFactoryBean().apply {
            setDataSource(dataSource())
            setTypeAliasesPackage("com.futi.app.domain")
        }.`object`
    }

    @Bean(name = ["transactionManager"])
    fun transactionManager(): DataSourceTransactionManager {
        return DataSourceTransactionManager().apply { dataSource = dataSource() }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(AppApplication::class.java, *args)
        }
    }

}
