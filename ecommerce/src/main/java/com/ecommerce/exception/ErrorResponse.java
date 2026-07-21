package com.ecommerce.exception;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private LocalDateTime localDateTime;
    private int status;
    private String message;
    private String path;

}
