package com.IAM.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.IAM.dto.UserDto;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Integer, UserDto> users = Map.of(
        1, new UserDto(1, "Niranjan", "Ameenpur"),
        2, new UserDto(2, "Alice", "Hyderabad")
    );

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable int id) {
        UserDto user = users.get(id);
        return user != null ? ResponseEntity.ok(user)
                            : ResponseEntity.notFound().build();
    }
}
