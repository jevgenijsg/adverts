package com.example.adverts.service.impl;

import com.example.adverts.domain.Ad;
import com.example.adverts.dto.AdDto;
import com.example.adverts.mapper.AdMapper;
import com.example.adverts.repository.AdRepository;
import com.example.adverts.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;


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
        return mapper.adToAdDto(adRepository.findById(adDto.getId()).orElse(null));
    }

    @Override
    public void deleteAdById(Long id) {
        adRepository.deleteById(id);
    }

    @Override
    public List<AdDto> getAllAdds() {
        return mapper.adListToAdDtoList(adRepository.findAll());
    }

/*    @Override
    public List<Ad> filterAdsByCategory(Category category) {
        List<Ad> ads = new ArrayList<>();
        for(Ad ad : adRepository.findAll()){
            if(ad.getCategory().equals(category)){
                ads.add(ad);
            }
        }
        return ads;
    }*/
}
