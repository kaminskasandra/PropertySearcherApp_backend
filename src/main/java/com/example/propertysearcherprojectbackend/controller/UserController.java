package com.example.propertysearcherprojectbackend.controller;

import com.example.propertysearcherprojectbackend.domain.User;
import com.example.propertysearcherprojectbackend.dto.UserDto;
import com.example.propertysearcherprojectbackend.exceptions.UserNotFoundException;
import com.example.propertysearcherprojectbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> saveUser (@RequestBody UserDto userDto) {
        return ResponseEntity.ok(modelMapper.map(userService.saveUser(userDto), UserDto.class));
    }
    @DeleteMapping(value = "{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable long userId) throws UserNotFoundException{
        userService.deleteUserById(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping()
    public List<UserDto> findAll() {
        List<User> users = userService.getAllUsers();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long userId) throws UserNotFoundException {
        return ResponseEntity.ok(modelMapper.map(userService.getUser(userId), UserDto.class));
    }

    @PutMapping(value = "{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> updateUser (@RequestBody UserDto userDto, @PathVariable long userId) throws UserNotFoundException {
        return ResponseEntity.ok(modelMapper.map(userService.updateUser(userDto, userId), UserDto.class));
    }
}
