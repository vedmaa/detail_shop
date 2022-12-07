package com.example.Shop.models;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product", unique = true, nullable = false)
    private Long id;

    @NotNull(message = "Это обязательное поле")
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, message = "Минимальная длина - 2 символа")
    @Size(max = 50, message = "Максимальная длина - 50 символов")
    @Pattern(regexp = "^.*[а-яА-Я0-9].*$", message = "Название должно состоять только из букв и цифр")
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull(message = "Это обязательное поле")
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, message = "Минимальная длина - 2 символа")
    @Size(max = 255, message = "Максимальная длина - 255 символов")
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull(message = "Это обязательное поле")
    @Positive(message = "Цена должна быть больше 0")
    private Double price;

    @NotNull(message = "Это обязательное поле")
    @Positive(message = "Количество товара не может быть отрицательным числом")
    private Integer count;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;


    public Product() {
    }

    public Product(String title, String description, Double price, Integer count, Category category, Provider provider) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.count = count;
        this.category = category;
        this.provider = provider;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

}
