package com.philips.patientlog.config

import ca.uhn.fhir.context.FhirContext
import ca.uhn.fhir.rest.server.RestfulServer
import com.philips.patientlog.controller.PatientResourceProvider
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet

@WebServlet("/*")
class SimpleRestfulServer: RestfulServer() {

    @Throws(ServletException::class)
    override fun initialize() {
        //create a context for the appropriate version
        fhirContext = FhirContext.forR5()
        //Register Resource Providers - COMING SOON
        registerProvider(PatientResourceProvider())
    }

}