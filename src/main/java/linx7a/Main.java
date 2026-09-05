package linx7a;

import linx7a.console.FindOrdersCommand;
import linx7a.entity.Client;
import linx7a.entity.Order;
import linx7a.service.ClientService;
import linx7a.service.OrderService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("linx7a");
        ClientService clientService = context.getBean(ClientService.class);
        OrderService orderService = context.getBean(OrderService.class);
        FindOrdersCommand findOrdersCommand = context.getBean(FindOrdersCommand.class);

        Client client7 = clientService.getById(7L);
        Client client8 = clientService.getById(8L);

        Order order1 = orderService.saveOrder(new Order(LocalDate.of(2026, 8, 31), new BigDecimal("500"), "NEW"));
        order1.setClient(client7);
        Order order2 = orderService.saveOrder(new Order(LocalDate.of(2026, 8, 31), new BigDecimal("3000"), "COMPLETED"));
        order2.setClient(client7);
        Order order3 = orderService.saveOrder(new Order(LocalDate.of(2026, 9, 4), new BigDecimal("1200"), "NEW"));
        order3.setClient(client8);
        Order order4 = orderService.saveOrder(new Order(LocalDate.of(2026, 9, 4), new BigDecimal("50000"), "CANCELLED"));
        order4.setClient(client8);
        Order order5 = orderService.saveOrder(new Order(LocalDate.of(2026, 9, 1), new BigDecimal("800"), "COMPLETED"));
        order5.setClient(client7);
        Order order6 = orderService.saveOrder(new Order(LocalDate.of(2026, 9, 1), new BigDecimal("10000"), "NEW"));
        order6.setClient(client8);

        System.out.println("Тестовые заказы созданы");

        findOrdersCommand.execute();

    }
}