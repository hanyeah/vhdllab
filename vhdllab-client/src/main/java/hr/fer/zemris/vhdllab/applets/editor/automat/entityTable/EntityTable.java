package hr.fer.zemris.vhdllab.applets.editor.automat.entityTable;

import hr.fer.zemris.vhdllab.api.util.StringFormat;
import hr.fer.zemris.vhdllab.api.vhdl.CircuitInterface;
import hr.fer.zemris.vhdllab.api.vhdl.Port;
import hr.fer.zemris.vhdllab.api.vhdl.PortDirection;
import hr.fer.zemris.vhdllab.api.vhdl.Range;
import hr.fer.zemris.vhdllab.api.vhdl.Type;
import hr.fer.zemris.vhdllab.api.vhdl.TypeName;
import hr.fer.zemris.vhdllab.api.vhdl.VectorDirection;
import hr.fer.zemris.vhdllab.entities.Caseless;
import hr.fer.zemris.vhdllab.entities.FileInfo;
import hr.fer.zemris.vhdllab.platform.manager.view.PlatformContainer;
import hr.fer.zemris.vhdllab.platform.manager.workspace.model.FileIdentifier;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

/*TODO ako se samo pokrene a ne stavi ni jedan ulaz!!!!
 * -napravi isDataCorrect KASNIJE
 */

public class EntityTable extends JPanel implements IEntityWizard {

    private JTable table = null;
    private MyTableModel model = null;
    /**
	 * 
	 */
    private static final long serialVersionUID = 7533182574459245416L;

    private JTextField imeSklop;
    private ResourceBundle bundle;
    private PlatformContainer container;

    /**
     * @param header
     * @param args
     */

    public EntityTable() {
        super();
    }

    public EntityTable(String label, String[] data, String lab2) {
        super();
    }

