package com.example.telecom_shop.service;

import com.example.telecom_shop.repository.ServiceCategoriesRepository;
import com.example.telecom_shop.repository.ServicePackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PackageService {
    @Autowired
    ServicePackageRepository packageRepository;

    @Autowired
    ServiceCategoriesRepository categoriesRepository;


}
