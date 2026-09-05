package linx7a.console;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class ConsoleListener {
    private final Scanner scanner = new Scanner(System.in);
    private final Map<ConsoleOperationType, OperationCommand> commandMap = new HashMap<>();

    public ConsoleListener(List<OperationCommand> commands) {
        for (OperationCommand command : commands) {
            commandMap.put(command.getOperationType(), command);
        }
    }

    public void run() {
        while (true) {
            System.out.println("""
                    === Меню операций ===
                    1. Добавить клиента
                    2. Удалить клиента
                    3. Обновить профиль
                    4. Добавить заказ
                    5. Обновить купоны
                    6. Найти заказ
                    7. Выход
                    """);
            System.out.print("Введите номер команды: ");
            String input = scanner.nextLine().trim();
            try {
                ConsoleOperationType type = parseInput(input);
                OperationCommand command = commandMap.get(type);
                command.execute();
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка ввода: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Ошибка при выполнении операции: " + e.getMessage());
            }
        }
    }

    private ConsoleOperationType parseInput(String input) {
        return switch (input) {
            case "1" -> ConsoleOperationType.USER_ADD;
            case "2" -> ConsoleOperationType.USER_DELETE;
            case "3" -> ConsoleOperationType.PROFILE_UPDATE;
            case "4" -> ConsoleOperationType.ORDER_ADD;
            case "5" -> ConsoleOperationType.COUPONS_UPDATE;
            case "6" -> ConsoleOperationType.ORDER_FIND;
            case "7" -> ConsoleOperationType.EXIT;
            default -> throw new IllegalArgumentException("Неизвестная команда: " + input);
        };
    }
}
