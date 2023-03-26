# Используем python:3.9-slim-buster как базовый образ
FROM python:3.9-slim-buster

# Устанавливаем все необходимые зависимости
RUN apt-get update && apt-get install -y \
    gcc \
    libpq-dev \
    && rm -rf /var/lib/apt/lists/*

# Устанавливаем pandas
RUN pip install pandas==1.3.0

# Копируем исходный код проекта
COPY . /app
WORKDIR /app

# Используем alpine как базовый образ для второй части Dockerfile
FROM python:3.9-alpine

RUN apk update && apk add --no-cache gcc musl-dev libffi-dev python3-dev cargo g++
RUN ln -s /usr/include/locale.h /usr/include/xlocale.h

WORKDIR /app
COPY . /app

RUN pip install --upgrade pip && \
    pip install --no-cache-dir numpy==1.19.3 pandas==1.1.4 setuptools==57.4.0 wheel==0.37.0 && \
    pip install --no-cache-dir -r requirements.txt

# Используем openjdk:17-jdk-alpine3.13 как базовый образ для третьей части Dockerfile
FROM openjdk:17-jdk-alpine3.13

WORKDIR /app
COPY --from=0 /app /app

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
