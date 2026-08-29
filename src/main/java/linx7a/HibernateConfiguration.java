package linx7a;

import linx7a.entity.Client;
import linx7a.entity.Coupon;
import linx7a.entity.Order;
import linx7a.entity.Profile;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfiguration {
    @Bean
        public SessionFactory sessionFactory() {
            org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
            configuration
                    .addAnnotatedClass(Client.class)
                    .addAnnotatedClass(Profile.class)
                    .addAnnotatedClass(Order.class)
                    .addAnnotatedClass(Coupon.class)
                    .addPackage("linx7a")
                    .setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
                    .setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5434/postgres")
                    .setProperty("hibernate.connection.username", "postgres")
                    .setProperty("hibernate.connection.password", "root")
                    .setProperty("hibernate.show_sql", "true")
                    .setProperty("hibernate.hbm2ddl.auto", "update");
            return configuration.buildSessionFactory();
        }
}
