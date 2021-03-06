package ru.geekbrains.homework7;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Serializable {

    private MyServer myServer;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String name;

    public String getName() implements Serializable {
        return name;
    }

    public ClientHandler(MyServer myServer, Socket socket) implements Serializable {
        try {
            this.myServer = myServer;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.name = "";
            new Thread(() -> {
                try {
                    channel = ChannelBase.of(socket);
                    server.execute(this::process);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                    while (true) { // цикл авторизации
                        String str = in.readUTF();
                        if (str.startsWith("/auth")) {
                            String[] parts = str.split("\\s");
                            String nick =
                                    myServer.getAuthService().getNickByLoginPass(parts[1], parts[2]);
                            if (nick != null) {
                                if (!myServer.isNickBusy(nick)) {
                                    log("/authok " + nick);
                                    name = nick;
                                    myServer.subscribe(this);
                                    break;
                                } else log("Учетная запись уже используется");
                            } else {
                                log("Неверные логин/пароль");
                            }
                        }
                    }
                    while (true) {
                        String str = in.readUTF();
                        if (str.startsWith("/")) {
                            if (str.equals("/end")) break;
                            if (str.startsWith("/w ")) {
                                String[] tokens = str.split("\\s");
                                String nick = tokens[1];
                                String msg = str.substring(4 + nick.length());
                                myServer.sendMsgToClient(this, nick, msg);
                            }
                        } else {
                            myServer.broadcastMsg(name + ": " + str);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    myServer.unsubscribe(this);
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            throw new RuntimeException("Проблемы при создании обработчика клиента");
        }
    }

    public void sendMsg(String msg) implements Serializable {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

