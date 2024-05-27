package com.example.propertysearcherprojectbackend.service;

import com.example.propertysearcherprojectbackend.domain.AuditMessage;
import com.example.propertysearcherprojectbackend.domain.Property;
import com.example.propertysearcherprojectbackend.domain.User;
import com.example.propertysearcherprojectbackend.dto.PropertyDto;
import com.example.propertysearcherprojectbackend.exceptions.PropertyNotFoundException;
import com.example.propertysearcherprojectbackend.exceptions.UserNotFoundException;
import com.example.propertysearcherprojectbackend.repository.PropertyRepository;
import com.example.propertysearcherprojectbackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PropertyService {
    private final ModelMapper modelMapper;
    private final AuditMessageService auditMessageService;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    @Transactional
    public List<Property> getPropertiesByUser(Long userId) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return propertyRepository.getPropertiesByUser(user);
        } else {
            throw new UserNotFoundException("User with id %s not found");
        }
    }

    public Property getProperty(final Long propertyId) throws PropertyNotFoundException {
        if (!propertyRepository.existsById(propertyId)) {
            throw new PropertyNotFoundException("Property with id %s not found");
        } else {
            return propertyRepository.findById(propertyId).get();
        }
    }

    public void deletePropertyById(Long propertyId) throws PropertyNotFoundException {
        if (!propertyRepository.existsById(propertyId)) {
            throw new PropertyNotFoundException("Property with id %s not found");
        } else {
            auditMessageService.saveAuditMessage(AuditMessage.builder()
                    .message("Property deleted: property id " + propertyId)
                    .createdAt(LocalDateTime.now())
                    .build());
            propertyRepository.deleteById(propertyId);
        }
    }

    @Transactional
    public Property saveProperty(PropertyDto propertyDto) throws UserNotFoundException {
        Property property = propertyRepository.save(modelMapper.map(propertyDto, Property.class));
        User user = userService.getUser(propertyDto.getUserId());
        property.setUser(user);
        property = propertyRepository.save(property);
        auditMessageService.saveAuditMessage(AuditMessage.builder()
                .message("New property added: " + property.getPropertyType() + " " + property.getAddress())
                .createdAt(LocalDateTime.now())
                .build());

        return property;
    }

    public Property updateProperty(PropertyDto propertyDto, Long propertyId) throws PropertyNotFoundException, UserNotFoundException {
        if (!propertyRepository.existsById(propertyId)) {
            throw new PropertyNotFoundException(String.format("Property with id %s not found", propertyDto.getPropertyId()));
        } else {
            propertyDto.setPropertyId(propertyId);
            return saveProperty(propertyDto);
        }
    }
}
