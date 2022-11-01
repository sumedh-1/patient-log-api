package com.philips.patientlog.services

import com.philips.patientlog.models.User
import com.philips.patientlog.repositories.UserRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class UserService(private val userRepository: UserRepository) {

    fun save(user: User): User = this.userRepository.save(user)

    fun findByEmail(email: String): User? = this.userRepository.findByEmail(email)

    fun findById(id: Int): Optional<User> = this.userRepository.findById(id)

}