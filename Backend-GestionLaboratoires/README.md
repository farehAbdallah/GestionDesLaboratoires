# Configuration du Microservice

Ce fichier documente les propriétés principales requises pour configurer les microservices dans le projet

## Table des Matières

- [Configuration de Base](#configuration-de-base)
- [Configuration de la Base de Données](#configuration-de-la-base-de-données)
- [Eureka Client pour Discovery Server](#eureka-client-pour-discovery-server)
- [Configuration du Serveur Config](#configuration-du-serveur-config)
- [Propriétés Additionnelles](#propriétés-additionnelles)

---

### Configuration de Base

```properties
spring.application.name=serviceName
```
### Configuration de la Base de Données

```properties
server.port=portDeService
spring.datasource.url=jdbc:mysql://localhost:3306/dbName
spring.datasource.username=username
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```


### Eureka Client pour Discovery Server

```properties
eureka.client.serviceUrl.defaultZone=http://localhost:8761/eureka/
eureka.instance.preferIpAddress=true
```

### Configuration du Serveur Config

```properties
spring.cloud.config.enabled=true
spring.cloud.config.uri=http://localhost:8888
spring.cloud.config.import-check.enabled=false
```





