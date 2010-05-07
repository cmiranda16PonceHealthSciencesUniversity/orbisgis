package org.orbisgis.core.renderer.se.parameter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import org.gdms.data.DataSource;

import org.orbisgis.core.renderer.se.parameter.real.RealParameter;

/**
 *
 * @param <ToType> One of ColorParameter, RealParameter, StringParameter
 * @param <FallbackType> the Literal implementation of <ToType>
 * @author maxence
 *
 * @todo implement categorizeBy* methods
 */

public abstract class Categorize<ToType extends SeParameter, FallbackType extends ToType> implements SeParameter {

    public Categorize(ToType firstClassValue, FallbackType fallbackValue, RealParameter lookupValue) {
        this.firstClass = firstClassValue;
        this.fallbackValue = fallbackValue;
        this.lookupValue = lookupValue;
        this.classes = new ArrayList<Category<ToType>>();
        this.method = CategorizeMethod.MANUAL;
    }

    @Override
    public final boolean dependsOnFeature(){
        if (this.getLookupValue().dependsOnFeature())
            return true;

        int i;
        for (i=0;i<this.getNumClasses();i++){
           if (this.getClassValue(i).dependsOnFeature())
               return true;
        }

        return false;
    }
    
    public final void setFallbackValue(FallbackType fallbackValue) {
        this.fallbackValue = fallbackValue;
    }

    public final FallbackType getFallbackValue() {
        return fallbackValue;
    }

    public final void setLookupValue(RealParameter lookupValue) {
        this.lookupValue = lookupValue;
    }

    public final RealParameter getLookupValue() {
        return lookupValue;
    }

    /**
     * Return the number of classes defined within the classification. According to this number (n),
     *  available class IDs are [0;n] and IDs for threshold are [0;n-1]
     *
     *  @return number of defined class
     */
    public final int getNumClasses() {
        return classes.size() + 1;
    }

    /**
     * the new class begin from the specified threshold, up to the next one.
     * The class is inserted at the right place
     * @param threshold
     * @param value
     */
    public final void addClass(RealParameter threshold, ToType value) {
        classes.add(new Category<ToType>(value, threshold));
        sortClasses();
        this.method = CategorizeMethod.MANUAL;
    }

    public void removeClass(int i) {
        if (getNumClasses() > 1) {
            if (i < getNumClasses() && i >= 0) {
                if (i == 0) {
                    // when the first class is remove, the second one takes its place
                    Category<ToType> cat = classes.remove(0);
                    firstClass = cat.getClassValue();
                } else {
                    Category<ToType> cat = classes.remove(i - 1);
                }
            } else {
                // TODO Throws
            }
        } else {
            // TODO throws must have at least 1 category !!!
        }
        this.method = CategorizeMethod.MANUAL;
    }

    public ToType getClassValue(int i) {
        if (i == 0) {
            return firstClass;
        } else {
            return classes.get(i - 1).getClassValue();
        }
    }

    public void setClassValue(int i, ToType value) {
        if (i == 0) {
            firstClass = value;
        } else if (i > 0 && i < getNumClasses() - 1) {
            classes.get(i - 1).setClassValue(value);
        } else {
            // TODO throw
        }
    }

    public void setThresholdValue(int i, RealParameter threshold) {
        if (i >= 0 && i < getNumClasses() - 1) {
            classes.get(i).setThreshold(threshold);
            sortClasses();
        } else {
            // TODO throw
        }
        this.method = CategorizeMethod.MANUAL;
    }

    public RealParameter getThresholdValue(int i) {
        return classes.get(i).getThreshold();
    }

    public void setThresholdsSucceeding() {
        succeeding = true;
    }

    public boolean areThresholdsSucceeding() {
        return succeeding;
    }

    public void setThresholdsPreceding() {
        succeeding = false;
    }

    public boolean areThresholdsPreceding() {
        return (!succeeding);
    }

    private void sortClasses() {
        Collections.sort(classes);
    }

    protected ToType getParameter(DataSource ds, long fid) {
        try {
            if (getNumClasses() > 1) {

                double value = lookupValue.getValue(ds, fid);

                Iterator it = classes.iterator();

                while (it.hasNext()) {
                    Category<ToType> cat = (Category<ToType>) it.next();
                    double threshold = cat.getThreshold().getValue(ds, fid);

                    if ((!succeeding && value <= threshold) || ((value < threshold))) {
                        return cat.getClassValue();
                    }
                }
            } else {
                return firstClass;
            }
            return fallbackValue;

        } catch (Exception e) {
            return fallbackValue;
        }
    }


    /**
     *
     * @param ds 
     * @param values the values to affect to classes. number of values give the numbe of classes
     */
    public void categorizeByEqualsInterval(DataSource ds, ToType[] values){
        method = CategorizeMethod.EQUAL_INTERVAL;
        int n = values.length;
        // compute n-1 thresholds and assign values
    }

    /**
     *
     * @param ds
     * @param values the values to affect to classes. number of values give the numbe of classes
     */
    public void categorizeByNaturalBreaks(DataSource ds, ToType[] values){
        method = CategorizeMethod.NATURAL_BREAKS;
        int n = values.length;
        // compute n-1 thresholds and assign values
    }


    /**
     *
     * @param ds
     * @param values the values to affect to classes. number of values give the numbe of classes
     */
    public void categorizeByQuantile(DataSource ds, ToType[] values){
        method = CategorizeMethod.QUANTILE;
        int n = values.length;
        // compute n-1 thresholds and assign values
    }

    /**
     *
     *
     * @param ds
     * @param values the values to affect to classes. number of values give the numbe of classes
     * @param factor class (except first and last) interval equals sd*factor
     */
    public void categorizeByStandardDeviation(DataSource ds, ToType[] values, double factor){
        method = CategorizeMethod.STANDARD_DEVIATION;
        // even => mean is a threshold
        // odd => mean is the central point of the central class
        int n = values.length;

        // compute n-1 thresholds and assign values
        
    }

    private CategorizeMethod method;

    private boolean succeeding = true;
    private RealParameter lookupValue;
    protected FallbackType fallbackValue;
    private ToType firstClass;
    private ArrayList<Category<ToType>> classes;
}