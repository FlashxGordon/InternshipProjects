package com.sa22.entities;

public class Client {

    private int clientId;
    private String clientName;



    public Client() {
    }

    public Client(int clientId, String clientName){
        this.clientId = clientId;
        this.clientName = clientName;
    }

    public Client(String clientName) {
        this.clientName = clientName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return
                "ID: " + clientId +
                        ", Client Name: " + clientName ;
    }
}
