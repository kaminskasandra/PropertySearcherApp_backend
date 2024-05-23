package com.example.propertysearcherprojectbackend.service;

import com.example.propertysearcherprojectbackend.domain.AuditMessage;
import com.example.propertysearcherprojectbackend.domain.User;
import com.example.propertysearcherprojectbackend.dto.UserDto;
import com.example.propertysearcherprojectbackend.exceptions.UserNotFoundException;
import com.example.propertysearcherprojectbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private AuditMessageService auditMessageService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        //Given
        List<User> userList = new ArrayList<>();
        userList.add(new User(1L, "Jane", "Doe", "mail@test.pl", null, null, null));
        userList.add(new User(2L, "Jane", "Doe1", "mail@test.pl", null, null, null));

        //When
        when(userRepository.findAll()).thenReturn(userList);
        List<User> result = userService.getAllUsers();

        //Then
        assertEquals(userList, result);
    }

    @Test
    public void testSaveUser() {
        // Given
        UserDto userDto = new UserDto();
        userDto.setUserName("John");
        userDto.setUserLastName("Doe");
        when(modelMapper.map(userDto, User.class)).thenReturn(new User());
        when(userRepository.save(any(User.class))).thenReturn(new User());

        //When
        User savedUser = userService.saveUser(userDto);

        // Then
        assertNotNull(savedUser);
        verify(userRepository, times(1)).save(any(User.class));
        verify(auditMessageService, times(1)).saveAuditMessage(any(AuditMessage.class));
    }

    @Test
    public void testDeleteUserById() throws UserNotFoundException {
        // Given
        Long userId = 1L;
        when(userRepository.existsById(userId)).thenReturn(true);

        // When
        userService.deleteUserById(userId);

        // Then
        verify(userRepository, times(1)).deleteById(userId);
        verify(auditMessageService, times(1)).saveAuditMessage(any(AuditMessage.class));
    }

    @Test
    public void testUpdateUser() throws UserNotFoundException {
        // Given
        Long userId = 1L;
        UserDto userDto = new UserDto();
        User user = new User();
        when(userRepository.existsById(userId)).thenReturn(true);
        when(modelMapper.map(userDto, User.class)).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        User updatedUser = userService.updateUser(userDto, userId);

        // Then
        assertNotNull(updatedUser);
        assertEquals(userId, updatedUser.getUserId());
        verify(userRepository, times(2)).save(any(User.class));
    }

    @Test
    public void testGetUser() throws UserNotFoundException {
        // Given
        Long userId = 1L;
        User user = new User();
        when(userRepository.existsById(userId)).thenReturn(true);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // When
        User result = userService.getUser(userId);

        // Then
        assertNotNull(result);
        assertEquals(user, result);
        verify(userRepository, times(1)).findById(userId);
    }
}
