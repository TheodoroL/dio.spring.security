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
    public  String users() {
        return "autorizados usuários";
    }

    @GetMapping("/managers")
    public String managers(){
         return "autorizados managers";
    }

}
