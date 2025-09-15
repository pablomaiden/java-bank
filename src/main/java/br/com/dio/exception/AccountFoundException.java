package br.com.dio.exception;

public class AccountFoundException extends RuntimeException{
    public AccountFoundException(String message){
        super(message);
    }
}
