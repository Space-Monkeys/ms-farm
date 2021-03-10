package com.spacemonkeys.farmbox.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter

public class ErrorResponse {

    public Integer statusCode;
    public String message;

}
