package hr.fer.zemris.vhdllab.platform.manager.view;

import hr.fer.zemris.vhdllab.applets.main.interfaces.ISystemContainer;
import hr.fer.zemris.vhdllab.platform.manager.editor.EditorManagerFactory;
import hr.fer.zemris.vhdllab.platform.manager.file.FileManager;
import hr.fer.zemris.vhdllab.platform.manager.project.ProjectManager;
import hr.fer.zemris.vhdllab.platform.manager.workspace.IdentifierToInfoObjectMapper;

import javax.swing.JPanel;

public interface View {

    ISystemContainer getSystemContainer();

    void setSystemContainer(ISystemContainer systemContainer);

    EditorManagerFactory getEditorManagerFactory();

    void setEditorManagerFactory(EditorManagerFactory editorManagerFactory);

    IdentifierToInfoObjectMapper getMapper();

    void setMapper(IdentifierToInfoObjectMapper mapper);

    ProjectManager getProjectManager();

    void setProjectManager(ProjectManager projectManager);

    FileManager getFileManager();

    void setFileManager(FileManager fileManager);

    void init();

    JPanel getPanel();

}
