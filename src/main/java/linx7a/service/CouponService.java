package linx7a.service;

import linx7a.entity.Coupon;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.nio.channels.SeekableByteChannel;
import java.util.List;

@Service
public class CouponService {
    private final SessionFactory sessionFactory;

    public CouponService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Coupon saveCoupon(Coupon coupon) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(coupon);
            transaction.commit();
            return coupon;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void deleteCoupon(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Coupon coupon = session.find(Coupon.class, id);
            session.remove(coupon);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public Coupon getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Coupon coupon = session.find(Coupon.class, id);
            return coupon;
        }
    }

    public List<Coupon> findAll() {
        try (Session session = sessionFactory.openSession()){
            List<Coupon> allCoupons = session
                    .createQuery("SELECT c FROM Coupon c", Coupon.class)
                    .list();
            return allCoupons;
        }
    }

    public Coupon updateCoupon(Coupon coupon) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            coupon = session.merge(coupon);
            transaction.commit();
            return coupon;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}