    private void createGUI() {
        imeSklop = new JTextField(bundle.getString("entityName"));
        imeSklop.addMouseListener(new Mouse());

        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(imeSklop);

        panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory
                .createTitledBorder(bundle.getString("label2")), BorderFactory
                .createEmptyBorder(5, 5, 5, 5)));

        // panel.setBorder(BorderFactory.createEmptyBorder(0,10,0,200));
        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.NORTH);

        Object[][] obj = { { "", bundle.getString("initColumn1"),
                bundle.getString("initColumn2"), "0", "0" } };
        JComboBox inComboBox = createInComboBox();
        JComboBox tipComboBox = createTipComboBox();
        JTextField imeSignala = new JTextField("");

        NumberBox brojevi = new NumberBox("0");

        String[] data1 = { bundle.getString("table.column0"),
                bundle.getString("table.column1"),
                bundle.getString("table.column2"),
                bundle.getString("table.column3"),
                bundle.getString("table.column4") };

        model = new MyTableModel(obj, data1);
        model.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent arg0) {
                if (!model.getValueAt(model.getRowCount() - 1, 0).equals(""))
                    model.addRow();
            }
        });
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.getColumnModel().getColumn(0).setCellEditor(
                new DefaultCellEditor(imeSignala));
        table.getColumnModel().getColumn(1).setCellEditor(
                new DefaultCellEditor(inComboBox));
        table.getColumnModel().getColumn(2).setCellEditor(
                new DefaultCellEditor(tipComboBox));
        table.getColumnModel().getColumn(3).setCellEditor(
                new DefaultCellEditor(brojevi));
        table.getColumnModel().getColumn(4).setCellEditor(
                new DefaultCellEditor(brojevi));

        JScrollPane pane = new JScrollPane(table);
        pane.setBorder(BorderFactory.createCompoundBorder(BorderFactory
                .createTitledBorder(bundle.getString("label1")), BorderFactory
                .createEmptyBorder(5, 5, 5, 5)));
        pane.setPreferredSize(new Dimension(100, 100));
        this.add(pane, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(500, 300));
    }

    private JComboBox createInComboBox() {
        JComboBox cb = new JComboBox();
        InputStream in = this.getClass().getResourceAsStream(
                "EntityTable-Direction.properties");
        Properties p = new Properties();
        try {
            p.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Object key : p.keySet())
            cb.addItem(p.get(key));
        return cb;
    }

    private JComboBox createTipComboBox() {
        JComboBox cb = new JComboBox();
        InputStream in = this.getClass().getResourceAsStream(
                "EntityTable-Type.properties");
        Properties p = new Properties();
        try {
            p.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Object key : p.keySet())
            cb.addItem(p.get(key));
        return cb;
    }

    public ReturnData getData() {
        return model.getData();
    }

    // ***********MyTableModel************

    private class MyTableModel extends AbstractTableModel {
        /**
		 * 
		 */
        private static final long serialVersionUID = -4781965036332460224L;
        /**
		 * 
		 */
        private String[] columnNames = new String[5];
        private Object[][] data = null;

        protected MyTableModel(Object[][] obj, String[] names) {
            super();
            data = obj;
            for (int i = 0; i < 5 && i < names.length; i++)
                columnNames[i] = names[i];
        }

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        /*
         * public Class getColumnClass(int c) { return getValueAt(0,
         * c).getClass(); }
         */

        @Override
        public boolean isCellEditable(int row, int col) {
            if (col > 2 && getValueAt(row, 2).equals("std_logic"))
                return false;
            return true;
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }

        public void addRow() {
            int brojRedova = getRowCount();
            Object[][] obj = new Object[brojRedova + 1][getColumnCount()];
            for (int i = 0; i < getRowCount(); i++)
                for (int j = 0; j < 5; j++)
                    obj[i][j] = data[i][j];
            obj[getRowCount()][0] = "";
            obj[getRowCount()][1] = bundle.getString("initColumn1");
            obj[getRowCount()][2] = bundle.getString("initColumn2");
            obj[getRowCount()][3] = "0";
            obj[getRowCount()][4] = "0";
            data = obj;
            fireTableDataChanged();
        };

        public ReturnData getData() {
            updateTable();
            String[][] pom = new String[getRowCount()][getColumnCount()];
            for (int i = 0; i < getRowCount(); i++)
                for (int j = 0; j < getColumnCount(); j++)
                    pom[i][j] = (String) data[i][j];
            ReturnData dat = new ReturnData(imeSklop.getText(), pom);
            return dat;
        }

        public void setData(Object[][] obj) {
            data = obj;
            addRow();
        }
    }

    public void updateTable() {
        table.editingStopped(new ChangeEvent(model));
    }

    private class Mouse implements MouseListener {

        public void mouseClicked(MouseEvent e) {
            updateTable();
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

    }

    public void setData(CircuitInterface data) {
        imeSklop.setText(data.getName());
        List<Port> lista = data.getPorts();
        Object[][] obj = new Object[lista.size()][model.getColumnCount()];
        int i = 0;
        for (Port p : lista) {
            obj[i][0] = p.getName();
            if (p.getDirection().equals(PortDirection.IN))
                obj[i][1] = "in";
            else
                obj[i][1] = "out";
            Type tip = p.getType();
            obj[i][2] = tip.getTypeName();
            if (tip.getRange().isVector()) {
                obj[i][3] = tip.getRange().getFrom();
                obj[i][4] = tip.getRange().getTo();
            } else {
                obj[i][3] = 0;
                obj[i][4] = 0;
            }
            i++;
        }
        model.setData(obj);
    }

    public CircuitInterface getCircuitInterface() {
        List<Port> lista = new ArrayList<Port>();
        ReturnData data = model.getData();
        String[][] pomData = data.getData();
        for (int i = 0; i < pomData.length; i++) {
            if (pomData[i][0].equals(""))
                continue;
            PortDirection d = null;
            if (pomData[i][1].toLowerCase().equals("in"))
                d = PortDirection.IN;
            else
                d = PortDirection.OUT;
            Type tip = null;
            if (pomData[i][2].equalsIgnoreCase("Std_Logic"))
                tip = new Type(TypeName.STD_LOGIC, Range.SCALAR);
            else {
                if (Integer.parseInt(pomData[i][3]) > Integer
                        .parseInt(pomData[i][4])) {
                    int[] inte = { Integer.parseInt(pomData[i][3]),
                            Integer.parseInt(pomData[i][4]) };
                    tip = new Type(TypeName.STD_LOGIC_VECTOR, new Range(
                            inte[0], VectorDirection.DOWNTO, inte[1]));
                } else {
                    int[] inte = { Integer.parseInt(pomData[i][3]),
                            Integer.parseInt(pomData[i][4]) };
                    tip = new Type(TypeName.STD_LOGIC_VECTOR, new Range(
                            inte[0], VectorDirection.TO, inte[1]));
                }
            }
            lista.add(new Port(pomData[i][0], d, tip));
        }
        CircuitInterface inter = new CircuitInterface(data.getName(), lista);
        return inter;
    }

    public void setPlatformContainer(PlatformContainer container) {
        this.container = container;
        bundle = ResourceBundle
                .getBundle("Client_EntityTable_ApplicationResources");
    }

    /**
     * Funkcija provjerava dali su uneseni podatci u tablici dobri.
     */
    public boolean isDataCorrect() {
        Set<String> test = new HashSet<String>();
        for (int i = 0; i < model.getRowCount() - 1; i++) {
            if (test.contains(model.getValueAt(i, 0))
                    || !StringFormat.isCorrectPortName((String) model
                            .getValueAt(i, 0)))
                return false;
            else
                test.add((String) model.getValueAt(i, 0));
        }
        FileInfo file = container.getMapper().getFile(
                new FileIdentifier(container.getViewManager()
                        .getProjectExplorer().getSelectedProject(),
                        new Caseless(imeSklop.getText())));
        if (file != null
                || !StringFormat.isCorrectFileName(imeSklop.getText()))
            return false;
        return true;
    }

    public void init() {
        createGUI();
    }
}
