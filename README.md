# Tea time

## Описание
Spring Boot приложение, реализующее автоматическую отправку писем по списку адресатов с напоминанием "Пора пить чай!"

## Стек технологий
* Java 17
* MongoDB 4.6.1
* Apache Maven 3.8.5
* Spring Boot 2.7.5
* Hateoas 1.5.2
* Lombok 1.18.24
* Checkstyle 8.29
* Swagger 2.2.8

## Требуемое окружение для запуска проекта
* Postman
* JDK 17
* Apache Maven 3.8

## Инструкция по запуску проекта в Docker
1) Скачать репозиторий `git clone https://github.com/denfort50/tea-time`
2) Перейти в папку с проектом `cd c:\projects\tea-time`
3) Собрать проект `mvn install`
4) Собрать образ `docker build -t tea-time .`
5) Запустить контейнер `docker-compose up`
