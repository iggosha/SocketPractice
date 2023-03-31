package com.tcp;

import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args) {
        try {
            System.out.println("Server is running");
            int port = 3333;
            try (ServerSocket ss = new ServerSocket(port)) {
                while (true) {
                    Socket s = ss.accept();
                    ServerConnectionProcessor p = new ServerConnectionProcessor(s);
                    p.start();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ServerConnectionProcessor extends Thread {
    private Socket sock;

    public ServerConnectionProcessor(Socket s) {
        sock = s;
    }

    public void run() {
        try {
            // Получаем запрос
            boolean ready, writeCommands;
            DataInputStream dataInputStream = new DataInputStream(sock.getInputStream());
            ready = dataInputStream.readBoolean();
            writeCommands = dataInputStream.readBoolean();
            if (ready) {
                // Создаем объекты
                List<ProductPack> productPackList = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    productPackList.add(new ProductPack());
                }
                if (writeCommands) {
                    for (ProductPack product : productPackList) {
                        System.out.println(product);
                    }
                }
                // Передаём объекты
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(sock.getOutputStream());
                for (ProductPack product : productPackList) {
                    objectOutputStream.writeObject(product);
                }
                dataInputStream.close();
                objectOutputStream.close();
                sock.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

