# Client Order Console (Hibernate Core)

Консольное приложение для управления клиентами, их заказами и купонами.
Финальный проект курса Hibernate Core (Sorokin School), реализован на чистом
Hibernate Core (Session API) без Spring Data JPA – Spring используется только
для Dependency Injection.

## Стек

- Java 18
- Hibernate ORM 7.4.5.Final (чистый Session API, без Spring Data JPA)
- PostgreSQL 18 (в Docker)
- Spring Context (только DI)
- Maven

## Структура проекта

```
linx7a/
├── HibernateConfiguration.java   # конфигурация SessionFactory
├── Main.java                     # точка входа, запускает ConsoleListener
├── console/
│   ├── ConsoleListener.java      # главный цикл меню
│   ├── OperationCommand.java     # интерфейс команды
│   ├── ConsoleOperationType.java # enum типов операций
│   ├── AddClientCommand.java
│   ├── DeleteClientCommand.java
│   ├── UpdateProfileCommand.java
│   ├── AddOrderCommand.java
│   ├── UpdateCouponCommand.java
│   ├── FindOrdersCommand.java
│   └── ExitCommand.java
├── entity/
│   ├── Client.java
│   ├── Profile.java
│   ├── Order.java
│   └── Coupon.java
└── service/
    ├── ClientService.java
    ├── ProfileService.java
    ├── OrderService.java
    └── CouponService.java
```

## ER-схема

```
Client (id, name, email, registrationDate)
  ├── 1:1  → Profile (id, address, phone)
  ├── 1:N  → Order   (id, orderDate, totalAmount, status)
  └── N:M  → Coupon  (id, code, discount, expirationDate)
             через вспомогательную таблицу client_coupons (client_id, coupon_id)
```

Связи:
- **Client ↔ Profile** – `@OneToOne`, владеющая сторона – `Profile`
  (`@JoinColumn(client_id)`), каскад `ALL` на стороне `Client`.
- **Client ↔ Order** – `@OneToMany` / `@ManyToOne`, каскад `ALL` на стороне
  `Client`: при удалении клиента удаляются все его заказы.
- **Client ↔ Coupon** – `@ManyToMany`, владеющая сторона – `Client`, связь
  через `@JoinTable(client_coupons)`.

## Запуск

1. Поднять PostgreSQL в Docker (порт `5434`):
   ```bash
   docker run -d --name client-order-postgres \
     -e POSTGRES_PASSWORD=root \
     -p 5434:5432 \
     postgres
   ```
2. Собрать и запустить проект:
   ```bash
   mvn clean install
   mvn exec:java -Dexec.mainClass="linx7a.Main"
   ```
   (или через IDE – запустить `Main.java`)

Схема таблиц создаётся автоматически при старте
(`hibernate.hbm2ddl.auto=update`).

## Функциональность (меню)

1. **Добавить клиента** – вводятся имя, email, адрес, телефон; профиль
   создаётся автоматически вместе с клиентом (каскадно); опционально можно
   привязать существующие купоны.
2. **Удалить клиента** – удаление клиента вместе с профилем и всеми заказами
   (каскадно), связи в `client_coupons` также корректно очищаются.
3. **Обновить профиль** – изменение адреса и/или телефона существующего
   клиента по ID.
4. **Добавить заказ** – новый заказ для существующего клиента (дата, сумма,
   статус).
5. **Обновить купоны** – редактирование кода, скидки и даты окончания
   действия купона по ID.
6. **Найти заказ** – поиск заказов по фильтрам (дата, минимальная сумма,
   статус) через кастомный JPQL-запрос.
7. **Выход** – завершение приложения.

## Решение проблемы N+1

`ClientService.findAllWithOrders()` использует `LEFT JOIN FETCH` для загрузки
клиентов вместе с заказами одним запросом. `ClientService.getByIdWithCoupons()`
форсирует инициализацию LAZY-коллекции купонов внутри открытой сессии, не
переводя связь в EAGER на уровне маппинга.
