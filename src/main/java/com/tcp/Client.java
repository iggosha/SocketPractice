package com.tcp;

import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 3333;
        boolean ready = true, writeCommands = true;
        List<ProductPack> productPackList = new ArrayList<>();
        try {
            System.out.println("Клиент запущен");
            Socket sock = new Socket(host, port);
            DataOutputStream dataOutputStream = new DataOutputStream(sock.getOutputStream());
            dataOutputStream.writeBoolean(ready);
            dataOutputStream.writeBoolean(writeCommands);

            try (ObjectInputStream objectInputStream = new ObjectInputStream(sock.getInputStream())) {
                Object obj;
                while ((obj = objectInputStream.readObject()) != null) {
                    ProductPack productPack = (ProductPack) obj;
                    productPackList.add(productPack);
                }
            } catch (EOFException ignored) {
            }
            if (writeCommands) {
                for (ProductPack product : productPackList) {
                    System.out.println(product);
                }
            }

            dataOutputStream.close();
            sock.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
