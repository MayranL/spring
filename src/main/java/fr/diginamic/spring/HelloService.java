package fr.diginamic.spring;

import org.springframework.stereotype.Service;

@Service
public class HelloService {

    /**
     * Méthode qui retourne une chaine de caractère
     * @return
     */
    public String salutations(){
        return "Je suis la classe de service et je vous dis Bonjour ";
    }
}
