package linx7a.console;

import linx7a.entity.Coupon;
import linx7a.service.CouponService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Component
public class UpdateCouponCommand implements OperationCommand {
    private final CouponService couponService;

    public UpdateCouponCommand(CouponService couponService) {
        this.couponService = couponService;
    }

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void execute() {
        System.out.println("ID купона:");
        Long couponId = Long.parseLong(scanner.nextLine().trim());
        Coupon coupon = couponService.getById(couponId);
        if (coupon == null) {
            System.out.println("Купон с ID: " + couponId + " не найден.");
            return;
        }

        System.out.println("Новый код купона (Enter, чтобы не менять):");
        String code = scanner.nextLine().trim();
        if (!code.isBlank()) {
            coupon.setCode(code);
        }

        System.out.println("Новая скидка (Enter, чтобы не менять):");
        String input = scanner.nextLine().trim();
        if (!input.isBlank()) {
            Float discount = Float.parseFloat(input);
            coupon.setDiscount(discount);
        }

        System.out.println("Дата окончания действия (ДД.ММ.ГГГГ) (Enter, чтобы не менять):");
        String dateInput = scanner.nextLine().trim();
        if (!dateInput.isBlank()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate expirationDate = LocalDate.parse(dateInput, formatter);
            coupon.setExpirationDate(expirationDate);
        }
        couponService.updateCoupon(coupon);
        System.out.println("Купон успешно обновлен. Новый код: " + coupon.getCode() + ", новая скидка: " + coupon.getDiscount()
                + ", дата окончания действия: " + coupon.getExpirationDate());

    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.COUPONS_UPDATE;
    }
}
