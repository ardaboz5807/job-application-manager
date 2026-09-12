package Main;

import java.util.ArrayList;
import java.util.Scanner;

public class Logical {

	Scanner scanner = new Scanner(System.in);
	Database database = new Database();  //Datenbank laden
	ArrayList<String> inputs = new ArrayList<>();

	public void add_application() {
		inputs.clear();
		try {
			System.out.println("\n--- Bewerbung hinzufügen ---");

			System.out.print("Unternehmen: ");
			String inp = scanner.nextLine();
			inputs.add(inp);

			System.out.print("Position: ");
			inp = scanner.nextLine();
			inputs.add(inp);

			System.out.print("Standort: ");
			inp = scanner.nextLine();
			inputs.add(inp);

			System.out.print("Status: ");
			inp = scanner.nextLine();
			inputs.add(inp);

			System.out.print("Antragsdatum: ");
			inp = scanner.nextLine();
			inputs.add(inp);
			inputs.add(inp);

			database.insertTable(inputs.get(0), inputs.get(1), inputs.get(2), inputs.get(3), inputs.get(4), inputs.get(5));
			System.out.println("\nBewerbung erfolgreich hinzugefügt!");
		}
		catch(Exception e) {
			System.out.println("Fehler beim Hinzufügen der Bewerbung.");
			e.printStackTrace();
		}
	}

	public void edit_application() {
		inputs.clear();
		try {
			System.out.println("\n--- Bewerbung bearbeiten ---");

			System.out.print("ID der Bewerbung: ");
			String inp = scanner.nextLine();
			inputs.add(inp);

			System.out.print("Neuer Status: ");
			inp = scanner.nextLine();
			inputs.add(inp);

			System.out.print("Datum der letzten Aktualisierung: ");
			inp = scanner.nextLine();
			inputs.add(inp);

			database.update(Integer.parseInt(inputs.get(0)), inputs.get(1), inputs.get(2));
			System.out.println("\nBewerbung erfolgreich aktualisiert!");
		}
		catch(Exception e) {
			System.out.println("Fehler bei der Bearbeitung.");
			e.printStackTrace();
		}
	}

	public void delete_application() {
		try {
			System.out.println("\n--- Bewerbung löschen ---");

			System.out.print("ID der Bewerbung: ");
			int inp = Integer.parseInt(scanner.nextLine());

			System.out.print("Unternehmen, zur Bestätigung: ");
			String tmp = scanner.nextLine();

			database.delete(inp, tmp);

			System.out.println("\nBewerbung erfolgreich gelöscht!");
		}
		catch(Exception e) {
			System.out.println("Fehler beim Löschen.");
		}
	}

	public void all_clear() {
		try {
			database.clearTable();
			System.out.println("\nAlle Bewerbungen wurden gelöscht.");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void start() {
		System.out.println("================================");
		System.out.println("     Bewerbungssystem");
		System.out.println("================================");
		System.out.println("1. Bewerbungen anzeigen");
		System.out.println("2. Bewerbung hinzufügen");
		System.out.println("3. Bewerbung bearbeiten");
		System.out.println("4. Bewerbung löschen");
		System.out.println("5. Bewerbungen löschen (alle)");
		System.out.println("6. Programm beenden");
		System.out.println("================================");
		while(true) {
			System.out.println("================================");
			System.out.print("\nAuswahl: ");
			switch(Integer.parseInt(scanner.nextLine())) {
				case 1:
					database.printTable();
					break;
				case 2:
					add_application();
					break;
				case 3:
					edit_application();
					break;
				case 4:
					delete_application();
					break;
				case 5:
					System.out.println("Bist du sicher? Schreibe ‚ok‘, um fortzufahren. Wenn nicht, dann schreibe irgendetwas, um abzubrechen");
					String confirm = scanner.nextLine();
					if(confirm.equals("ok")) {
						all_clear();
					}
					else {
						System.out.println("Prozess abgebrochen");
					}
					break;
				case 6:
					System.out.println("Programm wird beendet...");
					System.out.println("Beendet");
					return;
				default:
					System.out.println("Bitte eine Zahl zwischen 1 und 5 eingeben.");
					break;
			}

		}

	}

}