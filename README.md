# Biodiversity API

### Prerequisites
- Java 1.8
- Postgresql 9.3
- Elasticsearch 6.0.1 (uncomment `http.port: 9200` in `config/elasticsearch.yml`) &rarr; [Download](https://www.elastic.co/downloads/past-releases/elasticsearch-6-0-1)
- Redis 4.0.9 &rarr; [Download](http://download.redis.io/releases/redis-4.0.9.tar.gz)


### Editor
Application was primarily developed using *Eclipse* but it works on *Netbeans w/ Gradle Plugin*


### Configuration
If you don't want to use `BIODIV_API_CONFIG` environment variable which is not ideal for IDE based development you can update it to load file from classpath
1. Move `@config/biodiv-api.properties` to `src/main/resources/biodiv-api.properties` and update configuration.
2. update `src/main/java/biodiv/BiodivServletContextListener.java` 
   ```java
   - config = configs.properties(new File(System.getenv(ENV_NAME)));
   + config = configs.properties(new File("biodiv-api.properties"));
   ```

### Running
```sh
./gradlew clean           # to clean project
./gradlew build           # to test build
./gradlew war             # to build deployable war
./gradlew tomcatRun       # to run application on tomcat
```
