/*
 * SoapUI, Copyright (C) 2004-2022 SmartBear Software
 *
 * Licensed under the EUPL, Version 1.1 or - as soon as they will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the Licence is
 * distributed on an "AS IS" basis, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the Licence for the specific language governing permissions and limitations
 * under the Licence.
 */

package com.eviware.soapui.model.workspace;

import com.eviware.soapui.model.ModelItem;
import com.eviware.soapui.model.project.Project;
import com.eviware.soapui.model.project.SaveStatus;
import com.eviware.soapui.support.SoapUIException;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * SoapUI workspace behaviour
 *
 * @author Ole.Matzura
 */

public interface Workspace extends ModelItem {
    Project getProjectAt(int index);

    Project getProjectByName(String projectName);

    int getProjectCount();

    SaveStatus onClose();

    SaveStatus save(boolean workspaceOnly);

    void addWorkspaceListener(WorkspaceListener listener);

    void removeWorkspaceListener(WorkspaceListener listener);

    Project createProject(String name, File file) throws SoapUIException;

    void removeProject(Project project);

    Project importProject(String filename) throws SoapUIException;

    Project importProject(InputStream inputStream);

    int getIndexOfProject(Project project);

    String getPath();

    List<? extends Project> getProjectList();

    void switchWorkspace(File newPath) throws SoapUIException;

    Project openProject(Project modelItem) throws SoapUIException;

    void inspectProjects();

    boolean isSupportInformationDialog();

    void setSupportInformationDialog(boolean value);
}
