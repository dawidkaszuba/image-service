# image-service
Mikroserwis pobierający obrazki

## Wymagania

- Java 17
- Kafka

## Jak uruchomić

- skopiuj plik /config/application.yaml.tpl w tej samej lokalizacji, ale bez rozszerzenia .tpl i uzupełnij parametry
- uruchom Kafkę lokalnie lub podepnij się do zewnętrznej
- w głównym katalogu odpal: `mvn spring-boot:run`

## Tworzenie obrazu i wrzucenie do zewnętrznego registry

- zainstaluj: `mvn clean -Dmaven.test.skip=true install`
- Zbuduj obraz: `docker build -t dawidkaszuba/image-service:latest .`
- Wrzuć na zewnętrzne registry: `docker push dawidkaszuba/image-service:latest`



## Autorzy
- Dawid Kaszuba (dawid.kaszuba1@edu.ueakt.pl)


