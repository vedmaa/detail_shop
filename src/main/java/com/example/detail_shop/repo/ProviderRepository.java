package com.example.Shop.repo;

import com.example.Shop.models.Provider;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProviderRepository extends CrudRepository<Provider, Long> {

    List<Provider> findProviderByNameContains(String provider_name);
}
