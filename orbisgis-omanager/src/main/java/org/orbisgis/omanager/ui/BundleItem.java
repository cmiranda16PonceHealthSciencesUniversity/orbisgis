/*
 * OrbisGIS is a GIS application dedicated to scientific spatial simulation.
 * This cross-platform GIS is developed at French IRSTV institute and is able to
 * manipulate and create vector and raster spatial information.
 *
 * OrbisGIS is distributed under GPL 3 license. It is produced by the "Atelier SIG"
 * team of the IRSTV Institute <http://www.irstv.fr/> CNRS FR 2488.
 *
 * Copyright (C) 2007-2012 IRSTV (FR CNRS 2488)
 *
 * This file is part of OrbisGIS.
 *
 * OrbisGIS is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * OrbisGIS is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * OrbisGIS. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, please consult: <http://www.orbisgis.org/>
 * or contact directly:
 * info_at_ orbisgis.org
 */
package org.orbisgis.omanager.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.osgi.framework.Bundle;
import org.osgi.framework.Constants;
import org.osgi.framework.Version;
import org.osgi.service.obr.Resource;

/**
 * A bundle that can be installed in local or/and on remote repository.
 * @author Nicolas Fortin
 */
public class BundleItem {
    private Resource obrResource; // only if a remote bundle is available
    private Bundle bundle;        // only for downloaded bundle

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BundleItem)) {
            return false;
        }
        BundleItem other = (BundleItem)o;
        return getSymbolicName().equals(other.getSymbolicName()) && getVersion().equals(other.getVersion());
    }

    @Override
    public int hashCode() {
        return getSymbolicName().hashCode() + getVersion().hashCode();
    }
    /**
     * Returns the symbolic name of this bundle.
     * @return The symbolic name of this bundle or empty string if this bundle
     *         does not have a symbolic name.
     */
    String getSymbolicName()  {
        if(bundle!=null) {
            return bundle.getSymbolicName();
        } else if(obrResource!=null){
            return obrResource.getSymbolicName();
        } else {
            return "";
        }
    }

    /**
     * @return Bundle version
     */
    Version getVersion() {
        if(bundle!=null) {
            return bundle.getVersion();
        } else if(obrResource!=null) {
            return obrResource.getVersion();
        } else {
            return new Version(0,0,0);
        }
    }
    /**
     * @param obrResource OSGi bundle repository resource reference. (remote bundle)
     */
    public void setObrResource(Resource obrResource) {
        this.obrResource = obrResource;
    }

    /**
     * @param bundle Bundle reference
     */
    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    /**
     * @return The bundle reference, can be null
     */
    public Bundle getBundle() {
        return bundle;
    }

    /**
     * @return OSGi bundle repository resource, can be null (remote bundle)
     */
    public Resource getObrResource() {
        return obrResource;
    }

    /**
     * @return The bundle presentation name (artifact-id by default)
     */
    String getPresentationName() {
        if(bundle!=null && bundle.getHeaders()!=null) {
            return bundle.getHeaders().get(Constants.BUNDLE_NAME);
        } else if(obrResource!=null) {
            return obrResource.getPresentationName();
        } else {
            return "Unknown";
        }
    }

    @Override
    public String toString() {
        return getPresentationName();
    }

    /**
     * @return The bundle short description. (empty string if none)
     */
    String getShortDescription() {
        return "";
    }

    /**
     * @return A map of bundle details to show on the right side of the GUI. (Title->Value)
     */
    Map<String,String> getDetails() {
        return new HashMap<String, String>();
    }

    /**
     * @return Bundle tags
     */
    List<String> getBundleCategories() {
        return new ArrayList<String>();
    }

    /**
     * @return True if the start method can be called.
     */
    boolean isStartReady() {
        return (bundle!=null) && (bundle.getState()==Bundle.INSTALLED || bundle.getState()==Bundle.RESOLVED);
    }

    /**
     * @return True if the stop method can be called.
     */
    boolean isStopReady() {
        return (bundle!=null) && (bundle.getState()==Bundle.ACTIVE);
    }
}
