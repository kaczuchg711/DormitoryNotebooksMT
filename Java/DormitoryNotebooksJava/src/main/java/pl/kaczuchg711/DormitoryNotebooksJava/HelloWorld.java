package pl.kaczuchg711.DormitoryNotebooksJava;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class HelloWorld {

    @Autowired
    private TestDbTableRepository repository;

    @GetMapping("/")
    public String sayHello() {
        TestDbTable a = new TestDbTable();
        return "Kocham Kamilcie";
    }



}
