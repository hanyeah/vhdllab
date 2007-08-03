package hr.fer.zemris.vhdllab.applets.view.simulation;

import hr.fer.zemris.vhdllab.applets.main.interfaces.IView;
import hr.fer.zemris.vhdllab.applets.main.interfaces.ISystemContainer;
import hr.fer.zemris.vhdllab.vhdl.SimulationMessage;
import hr.fer.zemris.vhdllab.vhdl.SimulationResult;

import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/** 
 * Panel koji sadrzi mozebitne greske prilikom kompajliranja VHDL koda.
 *
 * @author Boris Ozegovic
 * @version 1.0
 * @since 22.12.2006.
 */
public class SimulateErrorsPanel extends JPanel implements IView {
	
    /**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = -7361269803493786758L;

	/** DefaultListModel */
    private DefaultListModel content;

    /** JList komponenta u koju ce se potrpati sve greske */
    private JList listContent;

    /** Panel sadrzi JScrollPane komponentu cime je omoguceno scrollanje */
    private JScrollPane scrollPane;
    
    /** SystemContainer */
    private ISystemContainer systemContainer;
    
    /**
     * Constructor 
     *
     * Kreira objekt i dovodi ga u pocetno stanje ciji kontekst sadrzi prazan
     * string
     */
    public SimulateErrorsPanel()
    {
        super();
        content = new DefaultListModel();
        listContent = new JList(content);
        // TODO ovo ucitat iz preference-a
        listContent.setFixedCellHeight(15);
        scrollPane = new JScrollPane(listContent);
        
        this.addComponentListener(new ComponentListener() {
			public void componentHidden(ComponentEvent e) {}
			public void componentMoved(ComponentEvent e) {}
			public void componentResized(ComponentEvent e) {
				scrollPane.setPreferredSize(SimulateErrorsPanel.this.getSize());
			}
			public void componentShown(ComponentEvent e) {}
		});
        
        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Glavna metoda koja uzima neki rezultat dobiven od strane servera.
     *
     * @param result rezultat koji ce ciniti kontekst panela s greskama
     */
    public void setContent(SimulationResult result)
    {
    	if(result.isSuccessful()) {
    		// TODO ovo ucitat iz bundle-a
    		Format formatter = new SimpleDateFormat("HH:mm:ss");
    		String time = formatter.format(new Date());
    		content.addElement(time + "  Simulation finished successfully.");
    		return;
    	}
    	
    	content.clear();
        for(SimulationMessage msg : result.getMessages()) {
        	StringBuilder sb = new StringBuilder(msg.getMessageText().length() + 20);
        	sb.append(msg.getMessageEntity()).append(":")
        		.append(msg.getMessageText());
            content.addElement(sb.toString());
        }
    }

	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.view.IView#appendData(java.lang.Object)
	 */
	public void appendData(Object data) {
		// TODO ovdje pozvat setContent
		setData(data);
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.view.IView#setData(java.lang.Object)
	 */
	public void setData(Object data) {
		if(!(data instanceof SimulationResult)) {
			throw new IllegalArgumentException("Unknown data!");
		}
//		content.clear();
		setContent((SimulationResult)data);
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.view.IView#setProjectContainer(hr.fer.zemris.vhdllab.applets.main.interfaces.ProjectContainer)
	 */
	public void setSystemContainer(ISystemContainer container) {
		this.systemContainer = container;
	}
 
}