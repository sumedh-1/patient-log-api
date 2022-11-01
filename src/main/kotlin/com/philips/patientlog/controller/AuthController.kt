package com.philips.patientlog.controller

import com.philips.patientlog.dtos.LoginDto
import com.philips.patientlog.dtos.Message
import com.philips.patientlog.dtos.RegisterDto
import com.philips.patientlog.models.User
import com.philips.patientlog.models.UserType
import com.philips.patientlog.services.UserService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.Date
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/auth")
class AuthController(private val userService: UserService) {

    @PostMapping("/register")
    fun registerUser(@RequestBody body: RegisterDto): ResponseEntity<User> {
        val user = User()
        user.name = body.name
        user.email = body.email
        user.password = body.password
        user.userType = UserType.valueOf(body.userType).name
        return ResponseEntity.ok(this.userService.save(user))
    }

    @PostMapping("/login")
    fun login(@RequestBody body: LoginDto, response: HttpServletResponse): ResponseEntity<Any> {
        val user = this.userService.findByEmail(body.email)
        return when {
            user == null -> ResponseEntity.badRequest().body(Message("user not found"))
            !user.comparePassword(body.password) -> ResponseEntity.badRequest().body(Message("password mismatch"))
            else -> {
                val jwt = Jwts.builder().setIssuer(user.id.toString())
                    .setExpiration(Date(System.currentTimeMillis() + 60 * 24 * 1000))
                    .signWith(SignatureAlgorithm.HS256, "secret").compact()
                val cookie = Cookie("jwt", jwt)
                cookie.isHttpOnly = true
                response.addCookie(cookie)
                return ResponseEntity.ok(Message("success"))
            }
        }
    }

    @GetMapping("/user")
    fun user(@CookieValue("jwt") jwt: String?): ResponseEntity<Any> {
        return when (jwt) {
            null -> ResponseEntity.badRequest().body(Message("unauthenticated"))
            else -> {
                val body = Jwts.parser().setSigningKey("secret").parseClaimsJws(jwt).body
                return ResponseEntity.ok(this.userService.findById(body.issuer.toInt()).get())
            }
        }
    }

    @PostMapping("/logout")
    fun logout(response: HttpServletResponse): ResponseEntity<Any> {
        val cookie = Cookie("jwt", "")
        cookie.maxAge = 0
        response.addCookie(cookie)
        return ResponseEntity.ok(Message("success"))
    }
}