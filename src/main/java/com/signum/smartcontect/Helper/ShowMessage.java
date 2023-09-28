package com.signum.smartcontect.Helper;

public class ShowMessage {
    private String type;
    private String message;
    
    
    public ShowMessage(String type, String message) {
        super();
        this.type = type;
        this.message = message;
    }


    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }


    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }
    
}
