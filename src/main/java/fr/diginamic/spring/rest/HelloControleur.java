package fr.diginamic.spring.rest;

import fr.diginamic.spring.services.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class HelloControleur {

    private final HelloService helloService;

    public HelloControleur(HelloService helloService) {
        this.helloService = helloService;
    }

    /**
     * Méthode qui permet d'afficher le contenu de salutations
     * @return String
     */
    @GetMapping
    public String direHello() {
        return helloService.salutations();
    }



}
