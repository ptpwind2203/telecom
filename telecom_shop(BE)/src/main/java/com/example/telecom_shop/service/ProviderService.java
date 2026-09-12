package com.example.telecom_shop.service;

import com.example.telecom_shop.dto.providerDTO.CreateProviderRequest;
import com.example.telecom_shop.dto.providerDTO.ProviderResponseDTO;
import com.example.telecom_shop.dto.providerDTO.UpdateProviderRequest;
import com.example.telecom_shop.dto.providerDTO.UpdateStatusProviderRequest;
import com.example.telecom_shop.enums.ActivationStatus;
import com.example.telecom_shop.models.Provider;
import com.example.telecom_shop.repository.ProviderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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


    public void createProvider(CreateProviderRequest req) {
        if (!providerRepository.existsByCode(req.getCode())) {
            throw new RuntimeException("Mã nhà cung cấp này đã tồn tại");
        };

        Provider provider = new Provider();
        provider.setName(req.getName());
        provider.setCode(req .getCode());
        provider.setDescription(req.getDescription());
        provider.setStatus(ActivationStatus.ACTIVE);
        provider.setLogo_url(req.getLogoURL());
        provider.setCreated_at(LocalDate.now());
        provider.setUpdated_at(LocalDate.now());

        providerRepository.save(provider);

    }

    public ProviderResponseDTO updateProvider(Integer id, UpdateProviderRequest req) {
        Provider provider = providerRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy ID của nhà cung câp"));

        if (providerRepository.existsByCodeAndIdNot(req.getCode(), id)) {
            throw new RuntimeException("Mã Code đã tồn tại");
        }
        provider.setName(req.getName());
        provider.setCode(req.getCode());
        provider.setDescription(req.getDescription());
        provider.setLogo_url(req.getLogo_url());
        provider.setUpdated_at(LocalDate.now());

        providerRepository.save(provider);

        ProviderResponseDTO response = new ProviderResponseDTO();
        response.setCode(provider.getCode());
        response.setName(provider.getName());
        response.setLogo_url(provider.getLogo_url());
        response.setDescription(provider.getDescription());
        response.setStatus(provider.getStatus());
        response.setCreated_at(provider.getCreated_at());
        response.setUpdated_at(provider.getUpdated_at());

        return response;
    }

    public void updatedStatusProvider(Integer id, UpdateStatusProviderRequest req) {
        Provider provider = providerRepository.findById(id).orElseThrow(() -> new RuntimeException("ID không tồn tại"));
        provider.setUpdated_at(LocalDate.now());
        provider.setStatus(req.getStatus());
        providerRepository.save(provider);
    }

}
