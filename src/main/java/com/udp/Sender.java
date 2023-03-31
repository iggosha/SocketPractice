package com.udp;

//sender

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Sender {
    public Sender() {
    }


    public void sendMessage(String message) {
        try {
            byte[] data = message.getBytes();
            InetAddress addr = InetAddress.getByName("localhost");
            DatagramPacket pack = new DatagramPacket(data, data.length, addr, 4444);
            DatagramSocket datagramSocket = new DatagramSocket();
            if (message.equals("00")) {
                datagramSocket.close();
                return;
            } else if (message.equals("000")) {
                datagramSocket.close();
                System.exit(0);
            }
            datagramSocket.send(pack);
            datagramSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("Отправитель запущен");
        System.out.println("Стандартный режим: передача текстового сообщения");
        System.out.println("Введите 00, чтобы переподключить отправитель");
        System.out.println("Введите 000, чтобы завершить работу");
        Sender sender = new Sender();
        while (true) {
            sender.sendMessage(new Scanner(System.in).nextLine());
        }
    }
}

