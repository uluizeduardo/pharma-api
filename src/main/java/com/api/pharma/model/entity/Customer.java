package com.api.pharma.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_customers")
public class Customer implements Serializable {

    @Serial
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive(message = "O Campo Id não pode ser negativo")
    private Long id;

    @OneToOne
    @NotBlank(message = "O Campo Usuário não pode ser vazio")
    private User user;

    @NotBlank(message = "O Campo Telefone não pode ser vazio")
    @JsonProperty("phone_number")
    @Column(name = "phone_number")
    @Max(value = 11, message = "O Campo Telefone não pode ter mais de 11 dígitos")
    private String phoneNumber;

    @OneToOne
    @NotBlank(message = "O Campo Endereço não pode ser vazio")
    private Address address;

}
