package com.alextim.yandextaxi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Coordinate {
    private double latitude;
    private double longitude;

    @Override
    public String toString() {
        return longitude + "," + latitude;
    }
}
