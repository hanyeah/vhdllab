package hr.fer.zemris.vhdllab.api.results;

/**
 * Represents a simulation message.
 * <p>
 * This class is immutable and therefor thread-safe.
 * </p>
 * 
 * @author Miro Bezjak
 * @version 1.0
 * @since vhdllab2
 */
public final class SimulationMessage extends Message {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a simulation message out of specified parameters. Entity name
	 * will be set to <code>null</code>.
	 * 
	 * @param type
	 *            a type of a message
	 * @param messageText
	 *            a text of a message
	 * @throws NullPointerException
	 *             if any parameter is <code>null</code>
	 */
	public SimulationMessage(MessageType type, String messageText) {
		this(type, messageText, null);
	}

	/**
	 * Creates a simulation message out of specified parameters.
	 * 
	 * @param type
	 *            a type of a message
	 * @param messageText
	 *            a text of a message
	 * @param entityName
	 *            an entity name of a unit for which message is generated.
	 *            Please note that this can be an empty string, if appropriate
	 *            information could not be determined
	 * @throws NullPointerException
	 *             if <code>type</code> or <code>messageText</code> is
	 *             <code>null</code>
	 */
	public SimulationMessage(MessageType type, String messageText,
			String entityName) {
		super(type, messageText, entityName);
	}
	
    /**
     * Copy constructor.
     * 
     * @param message
     *            a compilation message to copy
     */
    protected SimulationMessage(SimulationMessage message) {
        super(message);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        /*
         * Extended only for type checking!
         */
        if (!(obj instanceof SimulationMessage))
            return false;
        return super.equals(obj);
    }

    /**
     * Make a defensive copy.
     */
    @Override
    protected Object readResolve() {
        return new SimulationMessage(this);
    }

}