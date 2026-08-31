package com.example.telecom_shop.service;

import com.example.telecom_shop.dto.categoriesDTO.ServiceCategoriesResponseDTO;
import com.example.telecom_shop.models.ServiceCategories;
import com.example.telecom_shop.repository.ServiceCategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesService {
    @Autowired
    ServiceCategoriesRepository serviceCategoriesRepository;

    public List<ServiceCategories> getAllServiceCategories() {
        return serviceCategoriesRepository.findAll();
    }

    public ServiceCategoriesResponseDTO getServiceCategoriesById(Integer id) {
        ServiceCategories serviceCategories = serviceCategoriesRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy loại dịch vụ "));
        ServiceCategoriesResponseDTO response = new ServiceCategoriesResponseDTO();
        response.setId(serviceCategories.getId());
        response.setName(serviceCategories.getName());
        response.setDescription(serviceCategories.getDescription());
        response.setStatus(serviceCategories.getStatus());
        response.setCreated_at(serviceCategories.getCreated_at());
        response.setUpdated_at(serviceCategories.getUpdated_at());

        return response;
    }
}
