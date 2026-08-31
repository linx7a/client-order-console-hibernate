package linx7a;

import linx7a.entity.Order;
import linx7a.service.OrderService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("linx7a");

        OrderService orderService = context.getBean(OrderService.class);

        Order order1 = orderService.saveOrder(new Order(LocalDate.of(2026, 8, 1), new BigDecimal("1500.00"), "NEW"));
        Order order2 = orderService.saveOrder(new Order(LocalDate.of(2026, 8, 1), new BigDecimal("500.00"), "NEW"));
        Order order3 = orderService.saveOrder(new Order(LocalDate.of(2026, 8, 15), new BigDecimal("2000.00"), "COMPLETED"));

        System.out.println("Созданы заказы: " + order1.getId() + ", " + order2.getId() + ", " + order3.getId());

        List<Order> filtered = orderService.findByFilters(
                LocalDate.of(2026, 8, 1),
                new BigDecimal("1000"),
                "NEW"
        );

        System.out.println("Найдено заказов по фильтру: " + filtered.size());
        for (Order o : filtered) {
            System.out.println(" - " + o);
        }
    }
}