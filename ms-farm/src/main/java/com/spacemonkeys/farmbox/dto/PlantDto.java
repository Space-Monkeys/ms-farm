package com.spacemonkeys.farmbox.dto;

import com.spacemonkeys.farmbox.Models.Info;
import com.spacemonkeys.farmbox.Models.Plant;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Builder

public class PlantDto {
    @NotBlank
    public String type;
    public Long age;

    //@DateTimeFormat
    public String cicle;

    @NotNull(message = "The user field cannot be null")
    public Long user;

    public Plant dtoToPlant(){
        Plant data  = new Plant(this.type,this.age,this.cicle,this.user);
        return data;
    }
}
