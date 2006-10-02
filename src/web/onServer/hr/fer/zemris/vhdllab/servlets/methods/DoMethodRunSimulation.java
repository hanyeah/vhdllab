package hr.fer.zemris.vhdllab.servlets.methods;

import hr.fer.zemris.ajax.shared.MethodConstants;
import hr.fer.zemris.vhdllab.service.ServiceException;
import hr.fer.zemris.vhdllab.service.VHDLLabManager;
import hr.fer.zemris.vhdllab.servlets.ManagerProvider;
import hr.fer.zemris.vhdllab.servlets.RegisteredMethod;
import hr.fer.zemris.vhdllab.vhdl.Message;
import hr.fer.zemris.vhdllab.vhdl.SimulationMessage;
import hr.fer.zemris.vhdllab.vhdl.SimulationResult;

import java.util.Properties;

/**
 * This class represents a registered method for "run simulation" request.
 * 
 * @author Miro Bezjak
 * @see MethodConstants#MTD_RUN_SIMULATION
 */
public class DoMethodRunSimulation implements RegisteredMethod {

	/* (non-Javadoc)
	 * @see hr.fer.zemris.vhdllab.servlets.RegisteredMethod#run(java.util.Properties, hr.fer.zemris.vhdllab.servlets.ManagerProvider)
	 */
	public Properties run(Properties p, ManagerProvider mprov) {
		VHDLLabManager labman = (VHDLLabManager)mprov.get("vhdlLabManager");
		String method = p.getProperty(MethodConstants.PROP_METHOD);
		String fileID = p.getProperty(MethodConstants.PROP_FILE_ID,null);
		if(fileID==null) return errorProperties(method,MethodConstants.SE_METHOD_ARGUMENT_ERROR,"No file ID specified!");

		// Get simulation result
		SimulationResult result = null;
		try {
			Long id = Long.parseLong(fileID);
			result = labman.runSimulation(id);
		} catch (NumberFormatException e) {
			return errorProperties(method,MethodConstants.SE_PARSE_ERROR,"Unable to parse file ID = '"+fileID+"'!");
		} catch (ServiceException e) {
			result = null;
		}
		if(result==null) return errorProperties(method,MethodConstants.SE_CAN_NOT_GET_SIMULATION_RESULT,"Unable to get simulation result!");
		
		// Prepare response
		Properties resProp = new Properties();
		resProp.setProperty(MethodConstants.PROP_METHOD,method);
		resProp.setProperty(MethodConstants.PROP_STATUS,MethodConstants.STATUS_OK);
		resProp.setProperty(MethodConstants.PROP_RESULT_STATUS,String.valueOf(result.getStatus()));
		resProp.setProperty(MethodConstants.PROP_RESULT_IS_SUCCESSFUL,String.valueOf(result.isSuccessful() ? 1 : 0));
		int i = 1;
		for(Message msg : result.getMessages()) {
			if(!(msg instanceof SimulationMessage)) {
				return errorProperties(method,MethodConstants.SE_TYPE_SAFETY, "Found non-simulation type message for simulation result!");
			}
			resProp.setProperty(MethodConstants.PROP_RESULT_MESSAGE_TEXT+"."+i, msg.getMessageText());
			i++;
		}
		return resProp;
	}
	
	/**
	 * This method is called if error occurs.
	 * @param method a method that caused this error
	 * @param errNo error message number
	 * @param errorMessage error message to pass back to caller
	 * @return a response Properties
	 */
	private Properties errorProperties(String method, String errNo, String errorMessage) {
		Properties resProp = new Properties();
		resProp.setProperty(MethodConstants.PROP_METHOD,method);
		resProp.setProperty(MethodConstants.PROP_STATUS,errNo);
		resProp.setProperty(MethodConstants.PROP_STATUS_CONTENT,errorMessage);
		return resProp;
	}
}