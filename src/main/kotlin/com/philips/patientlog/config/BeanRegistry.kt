package com.philips.patientlog.config

import org.springframework.boot.web.servlet.ServletRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanRegistry {

    @Bean
    fun servletRegistrationBean(): ServletRegistrationBean<SimpleRestfulServer> {
        val registration = ServletRegistrationBean(SimpleRestfulServer(), "/*")
        registration.setName("FhirServlet")
        return registration
    }

}