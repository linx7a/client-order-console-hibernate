package linx7a;

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

        ClientService clientService = context.getBean(ClientService.class);
        OrderService orderService = context.getBean(OrderService.class);

        Client client1 = clientService.saveClient(new Client("Гоша Пирожков", "gosha" + new Random().nextInt(10000000) + "@example.com", LocalDate.now()));
        Client client2 = clientService.saveClient(new Client("Дуся Коржикова", "dusya" + new Random().nextInt(10000000) + "@example.com", LocalDate.now()));

        Order order1 = new Order(LocalDate.now(), new BigDecimal("1000"), "NEW");
        order1.setClient(client1);
        orderService.saveOrder(order1);

        Order order2 = new Order(LocalDate.now(), new BigDecimal("2000"), "NEW");
        order2.setClient(client2);
        orderService.saveOrder(order2);

        Order order3 = new Order(LocalDate.now(), new BigDecimal("1500"), "COMPLETED");
        order3.setClient(client2);
        orderService.saveOrder(order3);

        System.out.println("Созданы клиенты и заказы");

        // удаляем client1, у него есть order1
        clientService.deleteClient(client1.getId());
        System.out.println("client1 удалён");

        // проверяем, что order1 тоже исчез каскадом
        Order order1AfterDelete = orderService.getById(order1.getId());
        System.out.println("order1 после удаления client1: " + order1AfterDelete); //должен быть null

        // проверяем, что client2 и его заказы НЕ затронуты
        Order order2AfterDelete = orderService.getById(order2.getId());
        Order order3AfterDelete = orderService.getById(order3.getId());

        System.out.println("order2: " + orderService.getById(order2.getId())); //должен остаться
        System.out.println("order3: " + orderService.getById(order3.getId())); //должен остаться

        System.out.println("order2: " + order2AfterDelete);
        System.out.println("order3: " + order3AfterDelete);

    }
}