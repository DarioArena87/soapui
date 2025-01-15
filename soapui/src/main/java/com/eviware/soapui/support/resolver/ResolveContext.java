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

package com.eviware.soapui.support.resolver;

import com.eviware.soapui.impl.wsdl.AbstractWsdlModelItem;
import com.eviware.soapui.support.StringUtils;
import com.eviware.soapui.support.UISupport;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResolveContext<T extends AbstractWsdlModelItem<?>> {
    private final T modelItem;
    private final List<PathToResolve> pathsToResolve = new ArrayList<PathToResolve>();

    public ResolveContext(T modelItem) {
        this.modelItem = modelItem;
    }

    public T getModelItem() {
        return modelItem;
    }

    public PathToResolve addPathToResolve(AbstractWsdlModelItem<?> owner, String description, String path) {
        PathToResolve pathToResolve = new PathToResolve(owner, description, path);
        pathsToResolve.add(pathToResolve);
        return pathToResolve;
    }

    public PathToResolve addPathToResolve(
        AbstractWsdlModelItem<?> owner, String description, String path, Resolver resolver
    ) {
        PathToResolve pathToResolve = new PathToResolve(owner, description, path);
        pathToResolve.addResolvers(resolver);
        pathsToResolve.add(pathToResolve);
        return pathToResolve;
    }

    public boolean isEmpty() {
        return pathsToResolve.isEmpty();
    }

    public List<PathToResolve> getPathsToResolve() {
        return pathsToResolve;
    }

    public int getUnresolvedCount() {
        int resultCnt = 0;

        for (PathToResolve ptr : pathsToResolve) {
            if (ptr.getResolver() == null || !ptr.getResolver().isResolved()) {
                resultCnt++;
            }
        }

        return resultCnt;
    }

    public int apply() {
        int resultCnt = 0;

        for (PathToResolve ptr : pathsToResolve) {
            if (ptr.resolve()) {
                resultCnt++;
            }
        }

        return resultCnt;
    }

    public boolean hasThisModelItem(AbstractWsdlModelItem<?> modelItem, String description, String pathName) {
        // if removed path is changed and turned to null. that is ok.
        if (pathName == null) {
            return true;
        }
        PathToResolve pathToCheck = new PathToResolve(modelItem, description, pathName);
        for (PathToResolve path : pathsToResolve) {
            if (path.equals(pathToCheck)) {
                return true;
            }
        }
        return false;
    }

    public PathToResolve getPath(AbstractWsdlModelItem<?> modelItem, String description, String pathName) {
        PathToResolve pathToCheck = new PathToResolve(modelItem, description, pathName);
        for (PathToResolve path : pathsToResolve) {
            if (path.equals(pathToCheck)) {
                return path;
            }
        }
        return null;
    }

    public interface Resolver {
        boolean resolve();

        boolean isResolved();

        String getResolvedPath();

        Object getDescription();
    }

    public abstract static class FileResolver implements Resolver {
        private final String title;
        private final String extension;
        private final String fileType;
        private final String current;
        private File result;
        private boolean resolved;

        public FileResolver(String title, String extension, String fileType, String current) {

            this.title = title;
            this.extension = extension;
            this.fileType = fileType;
            this.current = current;
        }

        public abstract boolean apply(File newFile);

        public boolean resolve() {
            result = UISupport.getFileDialogs().open(this, title, extension, fileType, current);
            if (result != null) {
                resolved = apply(result);
            }

            return resolved;
        }

        public boolean isResolved() {
            return resolved;
        }

        public String getResolvedPath() {
            return result == null ? null : result.getAbsolutePath();
        }

        public Object getDescription() {
            return title;
        }

        @Override
        public String toString() {
            return (String)getDescription();
        }
    }

    public abstract static class DirectoryResolver implements Resolver {
        private final String title;
        private final String current;
        private File result;
        private boolean resolved;

        public DirectoryResolver(String title, String current) {

            this.title = title;
            this.current = current;
        }

        public abstract boolean apply(File newFile);

        public boolean resolve() {
            result = UISupport.getFileDialogs().openDirectory(this, title, StringUtils.isNullOrEmpty(current) ? null : new File(current));
            if (result != null) {
                resolved = apply(result);
            }

            return resolved;
        }

        public boolean isResolved() {
            return resolved;
        }

        public String getResolvedPath() {
            return result == null ? null : result.getAbsolutePath();
        }

        public Object getDescription() {
            return title;
        }

        public String toString() {
            return (String)getDescription();
        }
    }

    public class PathToResolve {
        private final AbstractWsdlModelItem<?> owner;
        private final String description;
        private final String path;
        private final List<Resolver> resolvers = new ArrayList<Resolver>();
        private Resolver resolver;
        private boolean resolved;

        public PathToResolve(AbstractWsdlModelItem<?> owner, String description, String path) {
            this.owner = owner;
            this.description = description;
            this.path = path;
        }

        public void addResolvers(Resolver... resolvers) {
            Collections.addAll(this.resolvers, resolvers);
        }

        public AbstractWsdlModelItem<?> getOwner() {
            return owner;
        }

        public String getDescription() {
            return description;
        }

        public Resolver getResolver() {
            return resolver;
        }

        public void setResolver(Object resolveOrDefaultAction) {
            resolver = (Resolver)resolveOrDefaultAction;
        }

        public String getPath() {
            return path;
        }

        public boolean resolve() {
            if (resolver != null) {
                resolved = resolver.resolve();
                return resolved;
            }

            return false;
        }

        public ArrayList<Resolver> getResolvers() {
            return (ArrayList<Resolver>)resolvers;
        }

        public boolean isResolved() {
            return resolved;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + ((description == null) ? 0 : description.hashCode());
            result = prime * result + ((owner == null) ? 0 : owner.hashCode());
            result = prime * result + ((path == null) ? 0 : path.hashCode());
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            PathToResolve other = (PathToResolve)obj;
            if (!getOuterType().equals(other.getOuterType())) {
                return false;
            }
            if (description == null) {
                if (other.description != null) {
                    return false;
                }
            }
            else if (!description.equals(other.description)) {
                return false;
            }
            if (owner == null) {
                if (other.owner != null) {
                    return false;
                }
            }
            else if (!owner.equals(other.owner)) {
                return false;
            }
            if (path == null) {
                return other.path == null;
            }
            else return path.equals(other.path);
        }

        @SuppressWarnings("rawtypes")
        private ResolveContext getOuterType() {
            return ResolveContext.this;
        }

        public void setSolved(boolean solved) {
            resolved = solved;
        }

        public boolean update() {
            for (Resolver resolver : resolvers) {
                if (resolver.isResolved()) {
                    return true;
                }
            }
            return false;
        }
    }
}
