package com.example.propertysearcherprojectbackend.configuration;

import com.example.propertysearcherprojectbackend.domain.Message;
import com.example.propertysearcherprojectbackend.dto.MessageDto;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ObjectMapperConfig {
    private final ModelMapper modelMapper;

    @PostConstruct
    public void init() {
        setMessageToMessageDtoConfiguration();
    }
    public void setMessageToMessageDtoConfiguration() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TypeMap<Message, MessageDto> propertyMapper = modelMapper.createTypeMap(Message.class, MessageDto.class);
        propertyMapper.addMappings(mapper -> mapper.map(message -> message.getToUserMail().getUserId(), MessageDto::setToUserMail));
        propertyMapper.addMappings(mapper -> mapper.map(message -> message.getFromUserMail().getUserId(), MessageDto::setFromUserMail));
    }
}
