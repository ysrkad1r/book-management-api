package com.kadiryasar.handler;

import com.kadiryasar.exception.BaseException;
import com.kadiryasar.exception.ErrorMessage;
import com.kadiryasar.exception.MessageType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BaseException.class})
    public ResponseEntity<ApiError<?>> handleBaseException(BaseException ex, WebRequest request){
        ErrorMessage errorMessage = ex.getErrorMessage();
        HttpStatus status = errorMessage.getMessageType().getStatus();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(createApiError(errorMessage, request, status));
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ApiError<Map<String, List<String>>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request){
        Map<String, List<String>> map = new HashMap<>();

        for (ObjectError objectError : ex.getBindingResult().getAllErrors()){
            String fieldName = ((FieldError)objectError).getField();

            if (map.containsKey(fieldName)){
                map.put(fieldName,addValue(map.get(fieldName),objectError.getDefaultMessage()));
            }else {
                map.put(fieldName,addValue(new ArrayList<>(),objectError.getDefaultMessage()));
            }
        }

        ErrorMessage errorMessage = new ErrorMessage(MessageType.GENERAL_EXCEPTION, null);

        return ResponseEntity
                .status(errorMessage.getMessageType().getStatus())
                .body(createApiError(map, request, errorMessage.getMessageType().getStatus()));
    }

    private List<String> addValue(List<String> list, String newValue){
        list.add(newValue);
        return list;
    }

    private String getHostName(){
        try {
            return Inet4Address.getLocalHost().getHostName();
        }catch (UnknownHostException e){
            e.printStackTrace();
        }
        return "";
    }

    public <E> ApiError<E> createApiError(E message, WebRequest request, HttpStatus status){
        ApiError<E> apiError = new ApiError<>();
        apiError.setStatus(status.value());

        Exception<E> exception = new Exception<>();
        exception.setPath(request.getDescription(false).substring(4));
        exception.setCreateTime(new Date());
        exception.setHostName(getHostName());
        exception.setMessage(message);

        apiError.setException(exception);

        return apiError;
    }

}
