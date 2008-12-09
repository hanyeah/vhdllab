package hr.fer.zemris.vhdllab.platform.manager.workspace.support;

import hr.fer.zemris.vhdllab.api.workspace.Workspace;
import hr.fer.zemris.vhdllab.platform.listener.AutoPublished;

import java.util.EventListener;

@AutoPublished(publisher = WorkspaceInitializer.class)
public interface WorkspaceInitializationListener extends EventListener {

    void initialize(Workspace workspace);

}