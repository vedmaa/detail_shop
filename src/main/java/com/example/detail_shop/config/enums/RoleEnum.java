package com.example.Shop.config.enums;

import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.List;


public enum RoleEnum implements GrantedAuthority {
    User("Пользователь", false),
    HR("Кадровый менеджер", true),
    Cashier("Кассир", true),
    Administrator("Администратор", true),
    Manager("Менеджер товаров", true);
    private final String displayedName;
    private final Boolean position;

    public final static List<RoleEnum> positions = Arrays.stream(RoleEnum.values()).filter(roleEnum -> roleEnum.position).toList();

    RoleEnum(String name, Boolean position) {
        this.position = position;
        this.displayedName = name;
    }

    public String getDisplayedName() {
        return displayedName;
    }

    public Boolean getPosition() {
        return position;
    }

    @Override
    public String getAuthority() {
        return this.name();
    }

}
