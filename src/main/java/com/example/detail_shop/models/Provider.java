package com.example.Shop.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Provider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_provider", unique = true, nullable = false)
    private Long id;

    @NotNull(message = "Это обязательное поле")
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, message = "Минимальная длина - 2 символа")
    @Size(max = 50, message = "Максимальная длина - 50 символов")
    @Pattern(regexp = "^.*[а-яА-Я0-9].*$", message = "Название поставщика должно состоять только из букв и цифр")
    private String name;

    @OneToMany(mappedBy = "provider", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<Product> products;

    public Provider() {
    }

    public Provider(String name, List<Product> products) {
        this.name = name;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
