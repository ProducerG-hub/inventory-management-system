package com.inventory_management.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ApiErrorException {

    private LocalDateTime timestamp;

    private Integer status;

    private String error;

    private String message;

    private String path;

}