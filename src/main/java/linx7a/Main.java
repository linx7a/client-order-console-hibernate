package linx7a;

import linx7a.console.AddOrderCommand;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("linx7a");
        AddOrderCommand addOrderCommand = context.getBean(AddOrderCommand.class);

        addOrderCommand.execute();
    }
}