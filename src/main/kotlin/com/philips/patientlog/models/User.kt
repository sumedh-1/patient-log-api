package com.philips.patientlog.models

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.DynamicInsert
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.Id
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Column
import javax.persistence.Table

@Entity
@DynamicInsert
@Table(name = "user_profiles")
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(name = "name")
    var name = ""

    @Column(name = "dob")
    var dob: String? = null

    @Column(name = "ssn")
    var ssn: String? = null

    @Column(name = "address")
    var address: String? = null

    @Column(name = "phone")
    var phone: String? = null

    @Column(name = "mrn")
    var mrn: String? = null

    @Column(name = "user_type")
    var userType: String = ""

    @Column(unique = true)
    var email = ""

    @Column
    var password = ""
        @JsonIgnore
        get() = field
        set(value) {
            field = BCryptPasswordEncoder().encode(value)
        }

    fun comparePassword(password: String): Boolean = BCryptPasswordEncoder().matches(password, this.password)

}