package com.spacemonkeys.farmbox.Models;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@Entity
@Table

public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private Long age;

    @DateTimeFormat
    private String cicle;

    @Column(name= "user_id")
    private Long user;

    @OneToOne
    @JoinColumn(name = "id")
    private Info info;

    public Plant(@NotBlank String type, @NotBlank Long age, String cicle, Long user) {
        this.type = type;
        this.age = age;
        this.cicle = cicle;
        this.user = user;
        this.id = getId();
    }
}
