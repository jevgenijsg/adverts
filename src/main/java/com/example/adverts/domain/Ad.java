package com.example.adverts.domain;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;


//@JsonIgnoreProperties({"hibernateLazyInitializer"}) - if use getById() cannot get error No serializer found for class
// org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor and no properties discovered to create BeanSerializer
// (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS). This error can be solved using this annotation
@Entity
public class Ad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private java.lang.String text;
    private boolean isActive;
    private BigDecimal price;


    @Enumerated(value = EnumType.STRING)
    private Category category;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date expiryDate;

    public Ad(String text, BigDecimal price, Category category, Date expiryDate) {
        this.text = text;
        this.isActive = true;
        this.price = price;
        this.category = category;
        this.expiryDate = expiryDate;
    }

    public Ad() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public java.lang.String getText() {
        return text;
    }

    public void setText(java.lang.String text) {
        this.text = text;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}

