[![CI](https://github.com/TriggerYonyon/tsukete_backend/actions/workflows/.github-ci.yml/badge.svg)](https://github.com/TriggerYonyon/tsukete_backend/actions/workflows/.github-ci.yml)
# Tukete core-system

---

## Setup

1. Create env file and set environment <br>
    ```dotenv
    POSTGRES_DB=yonyon
    POSTGRES_USER=yonyon
    POSTGRES_PASSWORD=yonyon
    POSTGRES_URL=jdbc:postgresql://db:5432/${POSTGRES_DB}
    ```
2. Run docker container
   ```commandline
   docker compose up -d
   ```
3. Run database migration
    ```commandline
    ./gradlew flywayMigrate
    ```
## Run Application
1. Build
   ```commandline
   ./gradlew build
   ```
2. Run
   ```commandline
   java -jar ./build/libs/core-system-0.1-all.jar 
   ```
   




