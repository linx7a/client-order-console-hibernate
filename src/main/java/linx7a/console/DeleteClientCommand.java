package linx7a.console;

import linx7a.entity.Client;
import linx7a.service.ClientService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class DeleteClientCommand implements OperationCommand {
    private final ClientService clientService;

    public DeleteClientCommand(ClientService clientService) {
        this.clientService = clientService;
    }
    private final Scanner scanner = new Scanner(System.in);
    @Override
    public void execute() {
        System.out.println("ID клиента:");
        Long clientId = Long.parseLong(scanner.nextLine().trim());
        Client client = clientService.getById(clientId);
        if (client == null) {
            System.out.println("Клиент с ID: " + clientId + " не найден.");
            return;
        }
        String clientName = client.getName();

        clientService.deleteClient(clientId);
        System.out.println("Клиент " + clientName + " удалён.");
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.USER_DELETE;
    }
}
