package com.example.telecom_shop.service;

import com.example.telecom_shop.dto.servicePackage.PackageDetailDTO;
import com.example.telecom_shop.dto.servicePackage.PackageResponseDTO;
import com.example.telecom_shop.models.ServicePackage;
import com.example.telecom_shop.repository.ServiceCategoriesRepository;
import com.example.telecom_shop.repository.ServicePackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public PackageDetailDTO getPackageDetailByCode(String code) {
        ServicePackage servicePackage = packageRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Không tìm thấy mã code "));
        PackageDetailDTO packageDetailDTO = new PackageDetailDTO();

        packageDetailDTO.setCode(servicePackage.getCode());
        packageDetailDTO.setCategoryName(servicePackage.getCategory().getName());
        packageDetailDTO.setProviderName(servicePackage.getProvider().getName());
        packageDetailDTO.setName(servicePackage.getName());
        packageDetailDTO.setPrice(servicePackage.getPrice());
        packageDetailDTO.setDescription(servicePackage.getDescription());
        packageDetailDTO.setDurationDays(servicePackage.getDuration_days());
        packageDetailDTO.setDataAmount(servicePackage.getData_amount());
        packageDetailDTO.setVoiceMinutes(servicePackage.getVoice_minutes());
        packageDetailDTO.setSmsCount(servicePackage.getSms_count());
        packageDetailDTO.setStatus(servicePackage.getStatus());

        return packageDetailDTO;
    }
}
