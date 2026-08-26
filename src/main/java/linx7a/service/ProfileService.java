package linx7a.service;

import linx7a.entity.Profile;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {
    private final SessionFactory sessionFactory;

    public ProfileService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Profile saveProfile(Profile profile) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(profile);
            transaction.commit();
            return profile;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void deleteProfile(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Profile profile = session.find(Profile.class, id);
            session.remove(profile);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public Profile getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Profile profile = session.find(Profile.class, id);
            return profile;
        }
    }

    public List<Profile> findAll() {
        try (Session session = sessionFactory.openSession()) {
            List<Profile> allProfiles = session
                    .createQuery("SELECT p FROM Profile p", Profile.class)
                    .list();
            return allProfiles;
        }
    }

    public Profile updateProfile(Profile profile) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            profile = session.merge(profile);
            transaction.commit();
            return  profile;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }
}
