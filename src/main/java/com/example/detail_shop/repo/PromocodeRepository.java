package com.example.Shop.repo;

import com.example.Shop.models.PromoCode;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PromocodeRepository extends CrudRepository<PromoCode, Long> {
    List<PromoCode> findPromoCodeByCode(String code);

    PromoCode findByCode(String code);
}
