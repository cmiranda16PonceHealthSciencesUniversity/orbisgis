package org.orbisgis.core.renderer.se.parameter.color;

import java.awt.Color;
import org.gdms.data.DataSource;
import org.gdms.driver.DriverException;
import org.orbisgis.core.renderer.se.parameter.ParameterException;

import org.orbisgis.core.renderer.se.parameter.PropertyName;

public class ColorAttribute extends PropertyName implements ColorParameter {

    public ColorAttribute(String fieldName, DataSource ds) throws DriverException{
        super(fieldName, ds);
    }

    @Override
    public boolean dependsOnFeature(){
        return true;
    }

    @Override
    public Color getColor(DataSource ds, long fid) throws ParameterException{
        try{
            return Color.getColor(getFieldValue(ds, (int)fid).getAsString()); //
        } catch (Exception e) {
            throw new ParameterException("Could not fetch feature attribute \""+ fieldName +"\"");
        }
    }

}