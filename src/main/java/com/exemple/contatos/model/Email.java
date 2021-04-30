package com.exemple.contatos.model;

public class Email {

    private String tipo;
    private String email;

    public Email () {}

    public Email (String tipo, String email){
        this.tipo = tipo;
        this.email = email;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
