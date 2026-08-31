package linx7a;

import linx7a.entity.Client;
import linx7a.entity.Coupon;
import linx7a.entity.Order;
import linx7a.service.ClientService;
import linx7a.service.CouponService;
import linx7a.service.OrderService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

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

        // проверяем N+1: должен быть ОДИН SQL-запрос, а не несколько
        List<Client> clientsWithOrders = clientService.findAllWithOrders();
        System.out.println("Клиентов с заказами: " + clientsWithOrders.size());
        for (Client c : clientsWithOrders) {
            System.out.println(c.getName() + " -> заказов: " + c.getOrders().size());
        }
    }
}