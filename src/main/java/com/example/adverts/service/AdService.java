package com.example.adverts.service;


import com.example.adverts.domain.Ad;
import com.example.adverts.dto.AdDto;

import java.util.List;
import java.util.Optional;

public interface AdService {
    AdDto createAd(AdDto adDto);

    Optional<AdDto> findAdById(Long id);

    AdDto updateAd(AdDto adDto);

    void deleteAdById(Long id);

    List<AdDto> getAllAdds();

//    List<Ad> filterAdsByCategory(Category category);
}
