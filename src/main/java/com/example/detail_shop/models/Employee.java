package com.example.Shop.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_employee", unique = true, nullable = false)
    private Long id;

    @NotNull(message = "Это обязательное поле")
    @Positive(message = "Заработная плата не может быть отрицательным числом")
    @Max(message = "Заработная плата не может быть больее 1 млрд.", value = 1000000000)

    private Integer salary;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id_user")
    private User user;

    public Employee() {
    }

    public Employee(Integer salary, User user) {
        this.salary = salary;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFullName() {
        return this.user.getSurname() + " " + this.user.getName() + " " + this.user.getPatronymic();
    }

    @Override
    public String toString() {
        return "Должность: " + user.getRole().getDisplayedName() + "\nЗарплата: " + salary + " руб.";
    }
}
