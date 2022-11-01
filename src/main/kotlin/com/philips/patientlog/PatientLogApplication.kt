package com.philips.patientlog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories
@SpringBootApplication(exclude = [SecurityAutoConfiguration::class])
class PatientLogApplication

fun main(args: Array<String>) {
    runApplication<PatientLogApplication>(*args)
}
