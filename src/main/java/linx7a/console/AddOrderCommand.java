package linx7a.console;

import linx7a.entity.Client;
import linx7a.entity.Order;
import linx7a.service.ClientService;
import linx7a.service.OrderService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

@Component
public class AddOrderCommand implements OperationCommand {
    private final Scanner scanner = new Scanner(System.in);
    private final ClientService clientService;
    private final OrderService orderService;

    public AddOrderCommand(ClientService clientService, OrderService orderService) {
        this.clientService = clientService;
        this.orderService = orderService;
    }

    @Override
    public void execute() {
        System.out.println("ID клиента:");
        Long id = Long.parseLong(scanner.nextLine());
        Client client = clientService.getById(id);

        System.out.println("Сумма заказа:");
        BigDecimal totalAmount = new BigDecimal(scanner.nextLine());

        System.out.println("Статус заказа:");
        String status = scanner.nextLine();

        Order order = new Order(LocalDate.now(), totalAmount, status);
        order.setClient(client);
        orderService.saveOrder(order);

        System.out.println("Заказ на сумму " + order.getTotalAmount() + " (статус: " + order.getStatus() + ") создан с id " + order.getId());
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ORDER_ADD;
    }
}
