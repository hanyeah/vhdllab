package hr.fer.zemris.vhdllab.applets.editor.schema2.predefined.beans;

import java.util.HashSet;
import java.util.Set;

public class PredefinedComponent {

	private String componentName;
	private String codeFileName;
	private String drawerName;
	private String categoryName;
	private Boolean genericComponent;
	private Set<Parameter> parameters;
	private Set<Port> ports;

	public PredefinedComponent() {
		parameters = new HashSet<Parameter>();
		ports = new HashSet<Port>();
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String parameterName) {
		if (parameterName.trim().equals("")) {
			parameterName = null;
		}
		this.componentName = parameterName;
	}

	public String getCodeFileName() {
		return codeFileName;
	}

	public void setCodeFileName(String codeFileName) {
		if (codeFileName.trim().equals("")) {
			codeFileName = null;
		}
		this.codeFileName = codeFileName;
	}

	public String getDrawerName() {
		return drawerName;
	}

	public void setDrawerName(String drawerName) {
		if (drawerName.trim().equals("")) {
			drawerName = null;
		}
		this.drawerName = drawerName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		if (categoryName.trim().equals("")) {
			categoryName = null;
		}
		this.categoryName = categoryName;
	}

	public Boolean isGenericComponent() {
		return genericComponent;
	}

	public void setGenericComponent(Boolean genericComponent) {
		this.genericComponent = genericComponent;
	}

	public void addParameter(Parameter p) {
		parameters.add(p);
	}

	public Set<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(Set<Parameter> parameters) {
		this.parameters = parameters;
	}

	public void addPort(Port p) {
		ports.add(p);
	}

	public Set<Port> getPorts() {
		return ports;
	}

	public void setPorts(Set<Port> ports) {
		this.ports = ports;
	}

	@Override
	public String toString() {
		return "Predefined Component {\n" + componentName + "\n" + codeFileName
				+ "\n" + drawerName + "\n" + categoryName + "\n"
				+ genericComponent + "\n" + parameters + "\n" + ports + "\n}";
	}

}