package com.example.adverts.controller;

import com.example.adverts.domain.Ad;
import com.example.adverts.domain.Category;
import com.example.adverts.dto.AdDto;
import com.example.adverts.service.AdService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ads")
public class AdController {

    @Autowired
    private AdService adService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<Ad> createAd(@RequestBody AdDto adDto) {
        Ad ad = adService.createAd(adDto.getText(), adDto.getPrice(), adDto.getExpiryDate(),
                adDto.getCategory());
        return ResponseEntity.status(HttpStatus.CREATED).body(ad);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Ad> updateAd(@RequestBody AdDto adDto, @PathVariable("id") Long id) {
        Ad ad = adService.updateAd(id, adDto.getText(), adDto.getPrice(), adDto.getCategory(), adDto.getExpiryDate());
        return ResponseEntity.ok(ad);
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<Ad> getAdById(@PathVariable("id") Long id) {
        Ad ad = adService.getAdById(id);
        return ResponseEntity.ok(ad);
    }

    @RequestMapping(value = "/remove/{id}", method = RequestMethod.DELETE)
    public void deleteAdById(@PathVariable Long id) {
        adService.deleteAdById(id);
    }

    @RequestMapping(value = "/get-all", method = RequestMethod.GET)
    public ResponseEntity<List<Ad>> getAllAds() {
        List<Ad> ads = adService.getAllAdds();
        return ResponseEntity.ok(ads);
    }

    @RequestMapping(value = "/filter/{category}", method = RequestMethod.GET)
    public ResponseEntity<List<Ad>> filterAdsByCategory(@PathVariable String category) {
        List<Ad> filteredAds = adService.filterAdsByCategory(Category.valueOf(category.toUpperCase()));
        return ResponseEntity.ok(filteredAds);
    }


}
