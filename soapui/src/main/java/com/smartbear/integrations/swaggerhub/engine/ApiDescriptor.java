package com.smartbear.integrations.swaggerhub.engine;

public class ApiDescriptor {
    public String name;
    public String description;
    public String swaggerUrl;
    public String oasVersion;
    public String owner;
    public String defaultVersion;
    public String[] versions;
    public boolean isPrivate;
    public boolean isPublished;

    @Override
    public String toString() {
        return name + " - " + description + ((description.length() > 0) ? " " : "") + "[" + versions.length + " version" + ((versions.length == 1) ? "]" : "s]");
    }
}
