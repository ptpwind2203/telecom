package com.example.telecom_shop.controller;

import com.example.telecom_shop.dto.categoriesDTO.ServiceCategoriesResponseDTO;
import com.example.telecom_shop.models.ServiceCategories;
import com.example.telecom_shop.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service-categories")
@CrossOrigin(origins = "http://localhost:5173")
public class ServiceCategoriesController {

    @Autowired
    CategoriesService  categoriesService;

    @GetMapping("/list")
    public List<ServiceCategories> getAllServiceCategories() {
        return categoriesService.getAllServiceCategories();
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ServiceCategoriesResponseDTO> getServiceCategories(@PathVariable("id") Integer id) {
        ServiceCategoriesResponseDTO serviceCategoriesResponseDTO = categoriesService.getServiceCategoriesById(id);
        return ResponseEntity.ok(serviceCategoriesResponseDTO);
    }
}
