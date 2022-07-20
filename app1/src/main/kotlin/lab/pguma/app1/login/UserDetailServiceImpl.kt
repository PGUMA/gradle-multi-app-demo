package lab.pguma.app1.login

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component("userDetailsServiceImpl")
class UserDetailServiceImpl: UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        return AppUserDetails(
            "pguma",
            BCryptPasswordEncoder().encode("pguma"),
            "pguma@example.com",
            "USER"
        )
    }
}