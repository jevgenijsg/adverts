package com.example.adverts.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum Category {
    CARS,
    TOYS,
    FURNITURE,
    APARTMENTS;
}