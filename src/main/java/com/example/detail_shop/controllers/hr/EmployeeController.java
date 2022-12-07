package com.example.Shop.controllers.hr;

import com.example.Shop.config.enums.RoleEnum;
import com.example.Shop.models.Employee;
import com.example.Shop.models.User;
import com.example.Shop.repo.EmployeeRepository;
import com.example.Shop.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/hr/employee")
@PreAuthorize("hasAnyAuthority('HR')")
public class EmployeeController {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String viewEmployee(@RequestParam(value = "query", required = false) String query, Model model) {
        Iterable<Employee> employees = employeeRepository.findAll();
        List<Employee> employeesList = new ArrayList<Employee>();
        employees.forEach(employeesList::add);
        if (query != null) {
            if (!query.isBlank()) {
                model.addAttribute("query", query);
                employeesList = employeesList.stream().filter(employee -> employee.getFullName().toLowerCase().contains(query.toLowerCase())).toList();
            }
        }

        model.addAttribute("models", employeesList);
        return "hr/main";
    }

    @GetMapping("/add")
    public String addEmployeeGet(User user, Employee employee, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("employee", employee);
        model.addAttribute("positions", RoleEnum.positions);
        return "/hr/addEmployee";
    }

    @PostMapping("/add")
    public String addEmployee(@Valid @ModelAttribute("employee") Employee employee,
                              BindingResult bindingResultEmployee,
                              @Valid @ModelAttribute("user") User user,
                              BindingResult bindingResult, Model model) {
        var positions = RoleEnum.positions;
        model.addAttribute("positions", positions);

        if (bindingResult.hasErrors() || bindingResultEmployee.hasErrors()) {
            return "hr/addEmployee";
        }

        if (userRepository.findUserByLogin(user.getLogin()).isPresent()) {
            bindingResult.rejectValue("login", "error.existing_login", "Данный логин уже занят");
            return "/hr/addEmployee";
        }

        employee.setUser(userRepository.save(user));
        employee.setSalary(employee.getSalary());
        employeeRepository.save(employee);
        return "redirect:/hr/employee";
    }

    @GetMapping("/edit/{id}")
    public String editGet(@PathVariable("id") Long id, Model model) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            return "redirect:/hr/employee";
        }
        model.addAttribute("employee", employee.get());
        model.addAttribute("id", employee.get().getId());
        model.addAttribute("user", employee.get().getUser());
        model.addAttribute("positions", RoleEnum.positions);
        return "hr/addEmployee";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,
                       @Valid @ModelAttribute("employee") Employee employee,
                       BindingResult bindingResultEmployee,
                       @Valid @ModelAttribute("user") User user,
                       BindingResult bindingResult, Model model) {

        var positions = RoleEnum.positions;
        model.addAttribute("positions", positions);

        model.addAttribute("id", id);

        Optional<User> profile = userRepository.findUserByLogin(user.getLogin());
        profile.get().setSurname(user.getSurname());
        profile.get().setName(user.getName());
        profile.get().setPatronymic(user.getPatronymic());
        profile.get().setRole(user.getRole());

        user.setPassword(profile.get().getPassword());

        if (bindingResult.getFieldErrors().stream().anyMatch((error) -> !error.getField().equals("password")) || bindingResultEmployee.hasErrors()) {
            return "hr/addEmployee";
        }

        employee.setUser(userRepository.save(profile.get()));
        employee.setSalary(employee.getSalary());

        employeeRepository.save(employee);
        return "redirect:/hr/employee/";
    }

    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Long id) {
        Employee employee = employeeRepository.findEmployeeById(id);
        employeeRepository.deleteById(id);
        userRepository.deleteById(employee.getUser().getId());
        return "redirect:/hr/employee";
    }
}
