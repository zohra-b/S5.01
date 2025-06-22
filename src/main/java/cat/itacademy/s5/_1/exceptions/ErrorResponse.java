package cat.itacademy.s5._1.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd - MM - yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private List<String> details;
    private String path;


    public ErrorResponse(HttpStatus status, String error, String message, List<String> details,String path ){
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.error = error;
        this.message = message;
        this.details = details;
        this.path = path;
    }

    public ErrorResponse(HttpStatus status, String message, String path){
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.message = message;
        this.path = path;
    }
    public ErrorResponse(HttpStatus status, String message){
        this.timestamp = LocalDateTime.now();
        this.status = status.value();
        this.message = message;

    }
}
