package com.froso.ufp.modules.core.user.model.plain;

import io.swagger.annotations.*;
import java.io.*;
import org.springframework.data.mongodb.core.index.*;

@ApiModel(description = "adress information is stored here")
public class AdressData implements Serializable {

    private static final long serialVersionUID = 1;
    private GeoLocation location;
    @ApiModelProperty(example = "Germany", notes = "The country of residence")
    @TextIndexed
    private String country = "Germany";

    //    @Pattern(regexp = Validations.REGEXP_ALPHABETIC)
    @ApiModelProperty(example = "Examplestreet", notes = "The street of residences")
    @TextIndexed
    private String street = "DefaultStreet";

    //    @Pattern(regexp = Validations.REGEXP_ALPHABETIC)
    @ApiModelProperty(example = "Flat 2, level 2", notes = "Additional adress data")
    @TextIndexed
    private String adressLine2 = "DefaultStreet";
    @TextIndexed
//    @Pattern(regexp = Validations.REGEXP_ALPHABETIC)
    @ApiModelProperty(example = "12345", notes = "The Postal code - usually a 5 digit number")
    private String postalCode = "00000";
    //    @Pattern(regexp = Validations.REGEXP_ALPHABETIC)
    @TextIndexed
    @ApiModelProperty(example = "1", notes = "The housenumber")
    private String houseNumber = "00";
    @TextIndexed
//    @Pattern(regexp = Validations.REGEXP_ALPHABETIC)
    @ApiModelProperty(example = "ExampleCity", notes = "city of residence")
    private String city = "DefaultCity";
    @TextIndexed
//    @Pattern(regexp = Validations.REGEXP_ALPHABETIC)
    @ApiModelProperty(example = "DE", notes = "2 letter country code")
    private String countryCode = "DE";
    @TextIndexed
    @ApiModelProperty(example = "NRW", notes = "Region of country")
    private String region = "DefaultRegion";

    public String getAddressLine2() {
        return adressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.adressLine2 = addressLine2;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public GeoLocation getLocation() {
        return location;
    }

    public void setLocation(GeoLocation location) {
        this.location = location;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }


    public AdressData clone() {
        AdressData result = new AdressData();
        result.setCity(getCity());
        result.setCountry(getCountry());
        result.setCountryCode(getCountryCode());
        result.setHouseNumber(getHouseNumber());
        result.setLocation(getLocation().clone());
        result.setPostalCode(getPostalCode());
        result.setRegion(getRegion());
        result.setStreet(getStreet());
        return result;
    }
}
