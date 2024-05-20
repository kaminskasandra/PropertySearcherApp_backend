package com.example.propertysearcherprojectbackend.controller;

import com.example.propertysearcherprojectbackend.domain.LocalDateAdapter;
import com.example.propertysearcherprojectbackend.domain.User;
import com.example.propertysearcherprojectbackend.dto.UserDto;
import com.example.propertysearcherprojectbackend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private ModelMapper modelMapper;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void saveUserTest() throws Exception {
        //Given
        User user = new User(1L, "Jane", "Doe", "example@test.com", null);
        when(userService.saveUser(any(UserDto.class))).thenReturn(user);
        when(modelMapper.map(any(), eq(User.class))).thenReturn(user);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, LocalDateAdapter.getInstance())
                .create();
        String jsonContent = gson.toJson(user);

        //When & Then
        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(status().isOk());
        verify(userService, times(1)).saveUser(any(UserDto.class));
    }
    @Test
    void deleteUserTest() throws Exception {
        // Given
        Long userId = 1L;
        doNothing().when(userService).deleteUserById(userId);

        //When & Then
        mockMvc.perform(delete("/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userService, times(1)).deleteUserById(userId);
        verifyNoMoreInteractions(userService);
    }
    @Test
    void findAllUsersTest() throws Exception {
        //Given
        when(userService.getAllUsers()).thenReturn(Collections.emptyList());

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(userService, times(1)).getAllUsers();
    }
    @Test
    void updateUserTest() throws Exception {
        // Given
        UserDto userDto = new UserDto();
        userDto.setUserId(2L);
        User user = new User();

        when(userService.updateUser(any(UserDto.class), eq(2L))).thenReturn(user);
        when(modelMapper.map(any(), eq(UserDto.class))).thenReturn(userDto);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/users/{userId}", 2)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.userId").value(2));
    }
    @Test
    void gettUserByIdTest() throws Exception {

    }
}
