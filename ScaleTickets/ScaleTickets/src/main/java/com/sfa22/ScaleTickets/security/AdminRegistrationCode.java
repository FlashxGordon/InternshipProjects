package com.sfa22.ScaleTickets.security;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "admin_registration_codes")
public class AdminRegistrationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_id", unique = true, updatable = false, insertable = false, nullable = false)
    private int id;


    @Column(name = "code", length = 100, unique = true, updatable = true, insertable = true, nullable = false)
    private String code;

    public AdminRegistrationCode() {
    }

    public AdminRegistrationCode(int id, String code) {
        this.id = id;
        this.code = code;
    }

    public AdminRegistrationCode(String code) {
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdminRegistrationCode)) return false;
        AdminRegistrationCode that = (AdminRegistrationCode) o;
        return getId() == that.getId() &&
                Objects.equals(getCode(), that.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCode());
    }
}