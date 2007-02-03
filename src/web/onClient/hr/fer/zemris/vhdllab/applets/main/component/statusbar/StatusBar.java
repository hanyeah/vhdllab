package hr.fer.zemris.vhdllab.applets.main.component.statusbar;

import hr.fer.zemris.vhdllab.applets.main.event.EventListenerList;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 * 
 * @author Miro Bezjak
 */
public class StatusBar extends JPanel implements IStatusBar {

	/** Serial Version UID */
	private static final long serialVersionUID = 9102232350033953560L;

	private JLabel statusText;
	private JLabel timeLabel;
	
	/** Listeners to notify when status text changes */
	private EventListenerList<StatusListener> statusListenerList;

	private MessageEnum messageType;

	private static final String TIME = " HH:mm ";

	public StatusBar() {
		statusText = new JLabel();
		timeLabel = new JLabel(TIME);
		statusListenerList = new EventListenerList<StatusListener>();

		Box box = Box.createHorizontalBox();
		box.add(new JSeparator(SwingConstants.VERTICAL));
		box.add(timeLabel);

		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
		this.add(new JLabel(" "), BorderLayout.WEST);
		this.add(statusText, BorderLayout.CENTER);
		this.add(box, BorderLayout.EAST);
		this.setPreferredSize(new Dimension(0, 24));

		final DateFormat df = new SimpleDateFormat(TIME);
		timeLabel.setText(df.format(new Date()));
		Timer timer = new Timer(1000*5,new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				timeLabel.setText(df.format(new Date()));
			}
		});
		timer.start();
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.main.component.statusbar.IStatusBar#setMessage(java.lang.String)
	 */
	public void setMessage(String message) {
		setMessage(message, MessageEnum.Information);
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.main.component.statusbar.IStatusBar#setMessage(java.lang.String, hr.fer.zemris.vhdllab.applets.main.component.statusbar.MessageEnum)
	 */
	public void setMessage(String text, MessageEnum type) {
		if(text == null) {
			text = "";
		}
		if(type == null) {
			type = MessageEnum.Information;
		}
		statusText.setText(text);
		this.messageType = type;
		fireStatusChanged();
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.main.component.statusbar.IStatusBar#getMessage()
	 */
	public String getMessage() {
		return statusText.getText();
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.main.component.statusbar.IStatusBar#getMessageType()
	 */
	public MessageEnum getMessageType() {
		return messageType;
	}

	public void addComponent(Component c, int x) {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.main.interfaces.IStatusBar#addStatusListener(hr.fer.zemris.vhdllab.applets.main.component.statusbar.StatusListener)
	 */
	public void addStatusListener(StatusListener l) {
		statusListenerList.add(l);
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.main.component.statusbar.IStatusBar#getStatusListeners()
	 */
	public List<StatusListener> getStatusListeners() {
		return statusListenerList.getListeners();
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.applets.main.interfaces.IStatusBar#removeStatusListener(hr.fer.zemris.vhdllab.applets.main.component.statusbar.StatusListener)
	 */
	public void removeStatusListener(StatusListener l) {
		statusListenerList.remove(l);
	}
	
	/**
     * Notifies all listeners that have registered interest for
     * notification on status text changes.
     * @see EventListenerList
	 */
	protected void fireStatusChanged() {
		StatusContent c = new StatusContent(statusText.getText(), messageType);
		for(StatusListener l : statusListenerList.getListeners()) {
			l.statusChanged(c);
		}
	}

}