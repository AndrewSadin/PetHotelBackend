FROM openjdk:8-jdk-alpine


# указываем точку монтирования для внешних данных внутри контейнера (как мы помним, это Линукс)
VOLUME /tmp

# внешний порт, по которому наше приложение будет доступно извне
EXPOSE 8080

# указываем, где в нашем приложении лежит джарник
ARG JAR_FILE=build/libs/demo-0.0.1-SNAPSHOT.jar

# добавляем джарник в образ под именем rebounder-chain-backend.jar
ADD ${JAR_FILE} demo-0.0.1-SNAPSHOT.jar

# команда запуска джарника
ENTRYPOINT ["java","-jar","/demo-0.0.1-SNAPSHOT.jar"]