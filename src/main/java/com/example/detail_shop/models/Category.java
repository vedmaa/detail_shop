package com.example.Shop.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;


@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_category", unique = true, nullable = false)
    private Long id;


    @NotNull(message = "Это обязательное поле")
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, message = "Минимальная длина - 2 символа")
    @Size(max = 30, message = "Максимальная длина - 30 символов")
    @Pattern(regexp = "^.*[а-яА-Я].*$", message = "Название категории должно состоять только из букв")
    private String category_name;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private List<Product> products;

    public Category() {
    }

    public Category(String category_name, List<Product> products) {
        this.category_name = category_name;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
