package com.ucp.controllers.restControllers;

import com.ucp.models.*;
import com.ucp.repo.*;
import com.ucp.service.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
public class UserRestController extends Main {

    @Autowired
    UserRepository userRepository;


    @PostMapping("/edit-user/{id}")
    public ResponseEntity<String> editUser(@RequestBody Map<String, Object> userData, @PathVariable("id") String id) {
        String fio = (String) userData.get("fio");
        String photo = (String) userData.get("photo");

        Optional<User> userOptional = userRepository.findById(Long.valueOf(id));
        User user = userOptional.orElse(null);

        user.setFio(fio);
        user.setPhoto(photo);

        userRepository.save(user);

        return ResponseEntity.ok("Профиль отредактирован!");
    }

    @PostMapping("/edit-user-balance")
    public ResponseEntity<String> editUser(@RequestBody String amount) {
        User user = getUser();

        Integer currentBalance = user.getBalance();
        user.setBalance(currentBalance + Integer.valueOf(amount));

        userRepository.save(user);

        return ResponseEntity.ok("Баланс пополнен!");
    }
}
