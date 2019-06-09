package com.froso.ufp.modules.core.user.model.plain;

import com.github.javafaker.*;

public class DummyUserData {

    public static GeoLocation createGeoLocation(Faker faker) {
        GeoLocation result = new GeoLocation();

        result.setLatitude(Double.parseDouble(faker.address().latitude().replace(',', '.')));
        result.setLongitude(Double.parseDouble(faker.address().longitude().replace(',', '.')));

        return result;

    }

    public static AdressData createAddress(Faker faker) {
        AdressData result = new AdressData();

        result.setCity(faker.address().city());
        result.setCountry(faker.address().country());
        result.setCountryCode(faker.address().countryCode());
        result.setAddressLine2(faker.address().secondaryAddress());
        result.setRegion(faker.address().state());
        result.setHouseNumber(faker.address().streetAddressNumber());
        result.setStreet(faker.address().streetName());
        result.setRegion(faker.address().state());
        result.setPostalCode(faker.address().zipCode());
        result.setLocation(createGeoLocation(faker));

        return result;

    }

}
