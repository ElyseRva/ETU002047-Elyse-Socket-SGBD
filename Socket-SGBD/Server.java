package Outils;

import java.io.*;
import java.net.*;

import Requetes.Create;
import Requetes.Insert;
import Requetes.Select;
import Requetes.Update;

public class Server {

    private int port;
    private File file;
    private ServerSocket serverSocket;
    private Socket socket;
    private DataInputStream dataInput;
    private DataOutputStream dataOutput;

    public Server(int port) throws IOException {
        this.setPort(port);
        try {
            this.setServerSocket(new ServerSocket(port));
            this.setSocket(this.getServerSocket().accept());
            this.setDataInput(new DataInputStream(this.getSocket().getInputStream()));
            this.setDataOutput(new DataOutputStream(this.getSocket().getOutputStream()));
        } catch (IOException e) {
            throw e;
        }
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public File getFile(String file) {
        setFile("../BDD/" + file + ".txt");
        return this.file;
    }

    public void setFile(String path) {
        this.file = new File(path);
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public DataInputStream getDataInput() {
        return dataInput;
    }

    public void setDataInput(DataInputStream dataInput) {
        this.dataInput = dataInput;
    }

    public DataOutputStream getDataOutput() {
        return dataOutput;
    }

    public void setDataOutput(DataOutputStream dataOutput) {
        this.dataOutput = dataOutput;
    }

    public void getCommand() throws IOException {
        String commande = new String(), serverResponse = new String();
        try {
            while (commande.compareToIgnoreCase("exit") != 0) {
                commande = this.getDataInput().readUTF();
                System.out.println("Commande: " + commande);
                String[] instuctions = commande.split(" ");
                if (instuctions[0].compareToIgnoreCase("create") == 0) {
                    serverResponse = Create.traitement(instuctions);
                } else if (instuctions[0].compareToIgnoreCase("insert") == 0) {
                    serverResponse = Insert.traitement(this, instuctions);
                } else if (instuctions[0].compareToIgnoreCase("select") == 0) {
                    serverResponse = Select.traitement(this, instuctions);
                } else if (instuctions[0].compareToIgnoreCase("update") == 0) {
                    serverResponse = Update.traitement(this, instuctions);
                } else if (instuctions[0].compareToIgnoreCase("exit") == 0) {
                    serverResponse = "bye!";
                } else {
                    serverResponse = "commande introuvable";
                }
                this.getDataOutput().writeUTF(serverResponse);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (this.getDataInput() != null) {
                this.getDataInput().close();
            }
            if (this.getSocket() != null) {
                this.getSocket().close();
            }
            if (this.getServerSocket() != null) {
                this.getServerSocket().close();
            }
        }
    }
}