package linx7a;

import linx7a.entity.Client;
import linx7a.entity.Coupon;
import linx7a.service.ClientService;
import linx7a.service.CouponService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("linx7a");

        ClientService clientService = context.getBean(ClientService.class);
        CouponService couponService = context.getBean(CouponService.class);

        // 1. Создаём клиента
        Client client = clientService.saveClient(new Client("Гоша Пирожков", "gosha" + new Random().nextInt(10000000) + "@example.com", LocalDate.now()));
        System.out.println("Клиент сохранён: " + client.getName());

        // 2. Создаём два купона
        Coupon coupon1 = couponService.saveCoupon(new Coupon("SUMMER2026", 15.0f, LocalDate.now().plusMonths(1)));
        Coupon coupon2 = couponService.saveCoupon(new Coupon("WINTER2026", 20.0f, LocalDate.now().plusMonths(2)));
        System.out.println("Купоны сохранены: " + coupon1.getCode() + ", " + coupon2.getCode());

        // 3. Привязываем купоны клиенту
        client.getCoupons().add(coupon1);
        client.getCoupons().add(coupon2);
        clientService.updateClient(client);

       // 4. Проверяем связь: заново достаём клиента, смотрим сколько купонов

        Client foundClient = clientService.getByIdWithCoupons(client.getId());
        System.out.println("У клиента купонов: " + foundClient.getCoupons().size());
    }
}