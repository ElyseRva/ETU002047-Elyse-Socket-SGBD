package Main;

import java.io.IOException;

import Outils.Client;

public class ClientSQL {
    public static void main(String[] args) throws IOException {
        Client client = null;
        try {
            client = new Client("localhost", 1672);
            client.setCommand();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}