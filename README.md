# FreightFlow

Pet-project для управления грузоперевозками на стеке Java 17 + Spring MVC 4 + Hibernate 4.

## Стек

- Java 17
- Spring 4.0.6 (MVC, ORM, TX)
- Hibernate 4 (JPA annotations)
- Maven (WAR packaging)
- H2 in-memory (`MODE=PostgreSQL`)
- Tomcat via `tomcat7-maven-plugin`
- JUnit 4 + Spring Test + MockMvc
- Checkstyle + `.editorconfig`

## Запуск

1. Сборка и тесты:

   ```bash
   mvn clean test
   ```

2. Запуск в embedded Tomcat:

   ```bash
   mvn tomcat7:run
   ```

3. Базовый URL:

   - `http://localhost:8080/freightflow/api/cargos`
   - `http://localhost:8080/freightflow/api/carriers`
   - `http://localhost:8080/freightflow/api/shipments`

## Настройка pre-commit hook

В репозитории есть hook: `.githooks/pre-commit`.

Чтобы включить:

```bash
git config core.hooksPath .githooks
chmod +x .githooks/pre-commit
```

Hook запускает `mvn checkstyle:check`, если в staging есть измененные `*.java`.
