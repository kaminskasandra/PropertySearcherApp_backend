package com.example.propertysearcherprojectbackend.repository;

import com.example.propertysearcherprojectbackend.domain.Appointment;
import com.example.propertysearcherprojectbackend.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface AppointmentRepository  extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByUser(User user);
}
