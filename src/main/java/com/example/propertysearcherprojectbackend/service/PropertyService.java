package com.example.propertysearcherprojectbackend.service;

import com.example.propertysearcherprojectbackend.domain.AuditMessage;
import com.example.propertysearcherprojectbackend.domain.Property;
import com.example.propertysearcherprojectbackend.dto.PropertyDto;
import com.example.propertysearcherprojectbackend.exceptions.PropertyNotFoundException;
import com.example.propertysearcherprojectbackend.repository.PropertyRepository;
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

    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    public Optional<Property> getProperty(final Long propertyId) throws PropertyNotFoundException {
        if (!propertyRepository.existsById(propertyId)) {
            throw new PropertyNotFoundException("Property with id %s not found");
        } else {
            return propertyRepository.findById(propertyId);
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

    public Property saveProperty(PropertyDto propertyDto) {
       Property property = propertyRepository.save(modelMapper.map(propertyDto, Property.class));
                auditMessageService.saveAuditMessage(AuditMessage.builder()
                .message("New property added: " + property.getPropertyType() + " " + property.getAddress())
                .createdAt(LocalDateTime.now())
                .build());

        return property;
    }

    public Property updateProperty(PropertyDto propertyDto, Long propertyId) throws PropertyNotFoundException {
        if (!propertyRepository.existsById(propertyId)) {
            throw new PropertyNotFoundException(String.format("Property with id %s not found", propertyDto.getPropertyId()));
        } else {
            propertyDto.setPropertyId(propertyId);
            return saveProperty(propertyDto);
        }
    }
}