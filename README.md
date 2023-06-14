# Tea time

## Описание
Spring Boot приложение, реализующее автоматическую отправку писем по списку адресатов с напоминанием "Пора пить чай!"

## Стек технологий
* Java 17
* PostgreSQL 14
* Apache Maven 3.8.5
* Spring Boot 2.7.5
* Liquibase 3.6.2
* Lombok 1.18.24
* Checkstyle 8.29

## Требуемое окружение для запуска проекта
* Postman
* JDK 17
* Apache Maven 3.8
* PostgreSQL 14

## Инструкция по запуску проекта в Docker (пока что не работает)
1) Скачать репозиторий `git clone https://github.com/denfort50/tea-time`
2) Перейти в папку с проектом `cd c:\projects\tea-time`
3) Собрать проект `mvn install`
4) Собрать образ `docker build -t tea-time .`
5) Запустить контейнер `docker-compose up`
