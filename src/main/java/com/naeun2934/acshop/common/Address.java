package com.naeun2934.acshop.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotEmpty;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @NotEmpty
    @Column(name = "zip_code")
    private String zipCode;

    @NotEmpty
    @Column(name = "address1")
    private String address1;

    @NotEmpty
    @Column(name = "address2")
    private String address2;
}
