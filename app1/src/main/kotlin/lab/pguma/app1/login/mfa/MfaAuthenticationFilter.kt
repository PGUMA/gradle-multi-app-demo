package lab.pguma.app1.login.mfa

import lab.pguma.app1.login.AppUserDetails
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class MfaAuthenticationFilter: AbstractAuthenticationProcessingFilter(
    AntPathRequestMatcher(
        "/login",
        "POST"
    )
) {
    override fun attemptAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?): Authentication {

        val user = AppUserDetails(
            request!!.getParameter("userId"),
            BCryptPasswordEncoder().encode("pguma"),
            "test@example.com",
            "USER"
        )

        val password = request.getParameter("password")
        println("password:${password}")

        val token = MfaAuthentication(
            AnonymousAuthenticationToken(user.username, user, AuthorityUtils.createAuthorityList("MFA_ANONYMOUS"))
        )

        return authenticationManager.authenticate(token)
    }
}