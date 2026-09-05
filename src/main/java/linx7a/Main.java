package linx7a;

import linx7a.console.ExitCommand;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("linx7a");
        ExitCommand exitCommand = context.getBean(ExitCommand.class);

        exitCommand.execute();
    }
}