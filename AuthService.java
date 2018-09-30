package ru.geekbrains.homework7.Server.service;

public interface AuthService implements Serializable {
    void start();

    String getNickByLoginPass(String login, String pass);

    void stop();

}
