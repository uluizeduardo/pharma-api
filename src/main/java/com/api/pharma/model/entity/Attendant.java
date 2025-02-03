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
@Table(name = "tb_attendants")
public class Attendant implements Serializable {

    @Serial
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @ToString.Exclude
    private List<Pharmacy> pharmacies;

}
