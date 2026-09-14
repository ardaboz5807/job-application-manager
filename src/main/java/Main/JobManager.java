package Main;

import java.util.ArrayList;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
public class JobManager {
    public static void main(String[] args) {
        JobViewerGUI gui = new JobViewerGUI(); 
        Database database = new Database();
        ArrayList<ArrayList<String>> inside = database.printTable();  //Alle Einträge im .db laden
        //Sortierer für die Tabelle erstellen und mit der JTable verbinden
        TableRowSorter<DefaultTableModel> sorter =new TableRowSorter<>(gui.getTableModel()); 
        gui.getApplicationTable().setRowSorter(sorter);
        //Tabelle beim starten füllen
        for(int i = 0; i<inside.size(); i++) {
        		gui.getTableModel().addRow(new Object[] {
        			Integer.parseInt(inside.get(i).get(0)),
        			inside.get(i).get(1),
        			inside.get(i).get(2),
        			inside.get(i).get(3),
        			inside.get(i).get(4),
        			inside.get(i).get(5),
        			inside.get(i).get(6)
        		});
        }
        //In Tabelle hinzufügen
        gui.getAddSaveButton().addActionListener(e -> {
            String company = gui.getAddCompany().getText();
            String position = gui.getAddPosition().getText();
            String location = gui.getAddLocation().getText();
            String status = gui.getAddStatus().getSelectedItem().toString();
            String applicationDate = gui.getAddApplicationDate().getText();
            String lastUpdate = gui.getAddLastUpdate().getText();
            database.insertTable(company, position, location, status, applicationDate, lastUpdate);
            //database.printTable();
            //Textfields leeren
            gui.getAddCompany().setText("");
            gui.getAddPosition().setText("");
            gui.getAddLocation().setText("");
            gui.getAddStatus().setSelectedIndex(0);
            gui.getAddApplicationDate().setText("");
            gui.getAddLastUpdate().setText("");
            gui.getTableModel().addRow(new Object[] {
                database.getId(company, location, position, applicationDate),                 // ID
                company,
                position,
                location,
                status,
                applicationDate,
                lastUpdate
            });
        });
        //Bearbeiten, den Eintrag
        gui.getEditSaveButton().addActionListener(e -> {
            int id = Integer.parseInt(gui.getEditId().getText());
            String status = gui.getEditStatus().getSelectedItem().toString();
            String lastUpdate = gui.getEditLastUpdate().getText();
            database.update(id, status, lastUpdate);
            for(int i = 0; i<gui.getTableModel().getRowCount(); i++) {
            		int tableID = Integer.parseInt(gui.getTableModel().getValueAt(i, 0).toString());
            		if(tableID == id) {
            			gui.getTableModel().setValueAt(status,i,4);
            			gui.getTableModel().setValueAt(lastUpdate, i, 6);
            			break;
            		}
            }
            gui.getEditId().setText("");
            gui.getEditStatus().setSelectedIndex(0);
            gui.getEditLastUpdate().setText("");
        });
        //Löschen
        gui.getDeleteButton().addActionListener(e -> {
            int id = Integer.parseInt(gui.getDeleteId().getText());
            String company = gui.getDeleteCompany().getText();
            database.delete(id, company);
            for(int i = 0; i<gui.getTableModel().getRowCount();i++) {
            		int tableID = Integer.parseInt(gui.getTableModel().getValueAt(i,0).toString());
            		String tableCompany = gui.getTableModel().getValueAt(i,1).toString();
            		if(tableID == id && tableCompany.equals(company)) {
            			gui.getTableModel().removeRow(i);
            			break;
            		}
            }
            gui.getDeleteId().setText("");
            gui.getDeleteCompany().setText("");
        });
        //Alles löschen
        gui.getClearAllButton().addActionListener(e -> {
            database.clearTable();
            gui.getTableModel().setRowCount(0);
            //database.printTable();
        });
        gui.setVisible(true);
        //Der Filter (Hauptseite)
        gui.getStatusFilter().addActionListener(e -> {
            String status = gui.getStatusFilter().getSelectedItem().toString();
            if (status.equals("Alle Status")) {
                sorter.setRowFilter(null);
            } 
            //Zeigt nur Zeilen deren Status in Spalte 4 exakt übereinstimmt
            else {
                sorter.setRowFilter(
                        RowFilter.regexFilter("^" + status + "$", 4)
                );
            }
        });
    }
}
