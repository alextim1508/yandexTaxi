package com.alextim.yandextaxi.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Option {
    public double price;
    public double min_price;
    public double waiting_time;
    public String class_name;
    public String price_text;
}