package com.example.telecom_shop.service;

import com.example.telecom_shop.dto.servicePackage.PackageResponseDTO;
import com.example.telecom_shop.models.ServicePackage;
import com.example.telecom_shop.repository.ServiceCategoriesRepository;
import com.example.telecom_shop.repository.ServicePackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PackageService {
    @Autowired
    ServicePackageRepository packageRepository;

    @Autowired
    ServiceCategoriesRepository categoriesRepository;

    @Autowired
    ProviderService providerService;

    public List<PackageResponseDTO> getAllPackage() {
        List<ServicePackage> packages = packageRepository.findAll();

        return packages.stream().map( packageEntity -> {
            PackageResponseDTO packageResponseDTO = new PackageResponseDTO();
            packageResponseDTO.setCode(packageEntity.getCode());
            packageResponseDTO.setName(packageEntity.getName());
            packageResponseDTO.setPrice(packageEntity.getPrice());
            packageResponseDTO.setDuration_days(packageEntity.getDuration_days());
            packageResponseDTO.setCategoryName(packageEntity.getCategory().getName());
            packageResponseDTO.setProviderName(packageEntity.getProvider().getName());
            return packageResponseDTO;
        }).toList();

    }


}
