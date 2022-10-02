package com.example.adverts.controller;
import com.example.adverts.domain.Category;
import com.example.adverts.dto.AdDto;
import com.example.adverts.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ads")
public class AdController {

    @Autowired
    private AdService adService;

    @PostMapping("/")
    public ResponseEntity<AdDto> createAd(@RequestBody AdDto adDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        AdDto createdAd = adService.createAd(adDto);
        if(createdAd == null){
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdAd, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdDto> updateAd(@PathVariable("id") Long id, @RequestBody AdDto adDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors() || !(adDto.getId().equals(id))){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else if (adService.findAdById(id).isEmpty()) {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AdDto updatedAd = adService.updateAd(adDto);
            return new ResponseEntity<>(updatedAd, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdDto> getAdById(@PathVariable("id") Long id) {
        return adService.findAdById(id)
                .map( user -> new ResponseEntity<>(user, HttpStatus.OK) )
                .orElseGet( () -> new ResponseEntity<>(HttpStatus.NOT_FOUND) );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdById(@PathVariable Long id) {
        if(adService.findAdById(id).isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        adService.deleteAdById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/")
    public ResponseEntity<List<AdDto>> getAllAds() {
        List<AdDto> ads = adService.getAllAdds();
        return new ResponseEntity<>(ads, HttpStatus.OK);
    }

    @GetMapping("/filter/{category}")
    public ResponseEntity<List<AdDto>> filterAdsByCategory(@PathVariable String category) {
        List<AdDto> filteredAds = adService.filterAdsByCategory(Category.valueOf(category.toUpperCase()));
        return ResponseEntity.ok(filteredAds);
    }

}
