package lab.pguma.app1.login

import org.springframework.context.event.EventListener
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent
import org.springframework.stereotype.Component

@Component
class BadCredentialEventListener {

    @EventListener
    fun onEvent(event: AuthenticationFailureBadCredentialsEvent) {
        println("失敗ユーザ名${event.authentication.name}")
    }
}