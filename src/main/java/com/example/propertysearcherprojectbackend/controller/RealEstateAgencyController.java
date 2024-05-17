package com.example.propertysearcherprojectbackend.controller;

import com.example.propertysearcherprojectbackend.domain.RealEstateAgency;
import com.example.propertysearcherprojectbackend.dto.RealEstateAgencyDto;
import com.example.propertysearcherprojectbackend.exceptions.RealEstateAgencyNotFoundException;
import com.example.propertysearcherprojectbackend.service.RealEstateAgencyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/agencies")
public class RealEstateAgencyController {
    private final ModelMapper modelMapper;
    private final RealEstateAgencyService realEstateAgencyService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RealEstateAgencyDto> saveAgency(@RequestBody RealEstateAgencyDto realEstateAgencyDto) {
        return ResponseEntity.ok(modelMapper.map(realEstateAgencyService.saveAgency(realEstateAgencyDto), RealEstateAgencyDto.class));
    }

    @DeleteMapping(value = "{agencyId}")
    public ResponseEntity<Void> deleteAgency(@PathVariable Long agencyId) throws RealEstateAgencyNotFoundException {
        realEstateAgencyService.deleteAgencyById(agencyId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "{agencyId}")
    public ResponseEntity<RealEstateAgencyDto> getAgency(@PathVariable Long agencyId) throws RealEstateAgencyNotFoundException {
        return ResponseEntity.ok(modelMapper.map(realEstateAgencyService.getAgency(agencyId), RealEstateAgencyDto.class));
    }

    @GetMapping
    public List<RealEstateAgencyDto> findAllAgency() {
        List<RealEstateAgency> agencies = realEstateAgencyService.getAllAgencies();
        return agencies.stream()
                .map(agency -> modelMapper.map(agency, RealEstateAgencyDto.class))
                .collect(Collectors.toList());
    }

    @PutMapping(value = "{agencyId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RealEstateAgencyDto> updateAgency(@RequestBody RealEstateAgencyDto realEstateAgencyDto, @PathVariable Long agencyId) throws RealEstateAgencyNotFoundException {
        return ResponseEntity.ok(modelMapper.map(realEstateAgencyService.updateAgency(realEstateAgencyDto, agencyId), RealEstateAgencyDto.class));
    }
}

