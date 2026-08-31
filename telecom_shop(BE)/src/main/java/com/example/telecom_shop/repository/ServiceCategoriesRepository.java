package com.example.telecom_shop.repository;

import com.example.telecom_shop.models.ServiceCategories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceCategoriesRepository extends JpaRepository<ServiceCategories, Integer> {

    boolean existsById(Integer id);

    Optional<ServiceCategories> findById(Integer id);
}
