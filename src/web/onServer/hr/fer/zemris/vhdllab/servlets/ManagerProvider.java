package hr.fer.zemris.vhdllab.servlets;

/**
 * Provides infomation regarding which implementations will
 * be used.
 */
public interface ManagerProvider {
	
	/**
	 * Key for VHDLLabManager interface.
	 */
	public static final String VHDL_LAB_MANAGER = "vhdlLabManager";
	
	/**
	 * Returns an object represented as a <code>name</code>.
	 * @param name a name by which to find cirtain object. 
	 * @return an object represented as a <code>name</code>.
	 */
	public Object get(String name);
	
}