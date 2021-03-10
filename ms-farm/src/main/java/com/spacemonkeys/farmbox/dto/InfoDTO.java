package com.spacemonkeys.farmbox.dto;

import com.spacemonkeys.farmbox.Models.Info;
import com.sun.istack.NotNull;
import jdk.jfr.BooleanFlag;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class InfoDTO {

    @NotNull
    public Float water;

    @NotNull
    public Float humidity;

    @BooleanFlag
    public Boolean light;
    @NotBlank
    public String ph;
    @NotBlank
    public String conductivity;
    @NotBlank
    public String nutrients;

    @DateTimeFormat
    private String time;

    public Info toInfo(){
        Info info = new Info(
                this.conductivity,
                this.water,
                this.humidity,
                this.light,
                this.ph,
                this.nutrients
                );
        return info;
    }

}
