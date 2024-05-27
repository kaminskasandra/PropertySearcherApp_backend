package com.example.propertysearcherprojectbackend.controller;

import com.example.propertysearcherprojectbackend.domain.Message;
import com.example.propertysearcherprojectbackend.dto.MessageDto;
import com.example.propertysearcherprojectbackend.exceptions.UserNotFoundException;
import com.example.propertysearcherprojectbackend.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/message")
public class MessageController {
    private final ModelMapper modelMapper;
    private final MessageService messageService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageDto> saveMessage(@RequestBody MessageDto messageDto) throws UserNotFoundException {
        return ResponseEntity.ok(modelMapper.map(messageService.saveMessage(messageDto), MessageDto.class));
    }

    @GetMapping
    public List<MessageDto> findAll() {
        List<Message> messages = messageService.getAllMessages();
        return messages.stream()
                .map(message -> modelMapper.map(message, MessageDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/findAllByUserId")
    public List<MessageDto> findAllByUserId(@RequestParam Long userId) throws UserNotFoundException {
        List<Message> messages = messageService.getAllMessagesByUserId(userId);
        return messages.stream()
                .map(message -> modelMapper.map(message, MessageDto.class))
                .collect(Collectors.toList());
    }

    @DeleteMapping
    public void deleteMessage(@RequestParam Long messageId) {
        messageService.deleteMessage(messageId);
    }
}
