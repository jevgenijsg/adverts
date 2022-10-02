package com.example.adverts.service.impl;

import com.example.adverts.domain.Ad;
import com.example.adverts.domain.Category;
import com.example.adverts.dto.AdDto;
import com.example.adverts.mapper.AdMapper;
import com.example.adverts.repository.AdRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AdServiceImplTest {

    @Mock
    private AdRepository adRepository;
    @Mock
    private AdMapper adMapper;

    @InjectMocks
    private AdServiceImpl adService;

    Ad firstAd;
    Ad secondAd;

    AdDto firstAdDto;
    AdDto secondAdDto;

    @BeforeEach
    void setUp(){
        firstAd = new Ad("test", BigDecimal.valueOf(10.00), Category.CARS, Date.valueOf("2022-10-10"));
        firstAd.setId(1L);

        secondAd = new Ad("test", BigDecimal.valueOf(22.20), Category.TOYS, Date.valueOf("2022-08-18"));
        secondAd.setId(2L);

        firstAdDto = new AdDto(1L, "test", BigDecimal.valueOf(10.00), Date.valueOf("2022-10-10"), Category.CARS);
        secondAdDto = new AdDto(2L, "test", BigDecimal.valueOf(22.20), Date.valueOf("2022-08-18"), Category.TOYS);
    }

    @Test
    void createAd() {
        when(adRepository.save(firstAd)).thenReturn(firstAd);
        when(adMapper.adToAdDto(firstAd)).thenReturn(firstAdDto);
        when(adMapper.adDtoToAd(firstAdDto)).thenReturn(firstAd);
        Ad savedAd = adMapper.adDtoToAd(adService.createAd(firstAdDto));
        assertEquals(firstAd.getId(), savedAd.getId());
        assertEquals(firstAd.getText(), savedAd.getText());
        assertEquals(firstAd.getCategory(),savedAd.getCategory());
        assertEquals(firstAd.getPrice(),savedAd.getPrice());
        assertEquals(firstAd.isActive(), savedAd.isActive());
        assertEquals(firstAd.getExpiryDate(), savedAd.getExpiryDate());
        assertThat(firstAd).isNotNull();
        verify(adRepository, times(1)).save(firstAd);
    }

    @Test
    void findAdById() {
        when(adRepository.findById(anyLong())).thenReturn(Optional.of(firstAd));
        when(adMapper.adToAdDto(firstAd)).thenReturn(firstAdDto);
        Optional<AdDto> retreivedAd = adService.findAdById(anyLong());
        assertEquals(1L, retreivedAd.get().getId());
        assertEquals("test", retreivedAd.get().getText());
        assertEquals(BigDecimal.valueOf(10.00), retreivedAd.get().getPrice());
        assertEquals(Date.valueOf("2022-10-10"), retreivedAd.get().getExpiryDate());
        assertEquals(Category.CARS, retreivedAd.get().getCategory());
        assertTrue(retreivedAd.get().isActive());
        verify(adRepository, times(1)).findById(anyLong());
    }

    @Test
    void updateAd() {

        when(adRepository.save(firstAd)).thenReturn(firstAd);
        when(adRepository.getById(anyLong())).thenReturn(firstAd);
        when(adMapper.adToAdDto(firstAd)).thenReturn(firstAdDto);
        AdDto updatedAd = adService.updateAd(firstAdDto);

        assertEquals(firstAd.getId(), updatedAd.getId());
        assertEquals(firstAd.getText(), updatedAd.getText());
        assertEquals(firstAd.isActive(), updatedAd.isActive());
        assertEquals(firstAd.getExpiryDate(), updatedAd.getExpiryDate());
        assertEquals(firstAd.getCategory(), updatedAd.getCategory());
        assertEquals(firstAd.getPrice(), updatedAd.getPrice());
        verify(adRepository, times(1)).save(firstAd);
    }

    @Test
    void deleteAdById() {
        willDoNothing().given(adRepository).deleteById(anyLong());
        adService.deleteAdById(anyLong());
        verify(adRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void getAllAdds() {
        List<Ad> ads = new ArrayList<>();
        ads.add(firstAd);
        ads.add(secondAd);
        List<AdDto> adsDto = new ArrayList<>();
        adsDto.add(firstAdDto);
        adsDto.add(secondAdDto);

        when(adRepository.findAll()).thenReturn(ads);
        when(adMapper.adListToAdDtoList(ads)).thenReturn(adsDto);
        assertThat(adService.getAllAdds(), hasSize(2));
        verify(adRepository,times(1)).findAll();
    }
    @Test
    void getAllAddsWillReturnEmptyList() {
        when(adRepository.findAll()).thenReturn(Collections.emptyList());
        assertTrue(adService.getAllAdds().isEmpty());
        assertThat(adService.getAllAdds(), hasSize(0));
        verify(adRepository,times(2)).findAll();
    }

    @Test
    void filterAdsByCategory() {
        Ad thirdAd = new Ad("test", BigDecimal.valueOf(10.00), Category.CARS, Date.valueOf("2022-10-10"));
        thirdAd.setId(3L);

        List<Ad> ads = new ArrayList<>();
        ads.add(firstAd);
        ads.add(secondAd);
        ads.add(thirdAd);

        when(adRepository.findAll()).thenReturn(ads);
        assertThat(adService.filterAdsByCategory(Category.CARS), hasSize(2));
        verify(adRepository,times(1)).findAll();
    }
}