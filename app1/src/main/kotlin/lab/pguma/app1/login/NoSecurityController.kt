package lab.pguma.app1.login

import lab.pguma.app1.login.mfa.MfaAuthentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class NoSecurityController {

    @GetMapping("/noSecurity")
    fun login(): String {
        try {
            val authentication = SecurityContextHolder
                .getContext()
                .authentication as MfaAuthentication

            println("匿名：${authentication::class.java.simpleName}")
            println("匿名：${authentication.isAuthenticated}")
        } catch(e: Exception) {
            println(e.message)
        }
        return "noSecurity"
    }

    @PostMapping("/noSecurity")
    fun loginPost(): String {
        try {
            val authentication = SecurityContextHolder
                .getContext()
                .authentication as MfaAuthentication

            println("匿名：${authentication::class.java.simpleName}")
            println("匿名：${authentication.isAuthenticated}")
        } catch(e: Exception) {
            println(e.message)
        }
        return "noSecurity"
    }
}