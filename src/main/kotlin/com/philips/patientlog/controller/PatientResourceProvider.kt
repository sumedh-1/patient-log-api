package com.philips.patientlog.controller

import ca.uhn.fhir.rest.annotation.IdParam
import ca.uhn.fhir.rest.annotation.Read
import ca.uhn.fhir.rest.annotation.RequiredParam
import ca.uhn.fhir.rest.annotation.Search
import ca.uhn.fhir.rest.param.StringParam
import ca.uhn.fhir.rest.server.IResourceProvider
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException
import org.hl7.fhir.instance.model.api.IBaseResource
import org.hl7.fhir.r5.model.IdType
import org.hl7.fhir.r5.model.Patient
import java.util.Locale


class PatientResourceProvider : IResourceProvider {

    private val patientMap: MutableMap<String, Patient> = mutableMapOf()

    override fun getResourceType(): Class<out IBaseResource> {
        return Patient::class.java
    }

    @Search
    fun search(@RequiredParam(name = Patient.SP_FAMILY) theParam: StringParam): List<Patient> =
        this.searchByFamilyName(theParam.value)


    @Read
    fun read(@IdParam theId: IdType): Patient {
        loadDummyPatients()
        return patientMap[theId.idPart] ?: throw ResourceNotFoundException(theId)
    }

    private fun searchByFamilyName(familyName: String): List<Patient> {
        loadDummyPatients()
        val retPatients: List<Patient> = patientMap.values
            .stream()
            .filter { next ->
                familyName.lowercase(Locale.getDefault()) == next.nameFirstRep.family
                    .lowercase(Locale.getDefault())
            }
            .toList()
        return retPatients
    }

    private fun loadDummyPatients() {
        var patient = Patient()
        patient.id = "1"
        patient.addIdentifier().setSystem("http://optum.com/MRNs").value = "007"
        patient.addName().setFamily("Chakravarty").addGiven("Mithun").addGiven("A")
        patient.addAddress().addLine("Address Line 1")
        patient.addAddress().city = "Mumbai"
        patient.addAddress().country = "India"
        patient.addTelecom().value = "111-111-1111"
        patientMap["1"] = patient
        for (i in 2..4) {
            patient = Patient()
            patient.id = i.toString()
            patient.addIdentifier().setSystem("http://optum.com/MRNs").value = "007$i"
            patient.addName().setFamily("Bond$i").addGiven("James").addGiven("J")
            patient.addAddress().addLine("House Line $i")
            patient.addAddress().city = "Your City"
            patient.addAddress().country = "USA"
            patientMap[i.toString()] = patient
        }
    }
}