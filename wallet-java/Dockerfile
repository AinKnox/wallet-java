# Используем образ OpenJDK 17 JDK slim
FROM openjdk:17-jdk-slim

# Определяем точку монтирования временной директории
VOLUME /tmp

# Копируем JAR файл в контейнер
COPY target/wallet-java-0.0.1-SNAPSHOT.jar /wallet-java.jar

# Устанавливаем точку входа для запуска приложения
ENTRYPOINT ["java", "-jar", "/wallet-java.jar"]
