package linx7a.console;

import linx7a.entity.Client;
import linx7a.entity.Coupon;
import linx7a.entity.Profile;
import linx7a.service.ClientService;
import linx7a.service.CouponService;
import linx7a.service.ProfileService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Component
public class AddClientCommand implements OperationCommand {
    private final ClientService clientService;
    private final CouponService couponService;
    private final ProfileService profileService;

    public AddClientCommand(ClientService clientService, CouponService couponService, ProfileService profileService) {
        this.clientService = clientService;
        this.couponService = couponService;
        this.profileService = profileService;
    }

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void execute() {
        System.out.println("Имя клиента:");
        String name = scanner.nextLine();

        System.out.println("Email клиента:");
        String email = scanner.nextLine();

        System.out.println("Адрес:");
        String address = scanner.nextLine();

        System.out.println("Номер телефона:");
        String phone = scanner.nextLine();

        Client client = new Client(name, email, LocalDate.now());
        Profile profile = new Profile(address, phone, client);
        client.setProfile(profile);
        clientService.saveClient(client);

        System.out.println("Клиент \"" + client.getName() + "\" сохранён с id " + client.getId());

        List<Coupon> allCoupons = couponService.findAll();
        if (allCoupons.isEmpty()) {
            System.out.println("Доступных купонов пока нет.");
        } else {
            System.out.println("Доступные купоны: ");
            for (Coupon coupon : allCoupons) {
                System.out.println(coupon.getId() + ") " + coupon.getCode() + " - скидка " + coupon.getDiscount() + "%");
            }
            System.out.println("Введите id купонов через запятую (или оставьте пустым, если не нужны):");
            String input = scanner.nextLine();

            if (!input.isBlank()) {
                String[] ids = input.split(",");
                for (String id : ids) {
                    Long couponId = Long.parseLong(id.trim());
                    Coupon coupon = couponService.getById(couponId);
                    client.getCoupons().add(coupon);
                }
                clientService.updateClient(client);
            }
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.USER_ADD;
    }

}
