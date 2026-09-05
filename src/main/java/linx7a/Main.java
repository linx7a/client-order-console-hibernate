package linx7a;

import linx7a.console.DeleteClientCommand;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("linx7a");
        DeleteClientCommand deleteClientCommand = context.getBean(DeleteClientCommand.class);

        deleteClientCommand.execute();
    }
}