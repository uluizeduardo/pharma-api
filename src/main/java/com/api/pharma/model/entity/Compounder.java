package com.api.pharma.model.entity;

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
@Table(name = "tb_compounders")
public class Compounder implements Serializable {

    @Serial
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive(message = "O Campo Id não pode ser negativo")
    private Long id;

    @NotBlank(message = "O Campo Usuário não pode ser vazio")
    @OneToOne
    private User user;

    @ManyToMany
    @JoinTable(
        name = "tb_compounders_pharmacies",
            joinColumns = @JoinColumn(name = "compounder_id"),
            inverseJoinColumns = @JoinColumn(name = "pharmacy_id")
    )
    @ToString.Exclude
    private List<Pharmacy> pharmacies;

}
