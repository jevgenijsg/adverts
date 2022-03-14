package com.example.adverts.service.impl;

import com.example.adverts.domain.Ad;
import com.example.adverts.domain.Category;
import com.example.adverts.repository.AdRepository;
import com.example.adverts.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdServiceImpl implements AdService {

    @Autowired
    AdRepository adRepository;

    @Override
    public Ad createAd(String text, BigDecimal price, Date expiryDate, Category category) {

        Ad ad = setupAd(text, price, expiryDate, category);
        if(adRepository.exists(Example.of(ad))){
            return null;
        } else
            return adRepository.save(ad);
    }

    @Override
    public Ad getAdById(Long id) {
        return adRepository.findById(id).get();
    }

    @Override
    public Ad updateAd(Long id, String text, BigDecimal price, Category category, Date expiryDate) {
        Ad ad = setupAd(text, price, expiryDate, category);
        ad.setId(id);
        return adRepository.save(ad);
    }

    @Override
    public void deleteAdById(Long id) {
        adRepository.deleteById(id);
    }

    @Override
    public List<Ad> getAllAdds() {
        return adRepository.findAll();
    }

    @Override
    public List<Ad> filterAdsByCategory(Category category) {
        List<Ad> ads = new ArrayList<>();
        for(Ad ad : adRepository.findAll()){
            if(ad.getCategory().equals(category)){
                ads.add(ad);
            }
        }
        return ads;
    }

    private Ad setupAd(String text, BigDecimal price, Date expiryDate, Category category){
        Ad ad = new Ad();
        ad.setText(text);
        ad.setCategory(category);
        ad.setExpiryDate(expiryDate);
        ad.setPrice(price);
        ad.setActive(true);
        return ad;
    }
}
