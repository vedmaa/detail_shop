package com.example.Shop.models;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class PromoCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_code", unique = true, nullable = false)
    private Long id;

    @NotNull(message = "Это обязательное поле")
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 2, message = "Минимальная длина - 2 символа")
    @Size(max = 40, message = "Максимальная длина - 40 символов")
    @Pattern(regexp = "^.*[a-zA-Zа-яА-Я0-9].*$", message = "Промокод должен состоять только из букв и цифр")
    private String code;

    private Boolean isActive;

    @NotNull(message = "Это обязательное поле")
    @Min(value = 10, message = "Минимальный размер скидки 10%")
    @Max(value = 100, message = "Максимальный размер скидки 100%")
    private Integer discontAmount;

    @Override
    public String toString() {
        return "Скидка " + discontAmount + "%";
    }

    public PromoCode() {
    }

    public PromoCode(String code, Boolean isActive, Integer discontAmount) {
        this.code = code;
        this.isActive = isActive;
        this.discontAmount = discontAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Integer getDiscontAmount() {
        return discontAmount;
    }

    public void setDiscontAmount(Integer discontAmount) {
        this.discontAmount = discontAmount;
    }
}
