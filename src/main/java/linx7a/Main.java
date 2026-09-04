package linx7a;

import linx7a.console.AddClientCommand;
import linx7a.entity.Client;
import linx7a.entity.Order;
import linx7a.service.ClientService;
import linx7a.service.OrderService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("linx7a");

        AddClientCommand addClientCommand = context.getBean(AddClientCommand.class);
        addClientCommand.execute();
    }
}