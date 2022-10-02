package com.example.adverts.service.impl;

import com.example.adverts.domain.Ad;
import com.example.adverts.domain.Category;
import com.example.adverts.dto.AdDto;
import com.example.adverts.mapper.AdMapper;
import com.example.adverts.repository.AdRepository;
import com.example.adverts.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdServiceImpl implements AdService {

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private AdMapper mapper;

    @Override
    public AdDto createAd(AdDto adDto) {
        Ad ad = mapper.adDtoToAd(adDto);
        if(adRepository.exists(Example.of(ad))){
            return null;
        } else
            return mapper.adToAdDto(adRepository.save(ad));
    }

    @Override
    public Optional<AdDto> findAdById(Long id) {
        return Optional.ofNullable(mapper.adToAdDto(adRepository.findById(id).orElse(null)));
    }

    @Override
    public AdDto updateAd(AdDto adDto) {
        Ad adToUpdate = adRepository.getById(adDto.getId());
        adToUpdate.setActive(adDto.isActive());
        adToUpdate.setCategory(adDto.getCategory());
        adToUpdate.setText(adDto.getText());
        adToUpdate.setPrice(adDto.getPrice());
        adToUpdate.setExpiryDate(adDto.getExpiryDate());
        adRepository.save(adToUpdate);
        return mapper.adToAdDto(adToUpdate);
    }

    @Override
    public void deleteAdById(Long id) {
        adRepository.deleteById(id);
    }

    @Override
    public List<AdDto> getAllAdds() {
        return mapper.adListToAdDtoList(adRepository.findAll());
    }

    @Override
    public List<AdDto> filterAdsByCategory(Category category) {
        List<AdDto> ads = new ArrayList<>();
        List<Ad> adsInDb = adRepository.findAll();
        for(Ad ad : adsInDb){
            if(ad.getCategory().equals(category)){
                ads.add(mapper.adToAdDto(ad));
            }
        }
        return ads;
    }
}
