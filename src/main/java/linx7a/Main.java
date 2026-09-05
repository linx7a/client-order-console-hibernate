package linx7a;

import linx7a.console.ConsoleListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("linx7a");
        ConsoleListener listener = context.getBean(ConsoleListener.class);
        listener.run();
    }
}