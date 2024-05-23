package com.example.propertysearcherprojectbackend.configuration;

import com.example.propertysearcherprojectbackend.domain.Appointment;
import com.example.propertysearcherprojectbackend.domain.Message;
import com.example.propertysearcherprojectbackend.domain.Property;
import com.example.propertysearcherprojectbackend.dto.AppointmentDto;
import com.example.propertysearcherprojectbackend.dto.MessageDto;
import com.example.propertysearcherprojectbackend.dto.PropertyDto;
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
        setAppointmentToDtoConfiguration();
        setPropertyToDtoConfiguration();
    }
    public void setMessageToMessageDtoConfiguration() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TypeMap<Message, MessageDto> propertyMapper = modelMapper.createTypeMap(Message.class, MessageDto.class);
        propertyMapper.addMappings(mapper -> mapper.map(message -> message.getToUserMail().getUserId(), MessageDto::setToUserMail));
        propertyMapper.addMappings(mapper -> mapper.map(message -> message.getFromUserMail().getUserId(), MessageDto::setFromUserMail));
    }

    public void setAppointmentToDtoConfiguration() {
        TypeMap<Appointment, AppointmentDto> appointmentDtoTypeMap = modelMapper.createTypeMap(Appointment.class, AppointmentDto.class);
        appointmentDtoTypeMap.addMappings(mapper -> mapper.map(appointment -> appointment.getUser().getUserId(), AppointmentDto::setUserId));
        appointmentDtoTypeMap.addMappings(mapper -> mapper.map(appointment -> appointment.getProperty().getPropertyId(), AppointmentDto::setPropertyId));
    }
    public void setPropertyToDtoConfiguration() {
        TypeMap<Property, PropertyDto> propertyDtoTypeMap = modelMapper.createTypeMap(Property.class, PropertyDto.class);
        propertyDtoTypeMap.addMappings(mapper -> mapper.map(property -> property.getUser().getUserId(), PropertyDto::setUserId));
    }
}
