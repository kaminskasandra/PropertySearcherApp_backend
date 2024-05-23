package com.example.propertysearcherprojectbackend.service;

import com.example.propertysearcherprojectbackend.domain.Property;
import com.example.propertysearcherprojectbackend.domain.User;
import com.example.propertysearcherprojectbackend.exceptions.PropertyNotFoundException;
import com.example.propertysearcherprojectbackend.exceptions.UserNotFoundException;
import com.example.propertysearcherprojectbackend.repository.PropertyRepository;
import com.example.propertysearcherprojectbackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PropertyServiceTest {
    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuditMessageService auditMessageService;

    @InjectMocks
    private PropertyService propertyService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testGetAllProperties() {
        // Given
        List<Property> properties = new ArrayList<>();
        properties.add(new Property());
        when(propertyRepository.findAll()).thenReturn(properties);

        // When
        List<Property> result = propertyService.getAllProperties();

        // Then
        assertNotNull(result);
        assertEquals(properties.size(), result.size());
    }

    @Test
    public void testGetPropertiesByUser() throws UserNotFoundException {
        // Given
        Long userId = 1L;
        User user = new User();
        user.setUserId(userId);

        List<Property> properties = new ArrayList<>();
        properties.add(new Property());
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(propertyRepository.getPropertiesByUser(user)).thenReturn(properties);

        // When
        List<Property> result = propertyService.getPropertiesByUser(userId);

        // Then
        assertNotNull(result);
        assertEquals(properties.size(), result.size());
    }
    @Test
    public void testGetProperty() throws PropertyNotFoundException {
        // Given
        Long propertyId = 1L;
        Property property = new Property();
        when(propertyRepository.existsById(propertyId)).thenReturn(true);
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));

        // When
        Property result = propertyService.getProperty(propertyId);

        // Then
        assertNotNull(result);
    }

    @Test
    public void testDeletePropertyById() {
        // Given
        Long propertyId = 1L;
        when(propertyRepository.existsById(propertyId)).thenReturn(true);
        doNothing().when(auditMessageService).saveAuditMessage(any());

        // When
        assertDoesNotThrow(() -> propertyService.deletePropertyById(propertyId));

        // Then
        verify(propertyRepository, times(1)).deleteById(propertyId);
        verify(auditMessageService, times(1)).saveAuditMessage(any());
    }

}
