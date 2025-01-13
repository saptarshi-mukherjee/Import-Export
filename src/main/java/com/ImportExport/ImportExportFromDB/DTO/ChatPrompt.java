package com.ImportExport.ImportExportFromDB.DTO;


import lombok.Data;

@Data
public class ChatPrompt {
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
