package code.club.blog.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomePageController {
    @GetMapping
    public String sayHello(){
        return "Hello";
    }
    @PostMapping
    public void addUser(){

        //додати нового користувача
    //uppdateUser -
    }
    @GetMapping("/bye")
    public String sayBye(){
        return "Bye-Bye";
    }
    @GetMapping("/GraficCard")
    public String sayCard(){
        return "Card";
    }


}
