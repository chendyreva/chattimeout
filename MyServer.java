package ru.geekbrains.homework7;

import ru.geekbrains.homework7.Server.service.AuthService;
import ru.geekbrains.homework7.Server.service.BaseAuthService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyServer implements Serializable {

    private ServerSocket server;
    private List<ClientHandler> clients;
    private AuthService authService;
    private ExecutorService executor = Executors.newCachedThreadPool();

    public AuthService getAuthService() {
        return authService;
    }

    private final int PORT = 8189;

    public static void main(String[] args) implements Serializable {
        new MyServer();
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            Integer integerSave = new Integer(100);
            oos.writeObject(integerSave);
            byte[] arr = os.toByteArray();
            os.close();
            oos.close();
            ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(is);
            Integer integerRead = (Integer)ois.readObject();
            is.close();
            ois.close();
            System.out.println("Written: " + integerSave + ", Read: " + integerRead);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public MyServer() {
        try {
            server = new ServerSocket(PORT);
            ServerSocket server = new ServerSocket(8189);
            server.setSoTimeout(60000);
            try {
                Socket socket = server.accept();
            }
            catch ( java.io.InterruptedIOException e ) {
                System.err.println( "Timed Out (60 sec)!" );
            }
            Socket socket = null;
            authService = new BaseAuthService();
            authService.start();
            clients = new ArrayList<>();
            while (true) {
                log("Сервер ожидает подключения...");
                socket = server.accept();
                log("Клиент подключился");
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            log("Ошибка при работе сервера");
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            authService.stop();
        }
    }

    public synchronized void sendMsgToClient implements Serializable(
            ClientHandler from,
            String nickTo,
            String msg
    ) {
        for (ClientHandler o : clients) {
            if (o.getName().equals(nickTo)) {
                o.sendMsg("от " + from.getName() + ": " + msg);
                from.sendMsg("клиенту " + nickTo + ": " + msg);
                return;
            }
        }
        from.sendMsg("Участника с ником " + nickTo + " нет в чат-комнате");
    }

    public synchronized void broadcastClientList() implements Serializable {
        StringBuilder sb = new StringBuilder("/clients ");
        for (ClientHandler o : clients) {
            sb.append(o.getName() + " ");
        }
        String msg = sb.toString();
        broadcastMsg(msg);
    }

    public synchronized void unsubscribe(ClientHandler o) {
        clients.remove(o);
        broadcastClientList();
    }

    public synchronized void subscribe(ClientHandler o) {
        clients.add(o);
        broadcastClientList();
    }

    public synchronized boolean isNickBusy(String nick) {
        for (ClientHandler o : clients) {
            if (o.getName().equals(nick)) return true;
        }
        return false;
    }

    public synchronized void broadcastMsg(String msg) implements Serializable {
        for (ClientHandler o : clients) {
            o.sendMsg(msg);
        }
    }
    public void execute(Runnable task) {
        execute.submit(task);
    }
}
