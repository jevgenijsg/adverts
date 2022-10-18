package com.example.adverts.dto;

import com.example.adverts.domain.Category;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Date;

public class AdDto {

    @NotNull
    private Long id;
    private String text;
    private BigDecimal price;
    private boolean isActive;
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date expiryDate;
    @Enumerated(value = EnumType.STRING)
    private Category category;

    public AdDto(Long id, String text, BigDecimal price, Date expiryDate, Category category) {
        this.id = id;
        this.text = text;
        this.price = price;
        this.isActive = true;
        this.expiryDate = expiryDate;
        this.category = category;
    }

    public AdDto() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

}
