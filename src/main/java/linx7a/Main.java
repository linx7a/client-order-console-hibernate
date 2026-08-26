package linx7a;

import linx7a.entity.Client;
import linx7a.entity.Profile;
import linx7a.service.ClientService;
import linx7a.service.ProfileService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("linx7a");

        ClientService clientService = context.getBean(ClientService.class);
        ProfileService profileService = context.getBean(ProfileService.class);

        Random random = new Random();

        // 1. Сохраняем клиента
        Client client = clientService.saveClient(
                new Client("Сёма Компотов", "syoma.kompotov" + random.nextInt(10000000) + "@example.com", LocalDate.now())
        );
        System.out.println("Клиент " + client.getName() + " сохранён с id: " + client.getId());

        // 2. Создаём и сохраняем профиль, ссылающийся на клиента
        Profile profile = new Profile("г. Варенье, ул. Сладкая, д. 21", "+7999" + random.nextInt(10000000), client);
        profile = profileService.saveProfile(profile);
        System.out.println("Профиль сохранён с id: " + profile.getId() + ", привязан к клиенту id: " + profile.getClient().getId());

        // 3. Проверяем каскад: получаем клиента заново из базы, смотрим, подтянулся ли профиль
        Client foundClient = clientService.getById(client.getId());
        System.out.println("Клиент из базы: " + foundClient.getName() + ", его профиль (каскадом): " + foundClient.getProfile());

        Profile foundProfile = profileService.getById(profile.getId());
        System.out.println("Профиль из базы принадлежит клиенту: " + foundProfile.getClient().getName());
    }
}