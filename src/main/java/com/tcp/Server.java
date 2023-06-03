package com.tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            System.out.println("Сервер запущен");
            try (ServerSocket ss = new ServerSocket(3333)) {
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
    private Socket socket;
    private static double freqF = 0;
    private static double freqU = 0;

    public ServerConnectionProcessor(Socket s) {
        socket = s;
    }

    public void run() {
        try {
            System.out.println("Новый клиент подключился: " + socket);
            // Создать потоки ввода и вывода для обмена данными с клиентом
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Создать поток для чтения сообщений с консоли
            BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));

            // Запустить поток для чтения сообщений от клиента и отправки ответов
            while (true) {
                // Прочитать сообщение от клиента
                String message = in.readLine();

                // Если сообщение "exit", то завершить работу
                if (message == null || message.equals("exit"))
                    break;
                else if (message.matches("\\d+\\.\\d+\\s\\d+\\.\\d+")) {
                    freqF = Double.parseDouble(message.split(" ")[0]);
                    freqU = Double.parseDouble(message.split(" ")[1]);
                    out.println("Значения установлены");
                    System.out.println("Установлены новые значения: " + freqF + " " + freqU);
                } else {
                    System.out.println("Клиенту " + socket + " переданы значения " + freqF + " " + freqU);
                    out.println(freqF + " " + freqU);
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении сообщения от клиента: " + e.getMessage());
        } finally {
            try {
                socket.close();
                System.out.println("Клиент отключился: " + socket);
            } catch (IOException e) {
                System.err.println("Ошибка при закрытии сокета: " + e.getMessage());
            }
        }
    }
}

