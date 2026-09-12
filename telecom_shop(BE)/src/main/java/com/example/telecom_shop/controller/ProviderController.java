package com.example.telecom_shop.controller;

import com.example.telecom_shop.dto.providerDTO.CreateProviderRequest;
import com.example.telecom_shop.dto.providerDTO.ProviderResponseDTO;
import com.example.telecom_shop.dto.providerDTO.UpdateProviderRequest;
import com.example.telecom_shop.dto.providerDTO.UpdateStatusProviderRequest;
import com.example.telecom_shop.models.Provider;
import com.example.telecom_shop.service.ProviderService;
import jakarta.websocket.server.PathParam;
import org.apache.coyote.BadRequestException;
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


    @GetMapping("/detail/{code}")
    public ResponseEntity<ProviderResponseDTO> detailProvider(@PathVariable("code") String code) {
        ProviderResponseDTO providerResponseDTO = providerService.getProviderByCode(code);
        return ResponseEntity.ok(providerResponseDTO);
    }

    @GetMapping("/list")
    public List<Provider> getAllProviders() {
        return providerService.getAllProviders();
    }


    @PostMapping("/create")
    public ResponseEntity<String> createProvider(@RequestBody CreateProviderRequest request){
        providerService.createProvider(request);
        return ResponseEntity.ok("Thêm mới nhà cung cấp " + request.getName() + " thành công!");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProviderResponseDTO> updateProvider(@PathVariable("id") Integer id, @RequestBody UpdateProviderRequest request){
        ProviderResponseDTO responseDTO = providerService.updateProvider(id, request);
        return  ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/update-status/{id}")
    public ResponseEntity<String> updateStatusProvider(@PathParam("id") Integer id, @RequestBody UpdateStatusProviderRequest request){
        providerService.updatedStatusProvider(id, request );
        return ResponseEntity.ok("Cập nhập trạng thái thành công ");
    }

}
