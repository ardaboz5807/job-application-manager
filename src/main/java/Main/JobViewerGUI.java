package Main;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;
public class JobViewerGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final Color BG = new Color(244, 247, 251);
    private static final Color SIDEBAR = new Color(20, 27, 45);
    private static final Color SIDEBAR_HOVER = new Color(35, 46, 72);
    private static final Color ACCENT = new Color(79, 70, 229);
    private static final Color CARD = Color.WHITE;
    private static final Color TEXT = new Color(31, 41, 55);
    private static final Color MUTED = new Color(107, 114, 128);
    private static final Color BORDER = new Color(226, 232, 240);
    private static final Color DANGER = new Color(220, 38, 38);
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel contentCards = new JPanel(cardLayout);
    // Komponenten koennen spaeter aus JobViewer/Controller angesprochen werden.
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"ID", "Unternehmen", "Position", "Standort", "Status", "Bewerbungsdatum", "Letztes Update"}, 0) {
        private static final long serialVersionUID = 1L;
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable applicationTable = new JTable(tableModel);
    private final JComboBox<String> statusFilter = new JComboBox<>(new String[]{
            "Alle Status", "Offen", "Beworben", "Interview", "Zusage", "Absage", "Wartend"
    });
    //Hinzufügen
    private final JTextField addCompany = createTextField();
    private final JTextField addPosition = createTextField();
    private final JTextField addLocation = createTextField();
    private final JComboBox<String> addStatus = createStatusCombo();
    private final JTextField addApplicationDate = createTextField();
    private final JTextField addLastUpdate = createTextField();
    private final JButton addSaveButton = primaryButton("Bewerbung speichern");
    //Bearbeiten
    private final JTextField editId = createTextField();
    private final JComboBox<String> editStatus = createStatusCombo();
    private final JTextField editLastUpdate = createTextField();
    private final JButton editSaveButton = primaryButton("Änderungen speichern");
    //Löschen
    private final JTextField deleteId = createTextField();
    private final JTextField deleteCompany = createTextField();
    private final JButton deleteButton = dangerButton("Bewerbung löschen");
    private final JButton clearAllButton = dangerButton("Löschen");
    public JobViewerGUI() {
        super("JobViewer - Bewerbungssystem");
        configureFrame();
        buildUI();
    }
    private void configureFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 680));
        setSize(1320, 800);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG);
    }
    private void buildUI() {
        setLayout(new BorderLayout());
        add(buildSidebar(), BorderLayout.WEST);
        contentCards.setBackground(BG);
        contentCards.add(buildDashboardPanel(), "dashboard");
        contentCards.add(buildAddPanel(), "add");
        contentCards.add(buildEditPanel(), "edit");
        contentCards.add(buildDeletePanel(), "delete");
        add(contentCards, BorderLayout.CENTER);
        cardLayout.show(contentCards, "dashboard");
    }
    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setBackground(SIDEBAR);
        sidebar.setPreferredSize(new Dimension(245, 0));
        sidebar.setBorder(new EmptyBorder(24, 18, 22, 18));
        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        JLabel brand = new JLabel("JOBVIEWER");
        brand.setForeground(Color.WHITE);
        brand.setFont(new Font("SansSerif", Font.BOLD, 24));
        brand.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel subtitle = new JLabel("Bewerbungen verwalten");
        subtitle.setForeground(new Color(156, 163, 175));
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        top.add(brand);
        top.add(Box.createVerticalStrut(4));
        top.add(subtitle);
        top.add(Box.createVerticalStrut(36));
        NavButton dashboard = new NavButton("Übersicht");
        NavButton add = new NavButton("Bewerbung hinzufügen");
        NavButton edit = new NavButton("Bewerbung bearbeiten");
        NavButton delete = new NavButton("Bewerbung löschen");
        List<NavButton> navButtons = List.of(dashboard, add, edit, delete);
        dashboard.setSelectedState(true);
        dashboard.addActionListener(e -> switchCard("dashboard", dashboard, navButtons));
        add.addActionListener(e -> switchCard("add", add, navButtons));
        edit.addActionListener(e -> switchCard("edit", edit, navButtons));
        delete.addActionListener(e -> switchCard("delete", delete, navButtons));
        for (NavButton button : navButtons) {
            button.setAlignmentX(Component.LEFT_ALIGNMENT);
            top.add(button);
            top.add(Box.createVerticalStrut(9));
        }
        sidebar.add(top, BorderLayout.NORTH);
        return sidebar;
    }
    private void switchCard(String cardName, NavButton selected, List<NavButton> buttons) {
        for (NavButton button : buttons) {
            button.setSelectedState(button == selected);
        }
        cardLayout.show(contentCards, cardName);
    }
    private JPanel buildDashboardPanel() {
        JPanel page = createPagePanel();
        page.add(pageHeader("Übersicht", "Alle Bewerbungen auf einen Blick"), BorderLayout.NORTH);
        JPanel body = new JPanel(new BorderLayout(0, 18));
        body.setOpaque(false);
        JPanel tableCard = new RoundedPanel(18, CARD);
        tableCard.setLayout(new BorderLayout(0, 14));
        tableCard.setBorder(new EmptyBorder(18, 18, 18, 18));
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        controls.setOpaque(false);
        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        statusLabel.setForeground(TEXT);
        statusFilter.setFont(new Font("SansSerif", Font.PLAIN, 13));
        statusFilter.setPreferredSize(new Dimension(160, 38));
        controls.add(statusLabel);
        controls.add(statusFilter);
        styleTable();
        JScrollPane scrollPane = new JScrollPane(applicationTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER));
        scrollPane.getViewport().setBackground(Color.WHITE);
        tableCard.add(controls, BorderLayout.NORTH);
        tableCard.add(scrollPane, BorderLayout.CENTER);
        body.add(tableCard, BorderLayout.CENTER);
        page.add(body, BorderLayout.CENTER);
        return page;
    }
    private JPanel buildAddPanel() {
        JPanel page = createPagePanel();
        page.add(pageHeader("Bewerbung hinzufügen", "Neue Bewerbung erfassen"), BorderLayout.NORTH);
        JPanel formCard = formCard();
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = baseGbc();
        addFormRow(form, gbc, 0, "Unternehmen", addCompany);
        addFormRow(form, gbc, 1, "Position", addPosition);
        addFormRow(form, gbc, 2, "Standort", addLocation);
        addFormRow(form, gbc, 3, "Status", addStatus);
        addFormRow(form, gbc, 4, "Bewerbungsdatum", addApplicationDate);
        addFormRow(form, gbc, 5, "Letztes Update", addLastUpdate);
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(18, 12, 0, 0);
        addSaveButton.setPreferredSize(new Dimension(190, 40));
        form.add(addSaveButton, gbc);
        formCard.add(form, BorderLayout.NORTH);
        page.add(formCard, BorderLayout.CENTER);
        return page;
    }
    private JPanel buildEditPanel() {
        JPanel page = createPagePanel();
        page.add(pageHeader("Bewerbung bearbeiten", "Status und letztes Update ändern"), BorderLayout.NORTH);
        JPanel formCard = formCard();
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = baseGbc();
        addFormRow(form, gbc, 0, "Bewerbungs-ID", editId);
        addFormRow(form, gbc, 1, "Neuer Status", editStatus);
        addFormRow(form, gbc, 2, "Letztes Update", editLastUpdate);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(18, 12, 0, 0);
        editSaveButton.setPreferredSize(new Dimension(190, 40));
        form.add(editSaveButton, gbc);
        formCard.add(form, BorderLayout.NORTH);
        page.add(formCard, BorderLayout.CENTER);
        return page;
    }
    private JPanel buildDeletePanel() {
        JPanel page = createPagePanel();
        page.add(pageHeader("Bewerbung löschen", "Bewerbung über ID und Unternehmen entfernen"), BorderLayout.NORTH);
        JPanel wrapper = new JPanel();
        wrapper.setOpaque(false);
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        JPanel formCard = formCard();
        formCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 260));
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = baseGbc();
        addFormRow(form, gbc, 0, "Bewerbungs-ID", deleteId);
        addFormRow(form, gbc, 1, "Unternehmen", deleteCompany);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(18, 12, 0, 0);
        deleteButton.setPreferredSize(new Dimension(190, 40));
        form.add(deleteButton, gbc);
        formCard.add(form, BorderLayout.NORTH);
        wrapper.add(formCard);
        wrapper.add(Box.createVerticalStrut(18));
        JPanel dangerZone = new RoundedPanel(18, new Color(254, 242, 242));
        dangerZone.setLayout(new BorderLayout(20, 0));
        dangerZone.setBorder(new EmptyBorder(20, 22, 20, 22));
        dangerZone.setMaximumSize(new Dimension(Integer.MAX_VALUE, 95));
        JPanel dangerText = new JPanel();
        dangerText.setOpaque(false);
        dangerText.setLayout(new BoxLayout(dangerText, BoxLayout.Y_AXIS));
        JLabel title = new JLabel("Gefahrenbereich");
        title.setFont(new Font("SansSerif", Font.BOLD, 15));
        title.setForeground(DANGER);
        JLabel description = new JLabel("Alle gespeicherten Bewerbungen löschen.");
        description.setFont(new Font("SansSerif", Font.PLAIN, 12));
        description.setForeground(MUTED);
        dangerText.add(title);
        dangerText.add(Box.createVerticalStrut(4));
        dangerText.add(description);
        clearAllButton.setPreferredSize(new Dimension(190, 40));
        dangerZone.add(dangerText, BorderLayout.CENTER);
        dangerZone.add(clearAllButton, BorderLayout.EAST);
        wrapper.add(dangerZone);
        page.add(wrapper, BorderLayout.CENTER);
        return page;
    }
    private JPanel createPagePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 22));
        panel.setBackground(BG);
        panel.setBorder(new EmptyBorder(30, 34, 34, 34));
        return panel;
    }
    private JPanel pageHeader(String titleText, String subtitleText) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel title = new JLabel(titleText);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(TEXT);
        JLabel subtitle = new JLabel(subtitleText);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(MUTED);
        panel.add(title);
        panel.add(Box.createVerticalStrut(5));
        panel.add(subtitle);
        return panel;
    }
    private JPanel formCard() {
        JPanel panel = new RoundedPanel(18, CARD);
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(28, 30, 28, 30));
        return panel;
    }
    private GridBagConstraints baseGbc() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 15, 0);
        return gbc;
    }
    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComponent component) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
        label.setForeground(TEXT);
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.insets = new Insets(0, 0, 15, 22);
        panel.add(label, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.insets = new Insets(0, 0, 15, 0);
        component.setPreferredSize(new Dimension(420, 40));
        panel.add(component, gbc);
    }
    private void styleTable() {
        applicationTable.setRowHeight(38);
        applicationTable.setShowVerticalLines(false);
        applicationTable.setShowHorizontalLines(true);
        applicationTable.setGridColor(BORDER);
        applicationTable.setSelectionBackground(new Color(238, 242, 255));
        applicationTable.setSelectionForeground(TEXT);
        applicationTable.setFont(new Font("SansSerif", Font.PLAIN, 12));
        applicationTable.setFillsViewportHeight(true);
        JTableHeader header = applicationTable.getTableHeader();
        header.setPreferredSize(new Dimension(0, 40));
        header.setFont(new Font("SansSerif", Font.BOLD, 12));
        header.setBackground(new Color(248, 250, 252));
        header.setForeground(TEXT);
        header.setReorderingAllowed(false);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setBorder(new EmptyBorder(0, 8, 0, 8));
        for (int i = 0; i < applicationTable.getColumnCount(); i++) {
            applicationTable.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }
    private static JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("SansSerif", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(8, 11, 8, 11)
        ));
        return field;
    }
    private static JComboBox<String> createStatusCombo() {
        JComboBox<String> combo = new JComboBox<>(new String[]{
                "Offen", "Beworben", "Interview", "Zusage", "Absage", "Wartend"
        });
        combo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        return combo;
    }
    private static JButton primaryButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(ACCENT);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setBorder(new EmptyBorder(10, 16, 10, 16));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }
    private static JButton dangerButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(DANGER);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setBorder(new EmptyBorder(10, 16, 10, 16));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }
    // ------------------------------------------------------------
    // Nur Getter: damit du die GUI spaeter mit deinem Backend verbinden kannst.
    // Hier wird selbst KEINE Backend-Logik ausgefuehrt.
    // ------------------------------------------------------------
    public JTable getApplicationTable() { return applicationTable; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JComboBox<String> getStatusFilter() { return statusFilter; }
    public JTextField getAddCompany() { return addCompany; }
    public JTextField getAddPosition() { return addPosition; }
    public JTextField getAddLocation() { return addLocation; }
    public JComboBox<String> getAddStatus() { return addStatus; }
    public JTextField getAddApplicationDate() { return addApplicationDate; }
    public JTextField getAddLastUpdate() { return addLastUpdate; }
    public JButton getAddSaveButton() { return addSaveButton; }
    public JTextField getEditId() { return editId; }
    public JComboBox<String> getEditStatus() { return editStatus; }
    public JTextField getEditLastUpdate() { return editLastUpdate; }
    public JButton getEditSaveButton() { return editSaveButton; }
    public JTextField getDeleteId() { return deleteId; }
    public JTextField getDeleteCompany() { return deleteCompany; }
    public JButton getDeleteButton() { return deleteButton; }
    public JButton getClearAllButton() { return clearAllButton; }
    private static class NavButton extends JButton {
        private static final long serialVersionUID = 1L;
        private boolean selectedState;
        NavButton(String text) {
            super(text);
            setHorizontalAlignment(SwingConstants.LEFT);
            setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
            setPreferredSize(new Dimension(205, 44));
            setFont(new Font("SansSerif", Font.PLAIN, 13));
            setForeground(new Color(209, 213, 219));
            setBackground(SIDEBAR);
            setBorder(new EmptyBorder(0, 14, 0, 14));
            setFocusPainted(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setOpaque(true);
            setContentAreaFilled(true);
            addChangeListener(e -> updateLook());
        }
        void setSelectedState(boolean selected) {
            this.selectedState = selected;
            updateLook();
        }
        private void updateLook() {
            if (selectedState) {
                setBackground(ACCENT);
                setForeground(Color.WHITE);
            } else if (getModel().isRollover()) {
                setBackground(SIDEBAR_HOVER);
                setForeground(Color.WHITE);
            } else {
                setBackground(SIDEBAR);
                setForeground(new Color(209, 213, 219));
            }
        }
    }
    private static class RoundedPanel extends JPanel {
        private static final long serialVersionUID = 1L;
        private final int radius;
        private final Color fill;
        RoundedPanel(int radius, Color fill) {
            this.radius = radius;
            this.fill = fill;
            setOpaque(false);
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(fill);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }
}
