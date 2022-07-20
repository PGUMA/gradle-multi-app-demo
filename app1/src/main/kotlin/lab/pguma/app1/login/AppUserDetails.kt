package lab.pguma.app1.login

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails

class AppUserDetails(
    userId: String,
    password: String,
    val mailAddress: String,
    vararg roles: String
): User(userId, password, AuthorityUtils.createAuthorityList(*roles)) {

}