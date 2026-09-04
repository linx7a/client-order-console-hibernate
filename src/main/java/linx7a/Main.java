package linx7a;

import linx7a.console.UpdateProfileCommand;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("linx7a");
        UpdateProfileCommand updateProfileCommand = context.getBean(UpdateProfileCommand.class);

        updateProfileCommand.execute();
    }
}