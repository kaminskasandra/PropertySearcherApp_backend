package com.example.propertysearcherprojectbackend.controller;

import com.example.propertysearcherprojectbackend.domain.Message;
import com.example.propertysearcherprojectbackend.dto.MessageDto;
import com.example.propertysearcherprojectbackend.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessageController.class)
@ExtendWith(MockitoExtension.class)
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSaveMessage() throws Exception {
        MessageDto messageDto = new MessageDto();
        Message message = new Message();
        given(messageService.saveMessage(any(MessageDto.class))).willReturn(message);
        given(modelMapper.map(any(Message.class), any())).willReturn(messageDto);

        mockMvc.perform(post("/v1/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(messageDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.messageId").value(messageDto.getMessageId()));
    }

    @Test
    public void testFindAll() throws Exception {
        //Given
        when(messageService.getAllMessages()).thenReturn(Collections.emptyList());

        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/message")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        verify(messageService, times(1)).getAllMessages();
    }
    @Test
    public void testFindAllByUserId() throws Exception {
        //Given
        Message message = new Message();
        MessageDto messageDto = new MessageDto();
        given(messageService.getAllMessagesByUserId(anyLong())).willReturn(Collections.singletonList(message));
        given(modelMapper.map(any(Message.class), any())).willReturn(messageDto);

        //When & Then

        mockMvc.perform(get("/v1/message/findAllByUserId").param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].messageId").value(messageDto.getMessageId()));
    }

    @Test
    public void testDeleteMessage() throws Exception {
        mockMvc.perform(delete("/v1/message").param("messageId", "1"))
                .andExpect(status().isOk());
    }
}