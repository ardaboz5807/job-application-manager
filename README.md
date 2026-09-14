# Job Application Manager

Desktop-Anwendung zur Verwaltung von Bewerbungen mit **Java Swing**, **SQLite** und **Maven**.

Die Anwendung bietet eine grafische Benutzeroberfläche, in der Bewerbungen gespeichert, angezeigt, gefiltert, bearbeitet und gelöscht werden können. Die Daten werden dauerhaft in einer lokalen SQLite-Datenbank gespeichert.

## Funktionen

- Bewerbungen hinzufügen
- Alle Bewerbungen in einer Tabelle anzeigen
- Bewerbungen nach Status filtern
- Status und letztes Update einer Bewerbung bearbeiten
- Einzelne Bewerbungen löschen
- Alle Bewerbungen löschen
- Lokale Speicherung mit SQLite

## Technologien

- Java 21
- Java Swing
- SQLite
- JDBC
- Maven

## Projektstruktur

```text
job-application-manager/
├── src/
│   └── main/
│       └── java/
│           └── Main/
│               ├── JobManager.java
│               ├── JobViewerGUI.java
│               └── Database.java
├── .gitignore
├── pom.xml
└── README.md
```

## Voraussetzungen

- Java 21 oder neuer
- Maven

Die SQLite-JDBC-Abhängigkeit wird automatisch über Maven geladen.

## Starten

### In einer IDE

Das Projekt als Maven-Projekt importieren und `Main.JobManager` starten.

### Über Maven bauen

```bash
mvn clean package
```

Danach befindet sich die ausführbare JAR im Ordner `target/`.

```bash
java -jar target/jobmanager-1.0-SNAPSHOT.jar
```

## Datenbank

Beim ersten Start wird automatisch eine lokale Datei `advanced.db` erstellt. Sie wird durch `.gitignore` nicht in das GitHub-Repository aufgenommen.
