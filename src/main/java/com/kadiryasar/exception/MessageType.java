package com.kadiryasar.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MessageType {

    NO_RECORD_EXIST("1004","No Record Exist!",HttpStatus.NOT_FOUND),
    TOKEN_IS_EXPIRED("1005", "The token is out of time!",HttpStatus.BAD_REQUEST),
    USERNAME_NOT_FOUND("1006","Username not found!",HttpStatus.NOT_FOUND),
    USERNAME_OR_PASSWORD_INVALID("1007", "Username or password is not valid!",HttpStatus.BAD_REQUEST),
    REFRESH_TOKEN_NOT_FOUND("1008","Refresh token could not found!",HttpStatus.NOT_FOUND),
    REFRESH_TOKEN_IS_EXPIRED("1009","Refresh token is out of time!",HttpStatus.BAD_REQUEST),
    THERE_IS_NO_USER("1010","There is no any user who logined!",HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("1011","There is no user which has that id!",HttpStatus.NOT_FOUND),
    YOUR_ROLE_IS_NOT_AUTHORIZED("1012","Only admins can request this url!",HttpStatus.BAD_REQUEST),
    BOOK_NOT_FOUND("1013","Book has not found which has id that you sent!",HttpStatus.NOT_FOUND),
    ONLY_OWNERS_AND_ADMINS_CAN_MAKE_ARRENGEMENTS("1014","Only book owners can make arrengements on books!",HttpStatus.BAD_REQUEST),
    INSUFFICIENT_AUTHORITY("1015", "Only review owners and admins can delete review!",HttpStatus.BAD_REQUEST),
    REVIEW_NOT_FOUND("1015", "Review could not found check your id",HttpStatus.NOT_FOUND),
    GENERAL_EXCEPTION("9999", "General error occured!",HttpStatus.BAD_REQUEST);

    private final String code;

    private final String message;

    private final HttpStatus status;

    MessageType(String code, String message, HttpStatus status){
        this.code = code;
        this.message = message;
        this.status = status;

    }

}
