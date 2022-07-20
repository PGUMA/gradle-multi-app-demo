package lab.pguma.app1.login

import lab.pguma.app1.login.mfa.MfaAuthentication
import lab.pguma.app1.login.mfa.MfaAuthenticationFilter
import lab.pguma.app1.login.mfa.MfaTrustResolver
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authorization.AuthorizationDecision
import org.springframework.security.authorization.AuthorizationManager
import org.springframework.security.config.annotation.ObjectPostProcessor
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.access.ExceptionTranslationFilter
import org.springframework.security.web.access.intercept.RequestAuthorizationContext
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import java.util.function.Supplier

@EnableWebSecurity
@Configuration
class SecurityConfig(
    @field:Qualifier("userDetailsServiceImpl")
    private val userDetailServiceImpl: UserDetailServiceImpl,
    @field:Qualifier("successHandler")
    private val successHandler: SuccessHandler,
    @field:Qualifier("mfaAuthenticationProvider")
    private val mfaAuthenticationProvider: AuthenticationProvider,
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Override
    override fun configure(http: HttpSecurity) {
        // 直リンク禁止＆ログイン不要ページの設定
        http.authorizeHttpRequests()
            .antMatchers(
                "/login",
            ).permitAll()
            .antMatchers(
                "/noSecurity"
            ).access(mfaAuthorizationManager())
            .anyRequest().authenticated()

        // ログイン処理
        http
            .formLogin()
            .loginPage("/login")
            //.successHandler(successHandler)
            //.defaultSuccessUrl("/noSecurity", true)
            //.failureUrl("/login?error")

        val filter = MfaAuthenticationFilter().apply {
            setRequiresAuthenticationRequestMatcher(AntPathRequestMatcher("/login", "POST"))
            setAuthenticationManager(authenticationManagerBean())
            setAuthenticationFailureHandler(SimpleUrlAuthenticationFailureHandler("/login?error"))
            setAuthenticationSuccessHandler(successHandler)
        }

        http.addFilterAt(filter, UsernamePasswordAuthenticationFilter::class.java)

        // ログアウト処理
        http
            .logout()
            .logoutRequestMatcher(AntPathRequestMatcher("/logout"))
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login")

        http.exceptionHandling { exceptions ->
            exceptions.withObjectPostProcessor(object: ObjectPostProcessor<ExceptionTranslationFilter> {
                override fun <O : ExceptionTranslationFilter?> postProcess(filter: O): O {
                    filter?.setAuthenticationTrustResolver(MfaTrustResolver())
                    return filter
                }
            })
        }

        http.securityContext().requireExplicitSave(false)
    }

    fun mfaAuthorizationManager(): AuthorizationManager<RequestAuthorizationContext> {
        return AuthorizationManager<RequestAuthorizationContext> { authentication, `object` -> AuthorizationDecision(authentication!!.get() is MfaAuthentication) }
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.eraseCredentials(true)
            .authenticationProvider(mfaAuthenticationProvider)
    }
}