package com.example.telecom_shop.repository;

import com.example.telecom_shop.dto.servicePackage.PackageDetailDTO;
import com.example.telecom_shop.models.ServicePackage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServicePackageRepository extends JpaRepository<ServicePackage, Integer> {

    Optional<ServicePackage> findByCode(String code);
}
