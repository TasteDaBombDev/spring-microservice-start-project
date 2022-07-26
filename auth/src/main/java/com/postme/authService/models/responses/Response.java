package com.postme.authService.models.responses;

import java.util.HashMap;

public class Response {

    private boolean success;
    private HashMap<String, String> data;
    private String message;

    public Response(boolean success, HashMap<String, String> data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public HashMap<String, String> getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage(){
        return message;
    }

    public void setData(HashMap<String, String> data){
        this.data = data;
    }

    public void setMessage(String message){
        this.message = message;
    }
}
