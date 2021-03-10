package com.spacemonkeys.farmbox.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table

public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String password;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Plant> plants;


    public Users(@NotBlank String name, @NotBlank String password) {
        this.name = name;
        this.password = password;
        this.id = getId();
    }
}
