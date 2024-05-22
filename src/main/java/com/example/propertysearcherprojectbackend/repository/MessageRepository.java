package com.example.propertysearcherprojectbackend.repository;

import com.example.propertysearcherprojectbackend.domain.Message;
import com.example.propertysearcherprojectbackend.domain.MessageCategory;
import com.example.propertysearcherprojectbackend.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByFromUserMailAndMessageCategory(User fromUserMail, MessageCategory messageCategory);

    List<Message> findAllByToUserMailAndMessageCategory(User toUserMail, MessageCategory messageCategory);
}
