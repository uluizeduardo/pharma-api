package com.api.pharma.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_pharmacies")
public class Pharmacy implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotBlank(message = "O Campo Id não pode ser nulo ou vazio")
    @Positive(message = "O Campo Id não pode ser negativo")
    private Long id;

    @NotBlank(message = "O Campo Nome não pode ser vazio")
    @Column(name = "pharmacy_name")
    private String pharmacyName;

    @NotBlank(message = "O Campo Unidade não pode ser vazio")
    @Column(name = "unit_name")
    private String unitName;

    @ManyToMany(mappedBy = "pharmacies")
    private List<Pharmacist> pharmacists;

    @ManyToMany(mappedBy = "pharmacies")
    private List<Compounder> compounders;

    @ManyToMany(mappedBy = "pharmacies")
    private List<Attendant> attendants;

    @OneToOne
    @NotBlank(message = "O Campo Endereço não pode ser vazio")
    private Address address;

    public Pharmacy(Long id, String pharmacyName, String unitName, List<Pharmacist> pharmacists, List<Compounder> compounders, List<Attendant> attendants, Address address) {
        this.id = id;
        this.pharmacyName = pharmacyName;
        this.unitName = unitName;
        this.pharmacists = pharmacists;
        this.compounders = compounders;
        this.attendants = attendants;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public List<Pharmacist> getPharmacists() {
        return pharmacists;
    }

    public void setPharmacists(List<Pharmacist> pharmacists) {
        this.pharmacists = pharmacists;
    }

    public List<Compounder> getCompounders() {
        return compounders;
    }

    public void setCompounders(List<Compounder> compounders) {
        this.compounders = compounders;
    }

    public List<Attendant> getAttendants() {
        return attendants;
    }

    public void setAttendants(List<Attendant> attendants) {
        this.attendants = attendants;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pharmacy pharmacy = (Pharmacy) o;
        return Objects.equals(id, pharmacy.id) && Objects.equals(pharmacyName, pharmacy.pharmacyName) && Objects.equals(unitName, pharmacy.unitName) && Objects.equals(pharmacists, pharmacy.pharmacists) && Objects.equals(compounders, pharmacy.compounders) && Objects.equals(attendants, pharmacy.attendants) && Objects.equals(address, pharmacy.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pharmacyName, unitName, pharmacists, compounders, attendants, address);
    }

    @Override
    public String toString() {
        return "Pharmacy{" +
                "id=" + id +
                ", name='" + pharmacyName + '\'' +
                ", unitName='" + unitName + '\'' +
                ", address=" + address +
                '}';
    }
}
