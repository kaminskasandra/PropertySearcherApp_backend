package com.example.propertysearcherprojectbackend.service;

import com.example.propertysearcherprojectbackend.domain.Message;
import com.example.propertysearcherprojectbackend.dto.MessageDto;
import com.example.propertysearcherprojectbackend.domain.MessageCategory;
import com.example.propertysearcherprojectbackend.exceptions.UserNotFoundException;
import com.example.propertysearcherprojectbackend.domain.User;
import com.example.propertysearcherprojectbackend.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MessageServiceTest {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private MessageService messageService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllMessages() {
        // Given
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(1L, "Test Message", LocalDate.now(), MessageCategory.RECEIVED, new User(), new User()));
        when(messageRepository.findAll()).thenReturn(messages);

        // When
        List<Message> result = messageService.getAllMessages();

        // Then
        assertNotNull(result);
        assertEquals(messages.size(), result.size());
    }

    @Test
    public void testGetAllMessagesByUserId() throws UserNotFoundException {
        // Given
        Long userId = 1L;
        User user = new User();
        user.setUserId(userId);

        List<Message> messages = new ArrayList<>();
        messages.add(new Message(1L, "Test Message", LocalDate.now(), MessageCategory.RECEIVED, user, new User()));
        messages.add(new Message(2L, "Test Message", LocalDate.now(), MessageCategory.SENT, new User(), user));

        when(userService.getUser(userId)).thenReturn(user);
        when(messageRepository.findAllByToUserMailAndMessageCategory(user, MessageCategory.RECEIVED)).thenReturn(messages);
        when(messageRepository.findAllByFromUserMailAndMessageCategory(user, MessageCategory.SENT)).thenReturn(messages);

        // When
        List<Message> result = messageService.getAllMessagesByUserId(userId);

        // Then
        assertNotNull(result);
        assertEquals(messages.size() * 2, result.size());
    }

    @Test
    public void testSaveMessage() throws UserNotFoundException {
        // Given
        MessageDto messageDto = new MessageDto();
        messageDto.setText("Test Message");
        messageDto.setFromUserMail("user1@example.com");
        messageDto.setToUserMail("user2@example.com");

        User fromUser = new User();
        fromUser.setUserId(1L);
        fromUser.setMail("user1@example.com");

        User toUser = new User();
        toUser.setUserId(2L);
        toUser.setMail("user2@example.com");
        when(userService.findByMail("user1@example.com")).thenReturn(fromUser);
        when(userService.findByMail("user2@example.com")).thenReturn(toUser);
        when(messageRepository.save(any(Message.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Message result = messageService.saveMessage(messageDto);

        // Then
        assertNotNull(result);
        assertEquals(messageDto.getText(), result.getText());
        assertEquals(MessageCategory.SENT, result.getMessageCategory());
        assertEquals(fromUser, result.getFromUserMail());
        assertEquals(toUser, result.getToUserMail());
    }

    @Test
    public void testDeleteMessage() {
        // Given
        Long messageId = 1L;
        doNothing().when(messageRepository).deleteById(messageId);

        // When & Then
        assertDoesNotThrow(() -> messageService.deleteMessage(messageId));
        verify(messageRepository, times(1)).deleteById(messageId);
    }
}
