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
@Table(name = "tb_pharmacists")
public class Pharmacist implements Serializable {

    @Serial
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @ToString.Exclude
    private List<Pharmacy> pharmacies;

}
