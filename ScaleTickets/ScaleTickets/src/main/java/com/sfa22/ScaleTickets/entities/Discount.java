package com.sfa22.ScaleTickets.entities;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "discount_codes")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discount_id", updatable = false, nullable = false)
    int discountId;

    @Column(name = "percentage", unique = false, updatable = true, insertable = true, nullable = false)
    int percentage;

    @Column(name = "code", length = 15, unique = true, updatable = true, insertable = true, nullable = false)
    String code;

    @Column(name = "expiration_date", updatable = true, insertable = true, nullable = false)
    LocalDate expirationDate;

    @OneToMany(mappedBy = "discount", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    public Discount() {
    }

    public Discount(int discountId, int percentage, String code, LocalDate expirationDate) {
        this.discountId = discountId;
        this.percentage = percentage;
        this.code = code;
        this.expirationDate = expirationDate;
    }

    public Discount(int percentage, String code, LocalDate expirationDate) {
        this.percentage = percentage;
        this.code = code;
        this.expirationDate = expirationDate;
    }

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return discountId == discount.discountId
                && percentage == discount.percentage
                && code.equals(discount.code)
                && expirationDate.equals(discount.expirationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(discountId, percentage, code, expirationDate);
    }

}
