package com.sapient.inventory.model.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Address {

    private Integer id;
    private String name;
    private String house;
    private String street;
    private String city;
    private String zip;
}
