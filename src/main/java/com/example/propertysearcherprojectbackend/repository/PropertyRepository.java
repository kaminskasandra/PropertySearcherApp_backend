package com.example.propertysearcherprojectbackend.repository;

import com.example.propertysearcherprojectbackend.domain.Property;
import com.example.propertysearcherprojectbackend.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface PropertyRepository extends JpaRepository<Property, Long> {
   List<Property> getPropertiesByUser(User user);
}
