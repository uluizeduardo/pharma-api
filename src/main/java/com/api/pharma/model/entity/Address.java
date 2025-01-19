package com.api.pharma.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_addresses")
public class Address implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotBlank(message = "O Campo Id não pode ser nulo ou vazio")
    @Positive(message = "O Campo Id não pode ser negativo")
    private Long id;

    @NotBlank(message = "O Campo Rua não pode ser nulo")
    private String street;

    @NotBlank(message = "O Campo Número não pode ser nulo")
    @Column(name = "address_number")
    private String addressNumber;

    @NotBlank(message = "O Campo Complemento não pode ser nulo")
    private String complement;

    @NotBlank(message = "O Campo Bairro não pode ser nulo")
    private String neighborhood;

    @NotBlank(message = "O Campo Cidade não pode ser nulo")
    private String city;

    @NotBlank(message = "O Campo Estado não pode ser nulo")
    private String state;

    @NotBlank(message = "O Campo CEP não pode ser nulo")
    @Column(name = "zip_code")
    private String zipCode;

    @NotBlank(message = "O Campo País não pode ser nulo")
    private String country;

    public Address(Long id, String street, String addressNumber, String complement, String neighborhood,
                   String city, String state, String zipCode, String country) {
        this.id = id;
        this.street = street;
        this.addressNumber = addressNumber;
        this.complement = complement;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(id, address.id)
                && Objects.equals(street, address.street)
                && Objects.equals(addressNumber, address.addressNumber)
                && Objects.equals(complement, address.complement)
                && Objects.equals(neighborhood, address.neighborhood)
                && Objects.equals(city, address.city)
                && Objects.equals(state, address.state)
                && Objects.equals(zipCode, address.zipCode)
                && Objects.equals(country, address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street, addressNumber, complement, neighborhood, city, state, zipCode, country);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", number='" + addressNumber + '\'' +
                ", complement='" + complement + '\'' +
                ", neighborhood='" + neighborhood + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
