package com.example.propertysearcherprojectbackend.service;

import com.example.propertysearcherprojectbackend.domain.AuditMessage;
import com.example.propertysearcherprojectbackend.domain.RealEstateAgency;
import com.example.propertysearcherprojectbackend.dto.RealEstateAgencyDto;
import com.example.propertysearcherprojectbackend.exceptions.RealEstateAgencyNotFoundException;
import com.example.propertysearcherprojectbackend.repository.RealEstateAgencyRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RealEstateAgencyService {
    private final RealEstateAgencyRepository realEstateAgencyRepository;
    private final AuditMessageService auditMessageService;
    private final ModelMapper modelMapper;

    public List<RealEstateAgency> getAllAgencies() {
        return realEstateAgencyRepository.findAll();
    }

    public Optional<RealEstateAgency> getAgency(final Long agencyId) throws RealEstateAgencyNotFoundException {
        if (!realEstateAgencyRepository.existsById(agencyId)) {
            throw new RealEstateAgencyNotFoundException("Agency with id %s not found");
        } else {
            return realEstateAgencyRepository.findById(agencyId);
        }
    }

    public RealEstateAgency saveAgency(RealEstateAgencyDto realEstateAgencyDto) {
        RealEstateAgency realEstateAgency = realEstateAgencyRepository.save(modelMapper.map(realEstateAgencyDto, RealEstateAgency.class));
        auditMessageService.saveAuditMessage(AuditMessage.builder()
                .message("New real estate agency added: " + realEstateAgency.getAgencyName())
                .createdAt(LocalDateTime.now())
                .build());
        return realEstateAgency;
    }

    public void deleteAgencyById(Long agencyId) throws RealEstateAgencyNotFoundException {
        if (!realEstateAgencyRepository.existsById(agencyId)) {
            throw new RealEstateAgencyNotFoundException("Agency with id %s not found");
        } else {
            auditMessageService.saveAuditMessage(AuditMessage.builder()
                    .message("Real estate agency deleted: agency id " + agencyId)
                    .createdAt(LocalDateTime.now())
                    .build());
            realEstateAgencyRepository.deleteById(agencyId);
        }
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
