package com.example.Shop.repo;

import com.example.Shop.models.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    List<Employee> findEmployeeBySalaryContains(String surname);
    List<Employee>findEmployeeBySalary(String surname); //Точный поиск

    Employee findEmployeeById(Long id);
}
