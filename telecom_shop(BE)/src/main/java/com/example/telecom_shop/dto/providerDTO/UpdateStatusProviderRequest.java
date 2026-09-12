package com.example.telecom_shop.dto.providerDTO;

import com.example.telecom_shop.enums.ActivationStatus;

public class UpdateStatusProviderRequest {
    private ActivationStatus status;

    public ActivationStatus getStatus() {
        return status;
    }

    public void setStatus(ActivationStatus status) {
        this.status = status;
    }
}
