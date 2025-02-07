package com.api.pharma.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
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
@Table(name = "tb_addresses")
public class Address implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive(message = "O Campo Id não pode ser negativo")
    private Long id;

    @NotBlank(message = "O Campo Rua não pode ser nulo")
    private String street;

    @NotBlank(message = "O Campo Número não pode ser nulo")
    @JsonProperty("address_number")
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
    @JsonProperty("zip_code")
    @Column(name = "zip_code")
    private String zipCode;

    @NotBlank(message = "O Campo País não pode ser nulo")
    private String country;

}
