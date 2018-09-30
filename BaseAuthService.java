package ru.geekbrains.homework7.Server.service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaseAuthService implements AuthService {
    private class Entry {
        private String login;
        private String pass;
        private String nick;

        public Entry(String login, String pass, String nick) {
            this.login = login;
            this.pass = pass;
            this.nick = nick;
        }
    }

    private List<Entry> entries;

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

    public BaseAuthService() {
        entries = new ArrayList<>();
        entries.add(new Entry("login1", "pass1", "nick1"));
        entries.add(new Entry("login2", "pass2", "nick2"));
        entries.add(new Entry("login3", "pass3", "nick3"));
    }

    @Override
    public String getNickByLoginPass(String login, String pass) {
        for (Entry o : entries) {
            if (o.login.equals(login) && o.pass.equals(pass)) return o.nick;
        }
        return null;
    }
}
    private Map<String, User> users = new Hashmap<>();
    private static Connection connection;
    private static Statement stmp;

    public BaseAuthService() {
//        users.put("nick1", new User("login1", "pass1", "Misha"));
//        users.put("nick2", new User("login2", "pass2", "Masha"));
//        users.put("nick3", new User("login3", "pass3", "Liza"));
        try {
            connect();
            String sqlQuery = "SELECT login, pass, nick FROM users;";
            ResultSet rs = stmp.executeQuery(sqlQuerry);
            while (rs.next()) {
                String login = rs.getString(1);
                String pass = rs.getString(2);
                String nick = rs.getString(3);
                users.put(login, new User(login, pass, nick));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }

    public static void connect() throws  SQLException {
        connection = DriverManager.getConnection(JDBC.PREFIX + "main.db");
        stmp = connection.createStatement();
    }
    public void changeLogin(String login, String nick) {
        try{
            connect();
            stmp.execute("UPDATE users SET login=" + login + " WHERE nick = " +nick);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }
    public static void disconnect() {
        try{
            stmp.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        try{
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        @Override
                public  String authByLoginAndPassword(String login, String password) {
            for(User user : users.values()) {
                if (login.equals(user.getLogin())
                        && password.equals(user.getPassword())
                        && user.isActive())
                    return user.getName();
            }
            return null;
        }

        @Override
        public User createOrActivateUser(String login, String password, String nick) {
            User user = new User(login, password, nick);
            if(users.containsKey(nick)) {
                users.get(nick).setActive(true);
                System.out.println("User with nick " + nick + "already exist");
            } else {
                users.put(nick, user);
                persist(user);
            }
            return user;
        }
        private void persist(User user) {
            try{
                connect();
                stmp.execute("INSERT INTO users (login, pass, nick") VALUES ("
                        + user.getLogin() + ", "
                        +  user.getPassword() + ", " + user.getNickname() + " )");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }


