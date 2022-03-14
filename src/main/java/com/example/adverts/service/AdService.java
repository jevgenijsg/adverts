package com.example.adverts.service;

import com.example.adverts.domain.Ad;
import com.example.adverts.domain.Category;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public interface AdService {
    Ad createAd(String text, BigDecimal price, Date expiryDate, Category category);

    Ad getAdById(Long id);

    Ad updateAd(Long id, String text, BigDecimal price, Category category, Date expiryDate);

    void deleteAdById(Long id);

    List<Ad> getAllAdds();

    List<Ad> filterAdsByCategory(Category category);
}
