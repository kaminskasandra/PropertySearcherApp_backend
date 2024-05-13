package com.example.propertysearcherprojectbackend.repository;

import com.example.propertysearcherprojectbackend.domain.Property;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PropertyRepository extends JpaRepository<Property, Long> {
}
