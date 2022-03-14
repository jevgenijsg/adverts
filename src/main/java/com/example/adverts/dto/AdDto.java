package com.example.adverts.dto;

import com.example.adverts.domain.Category;

import java.math.BigDecimal;
import java.sql.Date;

public class AdDto {

    private String text;
    private BigDecimal price;
    private Date expiryDate;
    private Category category;

    public AdDto(String text, BigDecimal price, Date expiryDate, Category category) {
        this.text = text;
        this.price = price;
        this.expiryDate = expiryDate;
        this.category = category;
    }

    public AdDto() {
    }

    public java.lang.String getText() {
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
}
