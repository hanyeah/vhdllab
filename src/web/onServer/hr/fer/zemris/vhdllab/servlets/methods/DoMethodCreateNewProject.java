package hr.fer.zemris.vhdllab.servlets.methods;

import hr.fer.zemris.ajax.shared.JavaToAjaxRegisteredMethod;
import hr.fer.zemris.ajax.shared.MethodConstants;
import hr.fer.zemris.vhdllab.model.Project;
import hr.fer.zemris.vhdllab.service.ServiceException;
import hr.fer.zemris.vhdllab.service.VHDLLabManager;

import java.util.Properties;

/**
 * This class represents a registered method for "create project" request.
 * 
 * @author Miro Bezjak
 */
public class DoMethodCreateNewProject implements JavaToAjaxRegisteredMethod {

	/* (non-Javadoc)
	 * @see hr.fer.zemris.ajax.shared.JavaToAjaxRegisteredMethod#run(java.util.Properties, hr.fer.zemris.vhdllab.service.VHDLLabManager)
	 */
	public Properties run(Properties p, VHDLLabManager labman) {
		String projectName = p.getProperty(MethodConstants.PROP_PROJECT_NAME,null);
		String ownerId = p.getProperty(MethodConstants.PROP_PROJECT_OWNER_ID,null);
		if(projectName == null) return errorProperties(MethodConstants.SE_METHOD_ARGUMENT_ERROR,"No project name specified!");
		if(ownerId == null) return errorProperties(MethodConstants.SE_METHOD_ARGUMENT_ERROR,"No owner ID specified!");
		
		Long id = null;
		try {
			id = Long.parseLong(ownerId);
		} catch (NumberFormatException e) {
			return errorProperties(MethodConstants.SE_PARSE_ERROR,"Unable to parse owner ID!");
		}
		
		// Create new project
		Project project = null;
		try {
			project = labman.createNewProject(projectName, id);
		} catch (ServiceException e) {
			project = null;
		}
		if(project == null) return errorProperties(MethodConstants.SE_CAN_NOT_CREATE_PROJECT, "Unable to create a new project!");
		
		// Prepare response
		Properties resProp = new Properties();
		resProp.setProperty(MethodConstants.PROP_METHOD,MethodConstants.MTD_CREATE_NEW_PROJECT);
		resProp.setProperty(MethodConstants.PROP_STATUS,MethodConstants.STATUS_OK);
		resProp.setProperty(MethodConstants.PROP_PROJECT_ID,String.valueOf(project.getId().longValue()));
		return resProp;
	}
	
	/**
	 * This method is called if errors occur.
	 * @param errNo error message number
	 * @param errorMessage error message to pass back to caller
	 * @return a response Properties
	 */
	private Properties errorProperties(String errNo, String errorMessage) {
		Properties resProp = new Properties();
		resProp.setProperty(MethodConstants.PROP_STATUS,errNo);
		resProp.setProperty(MethodConstants.STATUS_CONTENT,errorMessage);
		return resProp;
	}
}