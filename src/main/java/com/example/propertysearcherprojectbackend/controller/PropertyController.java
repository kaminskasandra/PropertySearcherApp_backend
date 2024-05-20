package com.example.propertysearcherprojectbackend.controller;

import com.example.propertysearcherprojectbackend.domain.Property;
import com.example.propertysearcherprojectbackend.dto.PropertyDto;
import com.example.propertysearcherprojectbackend.exceptions.PropertyNotFoundException;
import com.example.propertysearcherprojectbackend.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/property")
public class PropertyController {
    private final ModelMapper modelMapper;
    private final PropertyService propertyService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PropertyDto> saveProperty(@RequestBody PropertyDto propertyDto) {
        return ResponseEntity.ok(modelMapper.map(propertyService.saveProperty(propertyDto), PropertyDto.class));
    }

    @DeleteMapping(value = "{propertyId}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Long propertyId) throws PropertyNotFoundException{
        propertyService.deletePropertyById(propertyId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<PropertyDto> findAll() {
        List<Property> properties = propertyService.getAllProperties();
        return properties.stream()
                .map(property -> modelMapper.map(property, PropertyDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "{propertyId}")
    public ResponseEntity<PropertyDto> getPropertyById(@PathVariable Long propertyId) throws PropertyNotFoundException {
        return ResponseEntity.ok(modelMapper.map(propertyService.getProperty(propertyId), PropertyDto.class));
    }

    @PutMapping(value = "{propertyId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PropertyDto> updateProperty(@RequestBody PropertyDto propertyDto, @PathVariable Long propertyId) throws PropertyNotFoundException {
        return ResponseEntity.ok(modelMapper.map(propertyService.updateProperty(propertyDto, propertyId), PropertyDto.class));
    }
}
