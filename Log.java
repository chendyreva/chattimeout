package ru.geekbrains.chendyreva.homework6;
import java.time.LocalTime;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;

public class Log {
     static void log(String msg) {
         System.out.format("[%] %s%n",LocalTime.now(),msg);
         System.out.format("[%] %s%n",SQLException.class,msg);
         System.out.format("[%] %s%n",Connection.class,msg);
         System.out.format("[%] %s%n",DriverManager.class,msg);


     }
    }

