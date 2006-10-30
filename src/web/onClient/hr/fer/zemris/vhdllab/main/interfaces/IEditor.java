package hr.fer.zemris.vhdllab.main.interfaces;

/**
 * Interface that describes communication between MainApplet and instance that implemets this interface.
 */
public interface IEditor {
	/**
	 * Sets a FileContent that mostly represents internal format that is used in Editor.
	 * Note that {@linkplain hr.fer.zemris.vhdllab.model.File#FT_VHDLSOURCE} is also an
	 * "internal" format. This method is also used to initialize the component that uses internal
	 * format.
	 * @param fContent FileContent 
	 */
	void setFileContent(FileContent fContent);
	/**
	 * Returns an internal format.
	 * @return an internal format.
	 */
	String getData();
	boolean isModified();
	void setProjectContainer(ProjectContainter pContainer);
	IWizard getWizard();
}