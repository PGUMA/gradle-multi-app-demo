package lab.pguma.app1.login.mfa

import lab.pguma.app1.login.AppUserDetails
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component("mfaAuthenticationProvider")
class MfaAuthenticationProvider: AuthenticationProvider {
    override fun authenticate(authentication: Authentication?): Authentication {
        val user = authentication!!.principal as AppUserDetails

        if(!BCryptPasswordEncoder().matches( "pguma", user.password)) {
            println("認証失敗:${authentication.toString()}")
            println("passF:${user.password}")
            throw BadCredentialsException("認証失敗")
        }

        println("認証成功${authentication.toString()}")
        println("passS:${user.password}")

        //return authentication
        return MfaAuthentication(
            AnonymousAuthenticationToken(user.username, user, AuthorityUtils.createAuthorityList("MFA_ANONYMOUS"))
        )
    }

    // 対象Authenticationの指定
    override fun supports(authentication: Class<*>?): Boolean {
        return MfaAuthentication::class.java.isAssignableFrom(authentication)
    }
}