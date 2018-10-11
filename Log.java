package ru.geekbrains.chendyreva.homework6;

public class Log {
    public static void main(String[] args) {
        log.info(String msg, throwable);
    }
    String msg = "Сообщение {}, от {}";
    Throwable throwable = new Throwable();
    }

/////////////////////////2
//handlers =java.util.logging. FileHandler.level=ALL
//        java.util.logging.FileHandler.level =ALL
//        java.util.logging.FileHandler.formatter =java.util.logging.SimpleFormatter
//        java.util.logging.FileHandler.limit = 1000000
//        java.util.logging.FileHandler.pattern   = log.txt
//        #
//        java.util.logging.ConsoleHandler.level = ALL
//        java.util.logging.ConsoleHandler.pattern = log.log
//        java.util.logging.ConsoleHandler.formatter =java.util.logging.SimpleFormatter
//
//        LogManager.getLogManager().readConfiguration(<ваш класс>.class.getResourceAsStream("logging.properties"));
//////////////////////////////////1
//import javafx.concurrent.Task;
//
//@slf4j
//
//public class Log {
//
//    private static final Logger logger = LoggerFactory.getLogger(Manager.class);
//
//
//    public boolean processTask(Task task)    {
//        logger.debug("processTask id="+task.getId());
//        try  {
//            task.start();
//            task.progress();
//            task.compleate();
//            return true;
//        }
//        catch(Exception e)
//        {
//            logger.error("Unknown error", e);
//            return false;
//        }
//    }
//}
