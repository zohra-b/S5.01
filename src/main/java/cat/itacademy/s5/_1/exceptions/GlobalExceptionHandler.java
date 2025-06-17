package cat.itacademy.s5._1.exceptions;

import cat.itacademy.s5._1.validations.ValidateInputs;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<Object> handlePlayerNotFoundException(PlayerNotFoundException playerNotFoundException, WebRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        String errorMsg = playerNotFoundException.getMessage();
        String path = request.getDescription(false).replace("uri=", "");

        ErrorResponse errorResponse = new ErrorResponse(status, errorMsg, path);

        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException illegalArgExc, WebRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String errorMsg = illegalArgExc.getMessage();
        String path = request.getDescription(false).replace("uri=", "");

        ErrorResponse errorResponse = new ErrorResponse(status, errorMsg, path);

        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(EmptyInputException.class)
    public ResponseEntity<Object> handleEmptyInputException(EmptyInputException emptyInputException, WebRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String errorMsg = emptyInputException.getMessage();
        String path = request.getDescription(false).replace("uri=", "");

        ErrorResponse errorResponse = new ErrorResponse(status, errorMsg, path);

        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(ValueOutOfRangeException.class)
    public ResponseEntity<Object> handleValueOutOfRangeException(ValueOutOfRangeException valueOutOfRangeException, WebRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String errorMsg = valueOutOfRangeException.getMessage();
        String path = request.getDescription(false).replace("uri=", "");

        ErrorResponse errorResponse = new ErrorResponse(status, errorMsg, path);

        return new ResponseEntity<>(errorResponse, status);
    }

}
