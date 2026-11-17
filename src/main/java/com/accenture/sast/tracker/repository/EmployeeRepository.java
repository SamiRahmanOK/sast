package com.accenture.sast.tracker.repository;

import com.accenture.sast.tracker.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
