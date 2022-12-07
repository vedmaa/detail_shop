package com.example.Shop.repo;

import com.example.Shop.models.PickupPoint;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PointRepository extends CrudRepository<PickupPoint, Long> {
    List<PickupPoint> findByAddressContainsIgnoreCase(String address);
}
