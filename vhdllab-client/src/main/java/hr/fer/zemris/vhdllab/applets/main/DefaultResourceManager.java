package hr.fer.zemris.vhdllab.applets.main;

import hr.fer.zemris.vhdllab.api.hierarchy.Hierarchy;
import hr.fer.zemris.vhdllab.applets.main.event.ResourceVetoException;
import hr.fer.zemris.vhdllab.applets.main.event.VetoableResourceListener;
import hr.fer.zemris.vhdllab.applets.main.interfaces.IResourceManager;
import hr.fer.zemris.vhdllab.entities.Caseless;
import hr.fer.zemris.vhdllab.entities.FileInfo;
import hr.fer.zemris.vhdllab.entities.FileType;
import hr.fer.zemris.vhdllab.platform.manager.workspace.IdentifierToInfoObjectMapper;
import hr.fer.zemris.vhdllab.platform.manager.workspace.model.FileIdentifier;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.EventListenerList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This is a default implementation of {@link IResourceManager} interface. It
 * uses {@link Communicator} as a resources provider.
 * 
 * @author Miro Bezjak
 */
@Component
public class DefaultResourceManager implements IResourceManager {

    /**
     * A resource provider.
     */
    @Autowired
    private ICommunicator communicator;
    @Autowired
    private IdentifierToInfoObjectMapper mapper;

    /**
     * Vetoable resource listeners.
     */
    private EventListenerList listeners;

    /**
     * Constructs a default resource menagement using <code>communicator</code>
     * as a resource provider.
     * 
     * @throws NullPointerException
     *             if <code>communicator</code> is <code>null</code>
     */
    public DefaultResourceManager() {
        super();
        this.listeners = new EventListenerList();
    }

    /* LISTENERS METHODS */

    /*
     * (non-Javadoc)
     * 
     * @seehr.fer.zemris.vhdllab.applets.main.interfaces.IResourceManager#
     * addVetoableResourceListener
     * (hr.fer.zemris.vhdllab.applets.main.event.VetoableResourceListener)
     */
    @Override
    public void addVetoableResourceListener(VetoableResourceListener l) {
        listeners.add(VetoableResourceListener.class, l);
    }

    /*
     * (non-Javadoc)
     * 
     * @seehr.fer.zemris.vhdllab.applets.main.interfaces.IResourceManager#
     * removeVetoableResourceListener
     * (hr.fer.zemris.vhdllab.applets.main.event.VetoableResourceListener)
     */
    @Override
    public void removeVetoableResourceListener(VetoableResourceListener l) {
        listeners.remove(VetoableResourceListener.class, l);
    }

    public VetoableResourceListener[] getVetoableResourceListeners() {
        return listeners.getListeners(VetoableResourceListener.class);
    }

    /* RESOURCE MANIPULATION METHODS */

