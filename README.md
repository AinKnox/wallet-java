Тестовое задание для JavaCode

Само тестовое звучит так:

Напишите приложение, которое по REST принимает запрос вида
POST api/v1/wallet
{
valletId: UUID,
operationType: DEPOSIT or WITHDRAW,
amount: 1000
}
после выполнять логику по изменению счета в базе данных
также есть возможность получить баланс кошелька
GET api/v1/wallets/{WALLET_UUID}
стек:
java 8-17
Spring 3
Postgresql
Должны быть написаны миграции для базы данных с помощью liquibase
Обратите особое внимание проблемам при работе в конкурентной среде (1000 RPS по
одному кошельку). Ни один запрос не должен быть не обработан (50Х error)
Предусмотрите соблюдение формата ответа для заведомо неверных запросов, когда
кошелька не существует, не валидный json, или недостаточно средств.
приложение должно запускаться в докер контейнере, база данных тоже, вся система
должна подниматься с помощью docker-compose
предусмотрите возможность настраивать различные параметры как на стороне
приложения так и базы данных без пересборки контейнеров.
эндпоинты должны быть покрыты тестами.

Были использованны:
Spring Boot Starter Data JPA: Используется для работы с базой данных через JPA (Java Persistence API). Это позволяет взаимодействовать с базой данных, используя объектно-ориентированный подход.

Spring Boot Starter Web: Предоставляет инструменты для разработки веб-приложений с использованием Spring MVC и встроенного веб-сервера.

Liquibase Core: Используется для управления миграциями базы данных. Liquibase позволяет автоматически обновлять схему базы данных при изменении структуры приложения.

Spring Boot DevTools: Предоставляет различные инструменты для удобства разработки, такие как автоматическая перезагрузка приложения при изменениях в исходном коде.

PostgreSQL Driver: Драйвер для взаимодействия с базой данных PostgreSQL.

Spring Boot Starter Test: Набор зависимостей для тестирования приложения, включая интеграционное тестирование.

Java Persistence API (JPA): Стандарт Java для управления реляционными данными в приложении.

Maven Surefire Plugin: Плагин для запуска тестов в Maven.

Spring Boot Maven Plugin: Плагин для сборки и запуска Spring Boot приложения в виде исполняемого JAR-файла.

