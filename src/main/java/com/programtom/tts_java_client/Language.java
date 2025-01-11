package com.programtom.tts_java_client;

public enum Language {
    english, spanish, german, bulgarian;


    public String code() {

        return switch (this){
            case english -> "eng";
            case spanish -> "spa";
            case german -> "deu";
            case bulgarian -> "bul";
        };
    }
}
