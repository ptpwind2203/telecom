package com.example.telecom_shop.controller;

import com.example.telecom_shop.dto.servicePackage.PackageDetailDTO;
import com.example.telecom_shop.dto.servicePackage.PackageResponseDTO;
import com.example.telecom_shop.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/package")
@CrossOrigin(origins = "http://localhost:5173")
public class ServicePackageController {
    @Autowired
    private PackageService packageService;


    @GetMapping("/list")
    public List<PackageResponseDTO> listAllPackage() {
        return packageService.getAllPackage() ;
    }

    @GetMapping("/detail/{code}")
    public ResponseEntity<PackageDetailDTO> getPackageDetail(@PathVariable String code) {
        PackageDetailDTO packageDetailDTO = packageService.getPackageDetailByCode(code);
        return ResponseEntity.ok(packageDetailDTO);
    }





}
