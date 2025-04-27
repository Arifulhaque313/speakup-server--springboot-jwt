package com.practice.speakup.handlers;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@Getter
@Setter
public class GlobalResponseHandler<T> {
    private String status;
    private Integer status_code;
    private String message;
    private T details;

    public  GlobalResponseHandler(String status, String message, T details, Integer status_code){
        this.status = status;
        this.message = message;
        this.details = details;
        this.status_code = status_code;
    }

    public  static  <T>ResponseEntity<GlobalResponseHandler<T>> successResponse(String message, T details, Integer status_code){
        GlobalResponseHandler<T> response = new GlobalResponseHandler<>(
                "success",
                message != null ? message : "Request was successful",
                details,
                (status_code != null) ? status_code : HttpStatus.OK.value()
        );

        return new ResponseEntity<>(response, HttpStatus.valueOf(status_code));
    }

    public static <T> ResponseEntity<GlobalResponseHandler<T>> errorResponse(String message, HttpStatus status){
        GlobalResponseHandler<T> response = new GlobalResponseHandler<>(
                status.name(),
                message !=null ? message : "An error occurred",
                null,
                status.value()
        );

        return new ResponseEntity<>(response, status);
    }
}
