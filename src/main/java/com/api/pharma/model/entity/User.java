package com.api.pharma.model.entity;

import com.api.pharma.model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "tb_users")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotBlank(message = "O Campo Id não pode ser nulo ou vazio")
    @Positive(message = "O Campo Id não pode ser negativo")
    private Long id;

    @NotBlank(message = "O Campo Nome não pode ser vazio")
    @Column(name = "user_name")
    private String userName;

    @Email
    @NotBlank(message = "O Campo Email não pode ser vazio")
    private String email;

    @NotBlank(message = "O Campo Senha não pode ser vazio")
    @Column(name = "user_password")
    private String password;

    @FutureOrPresent
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @FutureOrPresent
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Token> tokens;

    public User(Long id, String userName, String email, String password,
                LocalDateTime createdAt, LocalDateTime updatedAt, Boolean isActive,
                Role role, List<Token> tokens) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
        this.role = role;
        this.tokens = tokens;
    }


    @PrePersist
    private void prePersist(){
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id)
                && Objects.equals(userName, user.userName)
                && Objects.equals(email, user.email)
                && Objects.equals(password, user.password)
                && Objects.equals(createdAt, user.createdAt)
                && Objects.equals(updatedAt, user.updatedAt)
                && Objects.equals(isActive, user.isActive)
                && Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, email, password, createdAt, updatedAt, isActive, role);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", isActive=" + isActive +
                '}';
    }
}
