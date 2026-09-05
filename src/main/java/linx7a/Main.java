package linx7a;

import linx7a.console.UpdateCouponCommand;
import linx7a.entity.Coupon;
import linx7a.service.CouponService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("linx7a");
        CouponService couponService = context.getBean(CouponService.class);
        UpdateCouponCommand updateCouponCommand = context.getBean(UpdateCouponCommand.class);

        Coupon coupon = couponService.saveCoupon(new Coupon("AUTUMN2026", 0.15F, LocalDate.now().plusMonths(1)));
        Coupon coupon1 = couponService.getById(coupon.getId());
        System.out.println("Купон c кодом: " + coupon1.getCode() + ", скидкой: " + coupon1.getDiscount()
                + " , истекающий: " + coupon1.getExpirationDate() + " сохранен с ID: " + coupon1.getId());

        updateCouponCommand.execute();
    }
}