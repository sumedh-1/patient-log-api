package com.philips.patientlog.services

import com.philips.patientlog.dtos.RegisterDto
import com.philips.patientlog.models.User
import com.philips.patientlog.models.UserType
import com.philips.patientlog.repositories.UserRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class UserService(private val userRepository: UserRepository) {

    companion object {
        fun convertToModelUser(body: RegisterDto): User {
            val user = User()
            user.name = body.name
            user.email = body.email
            user.password = body.password
            user.userType = UserType.valueOf(body.userType).name
            return user
        }
    }

    fun save(user: User): User = this.userRepository.save(user)

    fun saveAll(users: List<User>): List<User> = this.userRepository.saveAll(users)

    fun findByEmail(email: String): User? = this.userRepository.findByEmail(email)

    fun findById(id: Int): Optional<User> = this.userRepository.findById(id)

}