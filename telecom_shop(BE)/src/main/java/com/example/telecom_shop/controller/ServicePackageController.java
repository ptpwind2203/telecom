package com.example.telecom_shop.controller;

import com.example.telecom_shop.dto.servicePackage.PackageResponseDTO;
import com.example.telecom_shop.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/package")
@CrossOrigin(origins = "http://localhost:5173")
public class ServicePackageController {
    @Autowired
    private PackageService packageService;

    @GetMapping("/list-all-package")
    public List<PackageResponseDTO> listAllPackage() {
        return packageService.getAllPackage() ;
    }
}
