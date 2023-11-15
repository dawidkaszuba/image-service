# image-service
Mikroserwis pobiera dane z kafki tj informacje o obrazkach. Pobiera obrazek na podstawie otrzymanego
url'a i zapisuje go do AWS S3 bucket. Po zapisaniu wrzuca wiadomość na kafkę z informacją o nowej
lokalizacji pobranego obrazka.

### Autorzy
- Dawid Kaszuba (dawid.kaszuba1@edu.ueakt.pl)