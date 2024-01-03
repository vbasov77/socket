package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Server {
    public static final int PORT = 8181;
    private static long clientIdCounter = 1L;
    private static Map<Long, SocketWrapper> clients = new HashMap<>();


    public static void main(String[] args) throws IOException {
        try (ServerSocket server = new ServerSocket(PORT)) {

            System.out.println("Сервер запущен на порту " + PORT);
            while (true) {
                final Socket client = server.accept();
                final long clientId = clientIdCounter++;

                SocketWrapper wrapper = new SocketWrapper(clientId, client);
                System.out.println("Подключился новый клиент " + client);

                clients.put(clientId, wrapper);

                new Thread(() -> {
                    try (Scanner input = wrapper.getInput(); PrintWriter output = wrapper.getOutput();) {
                        output.println("Подключение произошло отлично. Список всех клиентов: " + clients);
                        while (true) {
                            String clientInput = input.nextLine();

                            if (Objects.equals("q", clientInput)) {
                                // Разослать это всем клиентам
                                clients.remove(clientId);
                                clients.values().forEach(it -> it.getOutput().println("Клиент " + clientId + " отключился"));
                                break;
                            }

                            ClientService clientService = new ClientService();
                            long destinationId = clientService.getIdClient(clientInput);

                            if (destinationId != -1L) {
                                SocketWrapper destination = clients.get(destinationId);
                                destination.getOutput().println(clientInput);
                            } else {
                                System.out.println(clients.values());
                                clients.values()/*.stream().filter(s -> s.getId() != destinationId)*/.forEach(it -> it.getOutput().println(clientInput));
                            }

                        }
                    }
                }).start();
            }
        }
    }
}
