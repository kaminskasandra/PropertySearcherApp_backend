package com.example.propertysearcherprojectbackend.service;

import com.example.propertysearcherprojectbackend.domain.RealEstateAgency;
import com.example.propertysearcherprojectbackend.dto.RealEstateAgencyDto;
import com.example.propertysearcherprojectbackend.exceptions.RealEstateAgencyNotFoundException;
import com.example.propertysearcherprojectbackend.repository.RealEstateAgencyRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RealEstateAgencyService {
    private final RealEstateAgencyRepository realEstateAgencyRepository;
    private final ModelMapper modelMapper;

    public List<RealEstateAgency> getAllAgencies() {
        return realEstateAgencyRepository.findAll();
    }

    public Optional<RealEstateAgency> getAgency(final Long agencyId) {
        return realEstateAgencyRepository.findById(agencyId);
    }

    public RealEstateAgency saveAgency(RealEstateAgencyDto realEstateAgencyDto) {
        return realEstateAgencyRepository.save(modelMapper.map(realEstateAgencyDto, RealEstateAgency.class));
    }

    public void deleteAgencyById(Long agencyId) {
        realEstateAgencyRepository.deleteById(agencyId);
    }

    public RealEstateAgency updateAgency(RealEstateAgencyDto agencyDto, Long agencyId) throws RealEstateAgencyNotFoundException {
        if (!realEstateAgencyRepository.existsById(agencyId)) {
            throw new RealEstateAgencyNotFoundException(String.format("Agency with id %s not found", agencyDto.getAgencyId()));
        } else {
            agencyDto.setAgencyId(agencyId);
            return saveAgency(agencyDto);
        }
    }
}
