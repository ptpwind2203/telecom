package com.example.telecom_shop.dto.userDTO;

public class UserPasswordDTO {
    private String password_old;
    private String password_new;
    private String comfirm_password;

    public String getPassword_old() {
        return password_old;
    }

    public void setPassword_old(String password_old) {
        this.password_old = password_old;
    }

    public String getPassword_new() {
        return password_new;
    }

    public void setPassword_new(String password_new) {
        this.password_new = password_new;
    }

    public String getComfirm_password() {
        return comfirm_password;
    }

    public void setComfirm_password(String comfirm_password) {
        this.comfirm_password = comfirm_password;
    }
}
