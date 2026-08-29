package linx7a.service;

import linx7a.entity.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    private final SessionFactory sessionFactory;

    public ClientService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Client saveClient(Client client) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(client);
            transaction.commit();
            return client;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void deleteClient(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Client client = session.find(Client.class, id);
            session.remove(client);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public Client getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Client client = session.find(Client.class, id);
            return client;
        }
    }

    /**
     * Загружает клиента вместе со списком его купонов, форсируя инициализацию
     * LAZY коллекции внутри открытой сессии.
     *
     * В отличие от fetch = EAGER на самой связи, этот подход тратит меньше ресурсов:
     * купоны подгружаются только тогда, когда они реально нужны, через этот
     * конкретный метод, а не при каждом обычном обращении к Client.
     */

    public Client getByIdWithCoupons(Long id) {
        try(Session session = sessionFactory.openSession()) {
            Client client = session.find(Client.class, id);
            client.getCoupons().size(); // форсируем инициализацию LAZY-коллекции внутри сессии
            return client;
        }
    }

    public List<Client> findAll() {
        try (Session session = sessionFactory.openSession()) {
            List<Client> allClients = session
                    .createQuery("SELECT c FROM Client c", Client.class)
                    .list();
            return allClients;
        }

    }

    public Client updateClient(Client client) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            client = session.merge(client);
            transaction.commit();
            return client;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
