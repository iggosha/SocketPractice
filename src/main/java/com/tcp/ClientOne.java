//TCP: реализовать возможность синхронной установки вероятностей
//появления физических и юридических лиц на текущем и одном из
//подключенных клиентов, т. е. при установке этих параметров на выбранном
//клиенте должны выставиться такие же параметры.

package com.tcp;

import java.io.*;
import java.net.Socket;

public class ClientOne {
    private static double freqF = -1;
    private static double freqU = -1;

    public static void main(String[] args) {

        System.out.println("Клиент запущен");
        try (Socket socket = new Socket("localhost", 3333)) {
            System.out.println("Клиент подключен к серверу: " + socket);
            // Создать потоки ввода и вывода для обмена данными с сервером
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Создать поток для чтения сообщений с консоли
            BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));

            // Цикл обмена сообщениями
            while (true) {
                // Отправить сообщение на сервер
                System.out.print("Введите сообщение: ");
                String message = consoleIn.readLine();
                out.println(message);

                // Если сообщение "exit", то завершить работу
                if (message.equals("exit"))
                    break;

                // Прочитать ответ от сервера
                String response = in.readLine();
                System.out.println("Сервер ответил: " + response);
                if (response.matches("\\d+\\.\\d+\\s\\d+\\.\\d+")) {
                    freqF = Double.parseDouble(response.split(" ")[0]);
                    freqU = Double.parseDouble(response.split(" ")[1]);
                    System.out.println("Установлены новые значения: " + freqF + " " + freqU);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Клиент отключен");
    }
}
