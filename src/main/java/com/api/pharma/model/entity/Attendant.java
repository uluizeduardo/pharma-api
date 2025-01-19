package com.api.pharma.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_attendants")
public class Attendant implements Serializable {

    @Serial
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotBlank(message = "O Campo Id não pode ser nulo ou vazio")
    @Positive(message = "O Campo Id não pode ser negativo")
    private Long id;

    @OneToOne
    @NotBlank(message = "O Campo Usuário não pode ser vazio")
    private User user;

    @NotBlank(message = "O Campo Farmacia não pode ser vazio")
    @ManyToMany
    @JoinTable(
            name = "tb_attendants_pharmacies",
            joinColumns = @JoinColumn(name = "attendant_id"),
            inverseJoinColumns = @JoinColumn(name = "pharmacy_id")
    )
    private List<Pharmacy> pharmacies;

    public Attendant(Long id, User user, List<Pharmacy> pharmacies) {
        this.id = id;
        this.user = user;
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
        Attendant attendant = (Attendant) o;
        return Objects.equals(id, attendant.id)
                && Objects.equals(user, attendant.user)
                && Objects.equals(pharmacies, attendant.pharmacies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, pharmacies);
    }

    @Override
    public String toString() {
        return "Attendant{" +
                "id=" + id +
                ", user=" + user +
                '}';
    }
}
