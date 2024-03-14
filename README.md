# Ветка: kafka-low-code
### Пример тестового проекта с и спользованием низкоуровневой библиотеки org.apache.kafka

* Spring Boot consumer apps
* Spring Boot producer apps
* Kafka klaster in docker
* Скрипт для создания топика и 3 partition -> `/sh/delete-create-topic.sh`
  

> В проект добавлены 2 **docker-compose** файла:
1. ***docker-compose*** - zookeeper + broker Kafka

2. ***docker-compose2*** - zookeeper + broker Kafka + 3 consumer объединенных в одну группу
![alt text](https://github.com/Aleshawork/kafka-learn/blob/master/materials/%D0%A1%D0%BD%D0%B8%D0%BC%D0%BE%D0%BA%20%D1%8D%D0%BA%D1%80%D0%B0%D0%BD%D0%B0%202024-03-14%20%D0%B2%2022.13.14.png)
