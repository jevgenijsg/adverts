package com.example.adverts;

import com.example.adverts.domain.Ad;
import com.example.adverts.domain.Category;
import com.example.adverts.dto.AdDto;
import com.example.adverts.mapper.AdMapper;
import com.example.adverts.repository.AdRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AdControllerIT {

    private static final ObjectMapper om = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdRepository mockRepository;

    @Autowired
    private AdMapper mockMapper;

    private Ad ad;

    @BeforeEach
    public void init() {
        ad = new Ad("test", BigDecimal.valueOf(10.00), Category.CARS, Date.valueOf("2022-10-10"));
        ad.setId(1L);
    }

    @Test
    void findaDById_OK() throws Exception {
        when(mockRepository.findById(1L)).thenReturn(Optional.ofNullable(ad));

        mockMvc.perform(get("/ads/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.text", is("test")))
                .andExpect(jsonPath("$.price", is(10.00)))
                .andExpect(jsonPath("$.category", is(Category.CARS.name())))
                .andDo(print());
        verify(mockRepository, times(1)).findById(1L);
    }

    @Test
    void findAdById_NotFound404() throws Exception {
        mockMvc.perform(get("/ads/12")).andExpect(status().isNotFound());
    }

    @Test
    void deleteAd_OK() throws Exception {
        when(mockRepository.findById(anyLong())).thenReturn(Optional.ofNullable(ad));
        doNothing().when(mockRepository).deleteById(1L);
        mockMvc.perform(delete("/ads/1"))
                .andExpect(status().isNoContent())
                .andDo(print());
        verify(mockRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteAdById_NotFound404() throws Exception {
        when(mockRepository.findById(anyLong())).thenReturn(Optional.empty());
        doNothing().when(mockRepository).deleteById(anyLong());
        mockMvc.perform(delete("/ads/12"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void findAllAds_OK() throws Exception {
        Ad anotherAd = new Ad("test1", BigDecimal.valueOf(12.00), Category.TOYS, Date.valueOf("2022-10-10"));
        anotherAd.setId(2L);

        List<Ad> ads = new ArrayList<>();
        ads.add(ad);
        ads.add(anotherAd);

        when(mockRepository.findAll()).thenReturn(ads);

        mockMvc.perform(get("/ads/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].price", is(10.00)))
                .andExpect(jsonPath("$[0].text", is("test")))
                .andExpect(jsonPath("$[0].category", is(Category.CARS.toString())))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].price", is(12.00)))
                .andExpect(jsonPath("$[1].category", is(Category.TOYS.toString())))
                .andExpect(jsonPath("$[1].text", is("test1")))
                .andDo(print());
        verify(mockRepository, times(1)).findAll();
    }

    @Test
    void saveAd_OK() throws Exception {
        Ad ad = new Ad("test", BigDecimal.valueOf(10.00), Category.CARS, Date.valueOf("2022-10-10"));
        ad.setId(1L);
        when(mockRepository.save(any(Ad.class))).thenReturn(ad);

        mockMvc.perform(post("/ads/")
                .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(ad)))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.id", is(1)))
                        .andExpect(jsonPath("$.price", is(10.00)))
                        .andExpect(jsonPath("$.text", is("test")))
                        .andExpect(jsonPath("$.category", is(Category.CARS.toString())))
                        .andDo(print())
                        .andExpect(jsonPath("$.active", is(Boolean.TRUE))).andDo(print());
        verify(mockRepository, times(1)).save(any(Ad.class));
    }

    @Test
    void saveAd_BadRequest() throws Exception {
        Ad ad = new Ad("test", BigDecimal.valueOf(10.00), Category.CARS, Date.valueOf("2022-10-10"));
        ad.setId(1L);
        when(mockRepository.save(any(Ad.class))).thenReturn(null);

        mockMvc.perform(post("/ads/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(ad)))
                .andExpect(status().isBadRequest())
                .andDo(print());
        verify(mockRepository, times(1)).save(any(Ad.class));
    }

    @Test
    void saveAd_BadRequest_BindingResult() throws Exception {
        Ad ad = new Ad("test", BigDecimal.valueOf(10.00), Category.CARS, Date.valueOf("2022-10-10"));

        mockMvc.perform(post("/ads/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(ad)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void updateAd_OK() throws Exception {

        AdDto updatedAdDto = new AdDto(1L,"updatedTest", BigDecimal.valueOf(14.00), Date.valueOf("2022-10-10"), Category.CARS);
        Ad updatedAd = mockMapper.adDtoToAd(updatedAdDto);

        when(mockRepository.findById(updatedAd.getId())).thenReturn(Optional.of(updatedAd));
        when(mockRepository.save(any(Ad.class))).thenReturn(updatedAd);

        mockMvc.perform(put("/ads/{id}", updatedAdDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(updatedAdDto)))
                .andExpect(jsonPath("$.id").value(updatedAdDto.getId()))
                .andExpect(jsonPath("$.price").value(updatedAdDto.getPrice()))
                .andExpect(jsonPath("$.text").value(updatedAdDto.getText()))
                .andExpect(jsonPath("$.category").value(updatedAdDto.getCategory().toString()))
                .andExpect(jsonPath("$.active").value(updatedAdDto.isActive()))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
