package hr.fer.zemris.vhdllab.applets.schema2.model;

import hr.fer.zemris.vhdllab.applets.editor.schema2.predefined.beans.ComponentWrapper;
import hr.fer.zemris.vhdllab.applets.editor.schema2.predefined.beans.ParameterWrapper;
import hr.fer.zemris.vhdllab.applets.editor.schema2.predefined.beans.PortWrapper;
import hr.fer.zemris.vhdllab.applets.schema2.exceptions.DuplicateKeyException;
import hr.fer.zemris.vhdllab.applets.schema2.exceptions.NotImplementedException;
import hr.fer.zemris.vhdllab.applets.schema2.exceptions.OverlapException;
import hr.fer.zemris.vhdllab.applets.schema2.interfaces.IParameterCollection;
import hr.fer.zemris.vhdllab.applets.schema2.interfaces.ISchemaComponent;
import hr.fer.zemris.vhdllab.applets.schema2.interfaces.ISchemaComponentCollection;
import hr.fer.zemris.vhdllab.applets.schema2.interfaces.ISchemaEntity;
import hr.fer.zemris.vhdllab.applets.schema2.interfaces.ISchemaInfo;
import hr.fer.zemris.vhdllab.applets.schema2.interfaces.ISchemaPrototypeCollection;
import hr.fer.zemris.vhdllab.applets.schema2.interfaces.ISchemaWire;
import hr.fer.zemris.vhdllab.applets.schema2.interfaces.ISchemaWireCollection;
import hr.fer.zemris.vhdllab.applets.schema2.misc.Caseless;
import hr.fer.zemris.vhdllab.applets.schema2.model.parameters.ParameterFactory;
import hr.fer.zemris.vhdllab.applets.schema2.model.serialization.EntityWrapper;
import hr.fer.zemris.vhdllab.applets.schema2.model.serialization.PortFactory;
import hr.fer.zemris.vhdllab.vhdl.model.Port;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;



public class SchemaInfo implements ISchemaInfo {
	
	private ISchemaWireCollection wires;
	private ISchemaComponentCollection components;
	private ISchemaEntity entity;
	private ISchemaPrototypeCollection prototypes;
	
	

	public SchemaInfo() {
		wires = new SimpleSchemaWireCollection();
		components = new SimpleSchemaComponentCollection();
		entity = new SchemaEntity(new Caseless(""));
		prototypes = new ComponentPrototyper();
	}
	
	

	public ISchemaComponentCollection getComponents() {
		return components;
	}

	public ISchemaEntity getEntity() {
		return entity;
	}

	public Map<Caseless, ISchemaComponent> getPrototypes() {
		return prototypes.getPrototypes();
	}

	public ISchemaWireCollection getWires() {
		return wires;
	}

	public void setComponents(ISchemaComponentCollection components) {
		this.components = components;
	}

	public void setWires(ISchemaWireCollection wires) {
		this.wires = wires;
	}
	
	public void setEntity(ISchemaEntity schemaEntity) {
		entity = schemaEntity;
	}

	public ISchemaPrototypeCollection getPrototyper() {
		return prototypes;
	}
	
	public void addWire(ISchemaWire wire) {
		try {
			wires.addWire(wire);
		} catch (DuplicateKeyException e) {
			throw new IllegalStateException();
		} catch (OverlapException e) {
			throw new IllegalStateException();
		}
	}
	
	public void addComponent(ComponentWrapper compwrap) {
		try {
			Class cls = Class.forName(compwrap.getComponentClassName());
			Class[] partypes = new Class[1];
			partypes[0] = ComponentWrapper.class;
			Constructor<ISchemaComponent> ct = cls.getConstructor(partypes);
			Object[] params = new Object[1];
			params[0] = compwrap;
			ISchemaComponent cmp = ct.newInstance(params);
			components.addComponent(compwrap.getX(), compwrap.getY(), cmp);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException("Could not create component.", e);
		}
	}
	
	public void setEntityFromWrapper(EntityWrapper entwrap) {
		SchemaEntity ent = new SchemaEntity();
		
		// fill with parameters
		IParameterCollection params = ent.getParameters();
		for (ParameterWrapper paramwrap : entwrap.getParameters()) {
			params.addParameter(ParameterFactory.createParameter(paramwrap));
		}
		
		// fill with ports
		List<Port> ports = ent.getPorts(this);
		for (PortWrapper portwrap : entwrap.getPortwrappers()) {
			ports.add(PortFactory.createPort(portwrap));
		}
		
		entity = ent;
	}

	public Caseless getFreeIdentifier() {
		throw new NotImplementedException();
	}

	public Caseless getFreeIdentifier(Caseless offered) {
		throw new NotImplementedException();
	}

	public boolean isFreeIdentifier(Caseless identifier) {
		throw new NotImplementedException();
	}
	
	

}






















