package com.api.pharma.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_pharmacies")
public class Pharmacy implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive(message = "O Campo Id não pode ser negativo")
    private Long id;

    @NotBlank(message = "O Campo Nome não pode ser vazio")
    @JsonProperty("pharmacy_name")
    @Column(name = "pharmacy_name")
    private String pharmacyName;

    @NotBlank(message = "O Campo Unidade não pode ser vazio")
    @JsonProperty("unit_name")
    @Column(name = "unit_name")
    private String unitName;

    @ManyToMany(mappedBy = "pharmacies")
    @ToString.Exclude
    private List<Pharmacist> pharmacists;

    @ManyToMany(mappedBy = "pharmacies")
    @ToString.Exclude
    private List<Compounder> compounders;

    @ManyToMany(mappedBy = "pharmacies")
    @ToString.Exclude
    private List<Attendant> attendants;

    @OneToOne
    @NotBlank(message = "O Campo Endereço não pode ser vazio")
    private Address address;

}
