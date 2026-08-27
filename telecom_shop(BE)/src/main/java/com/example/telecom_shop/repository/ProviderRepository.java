package com.example.telecom_shop.repository;

import com.example.telecom_shop.models.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProviderRepository extends JpaRepository<Provider, Integer> {

    boolean existsByCode(String Code);

    Optional<Provider> findByCode(String code);

}
