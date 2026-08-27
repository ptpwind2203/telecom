package com.example.telecom_shop.service;

import com.example.telecom_shop.dto.providerDTO.ProviderResponseDTO;
import com.example.telecom_shop.models.Provider;
import com.example.telecom_shop.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderService {
    @Autowired
    private ProviderRepository providerRepository;

    public ProviderResponseDTO getProviderByCode(String code) {
        Provider provider = providerRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhà cung cấp trùng khớp"));

        ProviderResponseDTO providerResponseDTO = new ProviderResponseDTO();
        providerResponseDTO.setCode(provider.getCode());
        providerResponseDTO.setName(provider.getName());
        providerResponseDTO.setLogo_url(provider.getLogo_url());
        providerResponseDTO.setDescription(provider.getDescription());
        providerResponseDTO.setStatus(provider.getStatus());
        providerResponseDTO.setCreated_at(provider.getCreated_at());
        providerResponseDTO.setUpdated_at(provider.getUpdated_at());
        providerResponseDTO.setId(provider.getId());
        return providerResponseDTO;
    }

    public List<Provider> getAllProviders() {
        return providerRepository.findAll();
    }

}
