package lab.pguma.app1.login

import lab.pguma.app1.login.mfa.MfaAuthentication
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component("successHandler")
class SuccessHandler: AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        println(authentication?.isAuthenticated ?: "authentication is null")

        //SecurityContextHolder.getContext().authentication = authentication!!

        request!!
            .getRequestDispatcher(request!!.contextPath + "/noSecurity")
            .forward(request, response);
    }
}