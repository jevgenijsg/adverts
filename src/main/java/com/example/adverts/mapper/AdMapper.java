package com.example.adverts.mapper;

import com.example.adverts.domain.Ad;
import com.example.adverts.dto.AdDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdMapper{

    Ad adDtoToAd(AdDto adDto);
    AdDto adToAdDto(Ad ad);

    List<AdDto> adListToAdDtoList(List<Ad> ad);

}
