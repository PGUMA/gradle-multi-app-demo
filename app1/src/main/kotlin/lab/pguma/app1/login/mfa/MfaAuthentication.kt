package lab.pguma.app1.login.mfa

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.CredentialsContainer
import org.springframework.security.core.GrantedAuthority

class MfaAuthentication(
    private val firstAuthentication: Authentication
) : AbstractAuthenticationToken(emptyList()) {

    override fun getPrincipal(): Any {
        return firstAuthentication.principal
    }

    override fun getCredentials(): Any {
        return firstAuthentication.credentials
    }

    override fun eraseCredentials() {
        if (firstAuthentication is CredentialsContainer) {
            firstAuthentication.eraseCredentials()
        }
    }

    override fun isAuthenticated(): Boolean {
        return false
    }
}