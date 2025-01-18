package com.api.pharma.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_pharmacists")
public class Pharmacist implements Serializable {

    @Serial
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotBlank(message = "O Campo Id não pode ser nulo ou vazio")
    @Positive(message = "O Campo Id não pode ser negativo")
    private Long id;

    @NotBlank(message = "O Campo Usuário não pode ser vazio")
    @OneToOne
    private User user;

    @NotBlank(message = "O Campo CRM não pode ser nulo ou vazio")
    @Column(name = "crm_number")
    private String crmNumber;

    @NotBlank(message = "O Campo Especialidade não pode ser nulo ou vazio")
    private String specialty;

    @ManyToMany
    @JoinTable(
            name = "tb_pharmacists_pharmacies",
            joinColumns = @JoinColumn(name = "pharmacist_id"),
            inverseJoinColumns = @JoinColumn(name = "pharmacy_id"))
    private List<Pharmacy> pharmacies;

    public Pharmacist(Long id, User user, String crmNumber, String specialty, List<Pharmacy> pharmacies) {
        this.id = id;
        this.user = user;
        this.crmNumber = crmNumber;
        this.specialty = specialty;
        this.pharmacies = pharmacies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCrmNumber() {
        return crmNumber;
    }

    public void setCrmNumber(String crmNumber) {
        this.crmNumber = crmNumber;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public List<Pharmacy> getPharmacies() {
        return pharmacies;
    }

    public void setPharmacies(List<Pharmacy> pharmacies) {
        this.pharmacies = pharmacies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pharmacist that = (Pharmacist) o;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(crmNumber, that.crmNumber) && Objects.equals(specialty, that.specialty) && Objects.equals(pharmacies, that.pharmacies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, crmNumber, specialty, pharmacies);
    }

    @Override
    public String toString() {
        return "Pharmacist{" +
                "id=" + id +
                ", user=" + user +
                ", crmNumber='" + crmNumber + '\'' +
                ", specialty='" + specialty + '\'' +
                '}';
    }
}
