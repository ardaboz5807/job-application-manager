# Job Application Manager
Konsolenbasierte Java-Anwendung zur Verwaltung von Bewerbungen

-----------------------------------------------

Die Anwendung ermöglicht es, Bewerbungen zu speichern, anzuzeigen, zu bearbeiten und zu löschen.  
Die Daten werden dauerhaft in einer lokalen SQLite-Datenbank gespeichert.

## Funktionen

- Bewerbungen hinzufügen
- Bewerbungen anzeigen
- Status einer Bewerbung aktualisieren
- Bewerbungen löschen
- Alle Bewerbungen löschen
- Speicherung der Daten in SQLite

## Verwendete Technologien

- Java
- SQLite

## Projektstruktur

- `JobViewer.java` – Startpunkt der Anwendung
- `Logical.java` – Programmlogik und Konsolenmenü
- `Database.java` – Kommunikation mit der SQLite-Datenbank

## Datenbank

Die Anwendung erstellt beim Start automatisch eine lokale SQLite-Datenbank, falls noch keine vorhanden ist.

Gespeichert werden unter anderem:

- Unternehmen
- Position
- Standort
- Bewerbungsstatus
- Bewerbungsdatum
- Datum der letzten Aktualisierung

## Aktueller Stand

Die Konsolenversion der Anwendung ist fertig (Sollte übersichtlich sein, hab mein bestes gegeben!)

Geplante Erweiterungen:

- Grafische Benutzeroberfläche mit Java Swing
- Such- und Filterfunktionen
- Verbesserte Eingabevalidierung