    /*
     * (non-Javadoc)
     * 
     * @seehr.fer.zemris.vhdllab.applets.main.interfaces.IResourceManager#
     * createNewResource(java.lang.String, java.lang.String, java.lang.String,
     * java.lang.String)
     */
    @Override
    public boolean createNewResource(Caseless projectName, Caseless fileName,
            FileType type, String data) throws UniformAppletException {
        if (projectName == null) {
            throw new NullPointerException("Project name cant be null");
        }
        if (fileName == null) {
            throw new NullPointerException("File name cant be null");
        }
        if (type == null) {
            throw new NullPointerException("File type cant be null");
        }
        if (data == null) {
            throw new NullPointerException("File data cant be null");
        }
        try {
            fireBeforeResourceCreation(projectName, fileName, type);
        } catch (ResourceVetoException e) {
            return false;
        }
        communicator.createFile(projectName, fileName, type, data);
        try {
            fireResourceCreated(projectName, fileName, type);
        } catch (ResourceVetoException e) {
            // rollback
            deleteFile(projectName, fileName);
            // FIXME should this return false?
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * hr.fer.zemris.vhdllab.applets.main.interfaces.IResourceManager#deleteFile
     * (java.lang.String, java.lang.String)
     */
    @Override
    public void deleteFile(Caseless projectName, Caseless fileName)
            throws UniformAppletException {
        if (projectName == null) {
            throw new NullPointerException("Project name can not be null.");
        }
        if (fileName == null) {
            throw new NullPointerException("File name can not be null.");
        }
        if (isPredefined(projectName, fileName)) {
            return;
        }
        FileInfo file = mapper.getFile(new FileIdentifier(projectName, fileName));
        communicator.deleteFile(projectName, fileName);
        fireResourceDeleted(projectName, file);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * hr.fer.zemris.vhdllab.applets.main.interfaces.IResourceManager#deleteProject
     * (java.lang.String)
     */
    @Override
    public void deleteProject(Caseless projectName)
            throws UniformAppletException {
        if (projectName == null) {
            throw new NullPointerException("Project name can not be null.");
        }
        communicator.deleteProject(projectName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * hr.fer.zemris.vhdllab.applets.main.interfaces.IResourceManager#existsFile
     * (java.lang.String, java.lang.String)
     */
    @Override
    public boolean existsFile(Caseless projectName, Caseless fileName)
            throws UniformAppletException {
        if (projectName == null) {
            throw new NullPointerException("Project name cant be null");
        }
        if (fileName == null) {
            throw new NullPointerException("File name cant be null");
        }
        return communicator.existsFile(projectName, fileName);
    }

    /* DATA EXTRACTION METHODS */

    private List<Caseless> getFilesFor(Caseless projectName)
            throws UniformAppletException {
        if (projectName == null) {
            throw new NullPointerException("Project name cant be null");
        }
        return communicator.findFilesByProject(projectName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * hr.fer.zemris.vhdllab.applets.main.interfaces.IResourceManager#getAllCircuits
     * (java.lang.String)
     */
    @Override
    public List<Caseless> getAllCircuits(Caseless projectName)
            throws UniformAppletException {
        List<Caseless> fileNames = getFilesFor(projectName);
        List<Caseless> circuits = new ArrayList<Caseless>();
        for (Caseless name : fileNames) {
            if (getFileType(projectName, name).isCircuit()) {
                circuits.add(name);
            }
        }
        return circuits;
    }

    private FileType getFileType(Caseless projectName, Caseless fileName) {
        if (projectName == null) {
            throw new NullPointerException("Project name cant be null");
        }
        if (fileName == null) {
            throw new NullPointerException("File name cant be null");
        }
        try {
            return communicator.loadFileType(projectName, fileName);
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @seehr.fer.zemris.vhdllab.applets.main.interfaces.IResourceManager#
     * extractHierarchy(java.lang.String)
     */
    @Override
    public Hierarchy extractHierarchy(Caseless projectName)
            throws UniformAppletException {
        if (projectName == null) {
            throw new NullPointerException("Project name cant be null");
        }
        return communicator.extractHierarchy(projectName);
    }

    /* IS-SOMETHING METHODS */

    private boolean isPredefined(Caseless projectName, Caseless fileName) {
        if (projectName == null || fileName == null) {
            return false;
        }
        FileType type = getFileType(projectName, fileName);
        if (type == null) {
            return false;
        }
        return type.equals(FileType.PREDEFINED);
    }

    /* FIRE EVENTS METHODS */

    /**
     * Fires beforeResourceCreation event.
     * 
     * @param projectName
     *            a name of a project
     * @param fileName
     *            a name of a file
     * @param type
     *            a file type
     * @throws ResourceVetoException
     *             a veto
     */
    private void fireBeforeResourceCreation(Caseless projectName,
            Caseless fileName, FileType type) throws ResourceVetoException {
        for (VetoableResourceListener l : getVetoableResourceListeners()) {
            l.beforeResourceCreation(projectName, fileName, type);
        }
    }

    /**
     * Fires resourceCreated event.
     * 
     * @param projectName
     *            a name of a project
     * @param fileName
     *            a name of a file
     * @param type
     *            a file type
     * @throws ResourceVetoException
     *             a veto
     */
    private void fireResourceCreated(Caseless projectName, Caseless fileName,
            FileType type) throws ResourceVetoException {
        for (VetoableResourceListener l : getVetoableResourceListeners()) {
            l.resourceCreated(projectName, fileName, type);
        }
    }

    /**
     * Fires resourceDeleted event.
     * 
     * @param projectName
     *            a name of a project
     * @param fileName
     *            a name of a file
     */
    private void fireResourceDeleted(Caseless projectName, FileInfo file) {
        for (VetoableResourceListener l : getVetoableResourceListeners()) {
            l.resourceDeleted(projectName, file);
        }
    }

}
