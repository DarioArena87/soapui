package com.smartbear.integrations.swaggerhub.component;

import com.eviware.soapui.support.StringUtils;
import com.smartbear.integrations.swaggerhub.engine.ApiDescriptor;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SwaggerHubAPITableModel {
    private final StringProperty name;
    private final StringProperty descr;
    private final StringProperty oasVersion;
    private final ObjectProperty versions;
    private final ApiDescriptor descriptor;
    private final StringProperty visibility;
    private final StringProperty owner;
    private final ComboBox<String> versionCombo;

    public SwaggerHubAPITableModel(ApiDescriptor descriptor) {
        name = new SimpleStringProperty(descriptor.name);
        descr = new SimpleStringProperty(descriptor.description);
        oasVersion = new SimpleStringProperty(descriptor.oasVersion);
        if (descriptor.isPrivate) {
            visibility = new SimpleStringProperty("Private");
        }
        else {
            visibility = new SimpleStringProperty("Public");
        }

        versionCombo = new ComboBox(FXCollections.observableArrayList(formatVersion(descriptor.versions)));
        if (StringUtils.hasContent(descriptor.defaultVersion)) {
            for (String version : versionCombo.getItems()) {
                if (version.equals(descriptor.defaultVersion)) {
                    versionCombo.getSelectionModel().select(version);
                    break;
                }
            }
        }
        else {
            versionCombo.getSelectionModel().selectFirst();
        }
        versions = new SimpleObjectProperty(versionCombo);
        if (StringUtils.hasContent(descriptor.owner)) {
            owner = new SimpleStringProperty(descriptor.owner);
        }
        else {
            owner = new SimpleStringProperty("Unknown");
        }
        this.descriptor = descriptor;
    }

    public String getName() {
        return name.get();
    }

    public String getDescr() {
        return descr.get();
    }

    public String getOasVersion() {
        return oasVersion.get();
    }

    public Object getVersions() {
        return versions.get();
    }

    public ApiDescriptor getDescriptor() {
        return descriptor;
    }

    public ComboBox getVersionCombo() {
        return versionCombo;
    }

    private List<String> formatVersion(String[] versions) {
        ArrayList<String> temp = new ArrayList(Arrays.asList(versions));
        ArrayList<String> result = new ArrayList();
        for (String version : temp) {
            if (version.startsWith("*-")) {
                result.add(version.substring(2).trim());
            }
            else if (version.startsWith("-") || version.startsWith("*")) {
                result.add(version.substring(1).trim());
            }
            else {
                result.add(version);
            }
        }
        return result;
    }

    public String getVisibility() {
        return visibility.get();
    }

    public String getOwner() {
        return owner.get();
    }
}
