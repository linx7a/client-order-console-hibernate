package linx7a.console;

import linx7a.entity.Order;
import linx7a.service.OrderService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@Component
public class FindOrdersCommand implements OperationCommand {
    final private OrderService orderService;

    public FindOrdersCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void execute() {
        System.out.println("Дата заказа (ДД.ММ.ГГГГ):");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate orderDate = LocalDate.parse(scanner.nextLine(), formatter);

        System.out.println("Минимальная сумма:");
        BigDecimal minAmount = new BigDecimal(scanner.nextLine());

        System.out.println("Статус:");
        String status = scanner.nextLine();

        List<Order> foundOrders = orderService.findByFilters(orderDate, minAmount, status);
        if (!foundOrders.isEmpty()) {
            for (Order o : foundOrders) {
                System.out.println("Заказ с ID: " + o.getId() + ", от " + o.getOrderDate() +
                        ", на сумму " + o.getTotalAmount() + " (" + o.getStatus() + ")");
            }
        } else {
            System.out.println("Ничего не найдено.");
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ORDER_FIND;
    }
}
