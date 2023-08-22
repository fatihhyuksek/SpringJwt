package com.alibou.security.demo;

import com.alibou.security.user.UserEntity;
import com.alibou.security.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth/demo-controller-admin")
public class DemoController {
    @Autowired
    private final UserRepository repository;

    private boolean isLoginAdmin=false;

    public DemoController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    @PreAuthorize("hashRole('ADMIN')")
    public ResponseEntity<String> sayHello(
            Authentication authentication
    ){
        String username=authentication.getName();
        String checkRole= String.valueOf(repository.findByRoleWithEmail(username));
        System.out.println(checkRole);
        if (checkRole.equals("ADMIN")) {
            isLoginAdmin=true;
            return ResponseEntity.ok("Hello from secured endpoint " + username);

        }
        return ResponseEntity.ok("Üzgünüm Rolünüz admin değil");

    }

    @GetMapping("/getUsers")
    public List<UserEntity> getUsers(){
        if (isLoginAdmin){
            return repository.getUsersAll();
        }
        else{
            return null;
        }
    }
}
