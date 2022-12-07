package com.example.Shop.config.enums;

public enum StatusEnum {
    Processing("Собирается"),
    Transit("В пути"),
    Arrived("Прибыл на пункт выдачи"),
    Ready("Готов к выдаче"),
    Success("Получен")
    ;
    private String status;

    public String getStatus() {
        return status;
    }

    StatusEnum(String status) {
        this.status = status;
    }
}
