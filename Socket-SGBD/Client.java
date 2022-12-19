package Outils;

import java.io.*;
import java.net.*;

public class Client {

    private String ip;
    private int port;
    private Socket socket;
    private DataInputStream dataInput;
    private DataOutputStream dataOutput;

    public Client(String ip, int port) throws IOException {
        this.setIp(ip);
        this.setPort(port);
        try {
            this.setSocket(new Socket(ip, port));
            this.setDataInput(new DataInputStream(this.getSocket().getInputStream()));
            this.setDataOutput(new DataOutputStream(this.getSocket().getOutputStream()));
        } catch (IOException e) {
            throw e;
        }

    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
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

    public DataOutputStream getDataOuput() {
        return dataOutput;
    }

    public void setDataOutput(DataOutputStream dataOutput) {
        this.dataOutput = dataOutput;
    }

    public void setCommand() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String commande = new String(), serverResponse = new String();
        try {
            while (commande.compareToIgnoreCase("exit") != 0) {
                System.out.print("SocketSQL> ");
                commande = br.readLine();
                this.getDataOuput().writeUTF(commande);
                this.getDataOuput().flush();
                serverResponse = this.getDataInput().readUTF();
                System.out.println(serverResponse);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (this.getDataOuput() != null) {
                this.getDataOuput().close();
            }
            if (this.getSocket() != null) {
                this.getSocket().close();
            }
        }
    }
}