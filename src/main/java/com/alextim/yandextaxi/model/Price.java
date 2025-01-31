package com.alextim.yandextaxi.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class Price {
    public List<Option> options;
    public String currency;
    public double distance;
    public String time_text;
}
