package hellojpa;

import jakarta.persistence.Embeddable;

@Embeddable//값 타입을 정의하는 곳에서 값타입이라 알려줘야 한다.
public class Address {
    private String city;
    private String street;
    private String zipcode;

//    private Member member;

    public Address() {
    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
