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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest()
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
        firstAd.setId(1L);

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

/*    @Test
    void findAdById() {

        when(adRepository.findById(anyLong())).thenReturn(Optional.of(firstAd));
        Optional<Ad> retreivedAd = adService.findAdById(firstAd.getId());
        assertEquals(firstAd.getId(), retreivedAd.get().getId());
        verify(adRepository, times(1)).findById(anyLong());
    }*/

/*    @Test
    void updateAd() {
        AdDto adDto = new AdDto(firstAd.getText(), firstAd.getPrice(), firstAd.getExpiryDate(), firstAd.getCategory());
        Ad updatedAd = adService.updateAd(adDto, firstAd.getId());
        when(adRepository.save(firstAd)).thenReturn(updatedAd);
        assertEquals(firstAd.getId(), updatedAd.getId());
        assertEquals(firstAd.getText(), updatedAd.getText());
        verify(adRepository, times(1)).save(firstAd);
    }*/

    @Test
    void deleteAdById() {
        willDoNothing().given(adRepository).deleteById(anyLong());
        adService.deleteAdById(anyLong());
        verify(adRepository, times(1)).deleteById(anyLong());
    }

/*    @Test
    void getAllAdds() {

        List<Ad> ads = new ArrayList<>();
        ads.add(firstAd);
        ads.add(secondAd);

        when(adRepository.findAll()).thenReturn(ads);
        assertThat(adService.getAllAdds()).isNotNull();
        assertThat(adService.getAllAdds().size()).isEqualTo(2);
    }*/

/*    @Test
    void filterAdsByCategory() {
    }*/
}