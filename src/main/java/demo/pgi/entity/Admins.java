package demo.pgi.entity;


import jakarta.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Admins {
    public static final String USERNAME_COLUMN = "username";
    public static final String EMAIL_COLUMN = "email";
    public static final String PASSWORD_COLUMN = "password";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Column(name = USERNAME_COLUMN, unique=true)
    private String username;
    public String getUsername() {
        return username;
    }

    @NotBlank
    @Email
    @Column(name = EMAIL_COLUMN, unique=true)
    private String email;

    @NotBlank
    @Size(min = 6)
    @Column(name = PASSWORD_COLUMN)
    private String password;
    public String getPassword() {
        return password;
    }
}