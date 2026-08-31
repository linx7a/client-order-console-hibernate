package linx7a.service;

import linx7a.entity.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {
    private final SessionFactory sessionFactory;

    public OrderService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Order saveOrder(Order order) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(order);
            transaction.commit();
            return order;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void deleteOrder(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Order order = session.find(Order.class, id);
            session.remove(order);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public Order getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Order order = session.find(Order.class, id);
            return order;
        }
    }

    public List<Order> findAll() {
        try (Session session = sessionFactory.openSession()){
            List<Order> allOrders = session
                    .createQuery("SELECT o FROM Order o", Order.class)
                    .list();
            return allOrders;
        }
    }

    public Order updateOrder(Order order) {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            order = session.merge(order);
            transaction.commit();
            return order;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public List<Order> findByFilters(LocalDate date, BigDecimal minAmount, String status) {
        try (Session session = sessionFactory.openSession()){
            return session.createQuery(
                    "SELECT o FROM Order o WHERE o.orderDate = :date " +
                            "AND o.totalAmount >= :minAmount " +
                            "AND o.status = :status", Order.class)
                    .setParameter("date", date)
                    .setParameter("minAmount", minAmount)
                    .setParameter("status", status)
                    .list();
        }
    }
}
