package jwt.tutorial.de.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("hello");
    }
    @PostMapping("/hello")
    public ResponseEntity<String> hello3(){
        return ResponseEntity.ok("hello");
    }

    @GetMapping("/signup")
    public ResponseEntity<String> hello2(){
        return ResponseEntity.ok("hello");
    }
}
