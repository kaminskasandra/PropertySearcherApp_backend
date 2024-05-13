package com.example.propertysearcherprojectbackend.repository;

import com.example.propertysearcherprojectbackend.domain.RealEstateAgency;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface RealEstateAgencyRepository extends JpaRepository<RealEstateAgency, Long> {
}
