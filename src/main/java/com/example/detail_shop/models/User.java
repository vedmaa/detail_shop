package com.example.Shop.models;

import com.example.Shop.config.enums.RoleEnum;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", unique = true, nullable = false)
    private Long id;

    @NotNull(message = "Это обязательное поле")
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 3, message = "Минимальная длина - 3 символа")
    @Size(max = 20, message = "Максимальная длина - 20 символов")
    @Pattern(regexp = "^.*[a-zA-Z].*$", message = "Логин должен содержать хотя бы одну букву")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Логин должен состоять только из букв и цифр")
    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @NotNull(message = "Это обязательное поле")
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 3, message = "Минимальная длина - 3 символа")
    @Size(max = 20, message = "Максимальная длина - 20 символов")
    @Pattern(regexp = "^.*[A-Z].*$", message = "Пароль должен содержать хотя бы одну прописную букву")
    @Pattern(regexp = "^.*[a-z].*$", message = "Пароль должен содержать хотя бы одну строчную букву")
    @Pattern(regexp = "^.*\\d.*$", message = "Пароль должен содержать хотя бы одну цифру")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Пароль должен состоять только из букв и цифр")
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull(message = "Это обязательное поле")
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, message = "Минимальная длина - 2 символа")
    @Size(max = 30, message = "Максимальная длина - 30 символов")
    @Pattern(regexp = "^.*[а-яА-Я].*$", message = "Фамилия должна состоять только из букв")
    @Column(name = "surname", nullable = false)
    private String surname;

    @NotNull(message = "Это обязательное поле")
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, message = "Минимальная длина - 2 символа")
    @Size(max = 30, message = "Максимальная длина - 30 символов")
    @Pattern(regexp = "^.*[а-яА-Я].*$", message = "Имя должно состоять только из букв")
    @Column(name = "name", nullable = true)
    private String name;

    @Pattern(regexp = "^.*[а-яА-Я].*$", message = "Отчество должно состоять только из букв")
    @Size(max = 30, message = "Максимальная длина - 30 символов")
    @Nullable
    @Pattern(regexp = "^.*[а-яА-Я].*$", message = "Имя должно состоять только из букв")
    private String patronymic;

    @Enumerated(value = EnumType.STRING)
    private RoleEnum role;

    public User(String login, String password, String surname, String name, @Nullable String patronymic, RoleEnum role) {
        this.login = login;
        this.password = password;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.role = role;
    }

    public User fromModel(User model) {
        return new User(model.login, model.password, model.surname, model.name, model.patronymic, model.role);
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(@Nullable String patronymic) {
        this.patronymic = patronymic;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public String getFullName(User user) {
        return user.getSurname() + " " + user.getName() + " " + user.getPatronymic();
    }
}
