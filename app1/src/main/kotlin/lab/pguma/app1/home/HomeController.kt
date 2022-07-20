package lab.pguma.app1.home

import lab.pguma.app1.login.AppUserDetails
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.security.Principal

@Controller
class HomeController {

    @GetMapping("/home")
    fun home(
        model: Model,
        @AuthenticationPrincipal user: AppUserDetails
    ):String {

        println("HomeController start")

        val authentication = SecurityContextHolder
            .getContext()
            .authentication as AbstractAuthenticationToken

        println("userInfo: ${authentication.toString()}")
        println("object:${authentication.hashCode()}")

        println("HomeController end")

        println("test start")
        val test = BaseEXEX()

        val testUpCast:Base = test
        val testDownCast1 = testUpCast as BaseEx
        val testDownCast2 = testDownCast1 as BaseEXEX
        println("test end")


        return "Home"
    }

    @GetMapping("/home2")
    fun home2(
        model: Model,
        principal: Principal
    ):String {

        println("HomeController start")

        val authentication = principal as Authentication
        val user = authentication.principal as AppUserDetails

        println("userInfo: ${user.toString()}")

        val user2 = SecurityContextHolder
            .getContext()
            .authentication
            .principal as AppUserDetails

        println("userInfo: ${user2.toString()}")

        println("HomeController end")
        return "Home"
    }



}

open class Base()

open class BaseEx(): Base()

class BaseEXEX(): BaseEx()