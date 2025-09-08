package model;

public class Address {
    private String street, city, province, country;
    public Address(String street, String city, String province, String country) {
        this.street = street; this.city = city; this.province = province; this.country = country;
    }
    public String getStreet() { return street; }
    public String getCity() { return city; }
    public String getProvince() { return province; }
    public String getCountry() { return country; }
}
