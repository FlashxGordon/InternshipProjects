package com.sa22.services.interfaces;

import com.sa22.entities.Client;
import java.util.List;

public interface ClientServiceInterface {

    List<Client> mapClient(String filePath);
}
