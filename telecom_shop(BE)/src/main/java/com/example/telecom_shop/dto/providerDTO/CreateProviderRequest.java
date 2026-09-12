package com.example.telecom_shop.dto.providerDTO;

import com.example.telecom_shop.enums.ActivationStatus;

import java.time.LocalDate;

public class CreateProviderRequest {
    private String name;
    private String logoURL;
    private String description;
    private String code;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code){
        this.code = code;
    }
}
