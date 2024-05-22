package com.example.propertysearcherprojectbackend.service;

import com.example.propertysearcherprojectbackend.domain.Message;
import com.example.propertysearcherprojectbackend.domain.MessageCategory;
import com.example.propertysearcherprojectbackend.domain.User;
import com.example.propertysearcherprojectbackend.dto.MessageDto;
import com.example.propertysearcherprojectbackend.exceptions.UserNotFoundException;
import com.example.propertysearcherprojectbackend.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserService userService;

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public List<Message> getAllMessagesByUserId(Long userId) throws UserNotFoundException {
        User user = userService.getUser(userId);
        List<Message> messageList = new ArrayList<>();
        messageList.addAll(messageRepository.findAllByToUserMailAndMessageCategory(user, MessageCategory.RECEIVED));
        messageList.addAll(messageRepository.findAllByFromUserMailAndMessageCategory(user, MessageCategory.SENT));
        return messageList;
    }

    public Message saveMessage(MessageDto messageDto) throws UserNotFoundException {
        User fromUser = userService.getUser(Long.valueOf(messageDto.getFromUserMail()));
        User toUser = userService.getUser(Long.valueOf(messageDto.getToUserMail()));

        Message sentMessage = new Message(null, messageDto.getText(), LocalDate.now(), MessageCategory.SENT, fromUser, toUser);
        Message receivedMessage = new Message(null, messageDto.getText(), LocalDate.now(), MessageCategory.RECEIVED, fromUser, toUser);

        sentMessage = messageRepository.save(sentMessage);
        messageRepository.save(receivedMessage);
        return sentMessage;
    }

    public void deleteMessage(Long messageId) {
        messageRepository.deleteById(messageId);
    }
}
