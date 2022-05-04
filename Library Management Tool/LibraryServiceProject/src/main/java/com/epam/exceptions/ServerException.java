package com.epam.exceptions;
import feign.FeignException;
public class ServerException extends FeignException {
    public ServerException(int status, String message){
        super(status,message);
    }
}
