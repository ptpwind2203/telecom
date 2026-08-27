package com.example.telecom_shop.controller;

import com.example.telecom_shop.dto.providerDTO.ProviderResponseDTO;
import com.example.telecom_shop.models.Provider;
import com.example.telecom_shop.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/provider")
@CrossOrigin(origins = "http://localhost:5173")
public class ProviderController {
    @Autowired
    private ProviderService providerService;


    @GetMapping("/detail-provider/{code}")
    public ResponseEntity<ProviderResponseDTO> detailProvider(@PathVariable("code") String code) {
        ProviderResponseDTO providerResponseDTO = providerService.getProviderByCode(code);
        return ResponseEntity.ok(providerResponseDTO);
    }

    @GetMapping("/list-provider")
    public List<Provider> getAllProviders() {
        return providerService.getAllProviders();
    }
}
