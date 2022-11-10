package com.sa22.services;

import com.sa22.data.DataReader;
import com.sa22.entities.Author;
import com.sa22.entities.Client;
import com.sa22.services.interfaces.ClientServiceInterface;
import com.sa22.services.interfaces.CommonPaths;

import java.util.ArrayList;
import java.util.List;

public class ClientServiceImpl implements ClientServiceInterface, CommonPaths {


    @Override
    public List<Client> mapClient(String filePath) {

        List<Client> allClients = new ArrayList<>();
        List<String> mapClient = DataReader.readAllData(clientPath);
        for (String s : mapClient) {
            String[] splitElementArr = s
                    .replace("[", "")
                    .replace("]", "")
                    .split("_");
            Client client = new Client(Integer.parseInt(splitElementArr[0]), splitElementArr[1]);
            allClients.add(client);
        }

        return allClients;
    }

    public static void displayAllClients(){
        List<String> ListOfClients = DataReader.readAllData(clientPath);
        for (String ClientList : ListOfClients) {
            System.out.println(ClientList);
        }
    }
}

