package lab.pguma.app2

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/app/2")
class DemoApiController {

    @GetMapping("/hello")
    fun helloWorld(): String {
        return "Hello App 2"
    }
}