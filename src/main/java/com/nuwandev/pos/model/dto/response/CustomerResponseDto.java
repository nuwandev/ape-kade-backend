package com.nuwandev.pos.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponseDto {
    private String id;
    private String title;
    private String name;
    private Date dob;
    private Double salary;
    private String address;
    private String city;
    private String province;
    private String postalCode;
}
