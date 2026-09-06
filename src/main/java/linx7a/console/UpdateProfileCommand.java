package linx7a.console;

import linx7a.entity.Client;
import linx7a.entity.Profile;
import linx7a.service.ClientService;
import linx7a.service.ProfileService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class UpdateProfileCommand implements OperationCommand {
    private final ProfileService profileService;
    private final ClientService clientService;

    public UpdateProfileCommand(ProfileService profileService, ClientService clientService) {
        this.profileService = profileService;
        this.clientService = clientService;
    }
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void execute() {
        System.out.println("Выберите клиента по id:");
        Long id = Long.parseLong(scanner.nextLine().trim());
        Client client = clientService.getById(id);
        Profile profile = client.getProfile();

        System.out.println("Новый адрес (Enter, чтобы не менять):");
        String address = scanner.nextLine().trim();
        if (!address.isBlank()) {
            profile.setAddress(address);
        }
        System.out.println("Новый номер телефона(Enter, чтобы не менять):");
        String phone = scanner.nextLine().trim();
        if (!phone.isBlank()) {
            profile.setPhone(phone);
        }
        profileService.updateProfile(profile);
        System.out.println("Профиль успешно обновлён. Новый адрес клиента: " + profile.getAddress() + ",  номер телефона: " + profile.getPhone());

    }
    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.PROFILE_UPDATE;
    }
}
