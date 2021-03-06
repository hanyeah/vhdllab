/*******************************************************************************
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package hr.fer.zemris.vhdllab.platform.ui.wizard.support;

import hr.fer.zemris.vhdllab.entity.Project;
import hr.fer.zemris.vhdllab.platform.manager.workspace.WorkspaceListener;
import hr.fer.zemris.vhdllab.platform.manager.workspace.WorkspaceManager;
import hr.fer.zemris.vhdllab.platform.manager.workspace.support.WorkspaceInitializationListener;
import hr.fer.zemris.vhdllab.platform.manager.workspace.support.WorkspaceInitializer;
import hr.fer.zemris.vhdllab.service.workspace.FileReport;
import hr.fer.zemris.vhdllab.service.workspace.Workspace;

import org.apache.commons.lang.Validate;
import org.springframework.richclient.core.Guarded;

public class WorkspaceNotEmptyCommandGuard implements WorkspaceListener,
        WorkspaceInitializationListener {

    protected final WorkspaceManager manager;
    protected final WorkspaceInitializer initializer;
    private final Guarded guarded;

    public WorkspaceNotEmptyCommandGuard(WorkspaceManager manager,
            WorkspaceInitializer initializer, Guarded guarded) {
        Validate.notNull(manager);
        Validate.notNull(initializer);
        Validate.notNull(guarded);
        this.manager = manager;
        this.initializer = initializer;
        this.guarded = guarded;
        this.manager.addListener(this);
        this.initializer.addListener(this);
    }

    protected void updateEnabledState() {
        setEnabled(manager.getWorkspace().getProjectCount() != 0);
    }

    protected void setEnabled(boolean enabled) {
        guarded.setEnabled(enabled);
    }

    @Override
    public void fileCreated(FileReport report) {
        updateEnabledState();
    }

    @Override
    public void fileDeleted(FileReport report) {
        updateEnabledState();
    }

    @Override
    public void fileSaved(FileReport report) {
        updateEnabledState();
    }

    @Override
    public void projectCreated(Project project) {
        updateEnabledState();
    }

    @Override
    public void projectDeleted(Project project) {
        updateEnabledState();
    }

    @Override
    public void initialize(Workspace workspace) {
        updateEnabledState();
    }

}
