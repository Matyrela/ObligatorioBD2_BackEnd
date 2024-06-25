package me.basedatos2.pencaucu.dto.responses;

import org.springframework.http.HttpStatus;

public class DataResponse {
    public String message;

    public DataResponse(String message) {
        this.message = message;
    }

    public static DataResponse GenerateDataResponse(String message) {
        return new DataResponse(message);
    }
}
