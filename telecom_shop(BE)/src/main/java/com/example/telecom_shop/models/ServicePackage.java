package com.example.telecom_shop.models;

import com.example.telecom_shop.enums.ActivationStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "service_packages")
public class ServicePackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price", nullable = false)
    private double price;
    @Column(name = "duration_days")
    private int duration_days;
    @Column(name = "data_amount")
    private double data_amount;
    @Column(name = "voice_munites")
    private int voice_minutes;
    @Column(name = "sms_count")
    private int sms_count;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ActivationStatus status;
    @Column(name = "created_at")
    private LocalDate created_at;
    @Column(name = "updated_at")
    private LocalDate updated_at;
    @Column(name = "code", nullable = false)
    private String code;

    @ManyToOne
    @JoinColumn(name = "provider_id")
    private Provider provider;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private ServiceCategories category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDuration_days() {
        return duration_days;
    }

    public void setDuration_days(int duration_days) {
        this.duration_days = duration_days;
    }

    public double getData_amount() {
        return data_amount;
    }

    public void setData_amount(double data_amount) {
        this.data_amount = data_amount;
    }

    public int getVoice_minutes() {
        return voice_minutes;
    }

    public void setVoice_minutes(int voice_minutes) {
        this.voice_minutes = voice_minutes;
    }

    public int getSms_count() {
        return sms_count;
    }

    public void setSms_count(int sms_count) {
        this.sms_count = sms_count;
    }

    public ActivationStatus getStatus() {
        return status;
    }

    public void setStatus(ActivationStatus status) {
        this.status = status;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }

    public LocalDate getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDate updated_at) {
        this.updated_at = updated_at;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public ServiceCategories getCategory() {
        return category;
    }

    public void setCategory(ServiceCategories category) {
        this.category = category;
    }
}
