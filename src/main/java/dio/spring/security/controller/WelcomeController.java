package dio.spring.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class WelcomeController {
    @GetMapping
    public String welcome(){
        return "Olá, seja bem vindo :)";
    }
    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('MANAGERS', 'USERS')")
    public  String users() {
        return "autorizados usuários";
    }

    @GetMapping("/managers")
    @PreAuthorize("hasAnyRole('MANAGERS')")
    public String managers(){
         return "autorizados managers";
    }

}
