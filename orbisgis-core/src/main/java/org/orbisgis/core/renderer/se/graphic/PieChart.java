package org.orbisgis.core.renderer.se.graphic;


import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import javax.media.jai.RenderableGraphics;
import org.gdms.data.DataSource;
import org.orbisgis.core.renderer.se.common.PieChartType;
import org.orbisgis.core.renderer.se.common.RenderContextFactory;
import org.orbisgis.core.renderer.se.common.Uom;
import org.orbisgis.core.renderer.se.fill.Fill;
import org.orbisgis.core.renderer.se.label.StyledLabel;
import org.orbisgis.core.renderer.se.parameter.ParameterException;
import org.orbisgis.core.renderer.se.parameter.real.RealParameter;
import org.orbisgis.core.renderer.se.stroke.Stroke;

public class PieChart extends Graphic {

    public PieChart(){
        slices = new ArrayList<Slice>();
    }


    public int getNumSlices() {
        return slices.size();
    }

    public Slice getSlice(int i) {
        System.out.println (getNumSlices() +" slices, fetch " + i);
        if (i >= 0 && i < getNumSlices()) {
            return slices.get(i);
        } else {
            return null;
        }
    }

    public void addSlice(Slice slice) {
        if (slice != null) {
            slices.add(slice);
            slice.setParent(this);
        }
    }

    public void moveSliceUp(int i) {
        // déplace i vers i-1
        if (slices.size() > 1) {
            if (i > 0 && i < slices.size()) {
                Slice tmp = slices.get(i);
                slices.set(i, slices.get(i - 1));
                slices.set(i - 1, tmp);
            } else {
                // TODO throw
            }
        }
    }

    public void moveSliceDown(int i) {
        // déplace i vers i+1
        if (slices.size() > 1) {
            if (i >= 0 && i < slices.size() - 1) {
                Slice tmp = slices.get(i);
                slices.set(i, slices.get(i + 1));
                slices.set(i + 1, tmp);
            } else {
                // TODO throw
            }
        }
    }

    public boolean isDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(boolean displayValue) {
        this.displayValue = displayValue;
    }

    public RealParameter getHoleRadius() {
        return holeRadius;
    }

    public void setHoleRadius(RealParameter holeRadius) {
        this.holeRadius = holeRadius;
    }

    public RealParameter getRadius() {
        return radius;
    }

    public void setRadius(RealParameter radius) {
        this.radius = radius;
    }

    public Stroke getStroke() {
        return stroke;
    }

    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
        stroke.setParent(this);
    }

    public PieChartType getType() {
        return type;
    }

    public void setType(PieChartType type) {
        this.type = type;
    }

    /**
     *
     * @param ds
     * @param fid
     */
    @Override
    public RenderableGraphics getRenderableGraphics(DataSource ds, long fid) throws ParameterException, IOException {

        int nSlices = slices.size();

        double total = 0.0;
        double[] values = new double[nSlices];
        double[] stackedValues = new double[nSlices];
        double[] gaps = new double[nSlices];

        double r = 30; // 30px by default

        System.out.println("UOM: " + getUom());

        if (radius != null) {
            r = Uom.toPixel(this.getRadius().getValue(ds, fid), this.getUom(), 96, 25000); // TODO DPI + SCALE
        }

        double holeR = 0.0;

        Area hole = null;
        if (this.holeRadius != null) {
            holeR = Uom.toPixel(this.getHoleRadius().getValue(ds, fid), this.getUom(), 96, 25000); // TODO DPI + SCALE

            hole = new Area(new Arc2D.Double(-holeR, -holeR, 2*holeR, 2*holeR, 0, 260, Arc2D.CHORD));
        }

        double maxGap = 0.0;

        for (int i = 0; i < nSlices; i++) {
            Slice slc = slices.get(i);
            values[i] = slc.getValue().getValue(ds, fid);
            total += values[i];
            stackedValues[i] = total;
            RealParameter gap = slc.getGap();
            if (gap != null) {
                gaps[i] = Uom.toPixel(slc.getGap().getValue(ds, fid), this.getUom(), 96, 25000);
            } else {
                gaps[i] = 0.0;
            }

            System.out.println ("Gap : " + gaps[i]);

            maxGap = Math.max(gaps[i], maxGap);
        }

        double pieMaxR = r + maxGap;
        
        if (stroke != null)
            pieMaxR += stroke.getMaxWidth(ds, fid);


        Rectangle2D bounds = new Rectangle2D.Double(-pieMaxR, -pieMaxR, 2*pieMaxR, 2*pieMaxR);

        RenderableGraphics rg;

        AffineTransform at = null;
        if (this.getTransform() != null){
            at = this.getTransform().getGraphicalAffineTransform(ds, fid, false);
        
            // Apply the AT to the bbox
            Shape newBounds = at.createTransformedShape(bounds);

            rg = Graphic.getNewRenderableGraphics(newBounds.getBounds2D(), 0);
        }
        else{
            at = new AffineTransform();
            rg = Graphic.getNewRenderableGraphics(bounds, 0);
        }

        // Now, the total is defines, we can compute percentages and slices begin/end angles
        double[] percentages = new double[nSlices];

        for (int i = 0; i < nSlices; i++) {
            if (i == 0) {
                percentages[i] = 0.0;
            } else {
                percentages[i] = stackedValues[(i - 1 + nSlices) % nSlices] / total;
            }
        }

        // Create BufferedImage imageWidth x imageWidth

        Shape[] shapes = new Shape[nSlices];

        double maxDeg = 360.0;
        
        if (this.getType() == PieChartType.HALF)
            maxDeg = 180.0;

        // Create slices
        for (int i = 0; i < nSlices; i++) {
            double aStart = percentages[i]*maxDeg;

            double aExtend;
            
            if (i < nSlices - 1)
                aExtend = (percentages[(i+1) % nSlices] - percentages[i])*maxDeg;
            else{
                aExtend = maxDeg - (percentages[i])*maxDeg;
            }


            Area gSlc = new Area(new Arc2D.Double(-r, -r, 2*r, 2*r, aStart, aExtend, Arc2D.PIE));

            if (hole != null){
                gSlc.subtract(hole);
            }


            double alphaMiddle = (aStart + aExtend/2.0)*Math.PI/180.0;

            // create AT = GraphicTransform + T(gap)
            AffineTransform gapAt = AffineTransform.getTranslateInstance(Math.cos(alphaMiddle)*gaps[i],
                           -Math.sin(alphaMiddle)*gaps[i]);

            gapAt.preConcatenate(at);

            Shape atShp = gapAt.createTransformedShape(gSlc);

            shapes[i] = atShp;

            Fill fill = getSlice(i).getFill();


            if  (fill != null){
                fill.draw(rg, atShp, ds, fid);
            }


            if (displayValue){
                double p;
                if (i==nSlices -1 )
                    p = 1.0 - percentages[i];
                else
                    p = percentages[i+ 1] - percentages[i];
                p *= 100;

                StyledLabel label = new StyledLabel(Double.toString(p));
                AffineTransform labelAt = (AffineTransform) gapAt.clone();


                double labelPosRatio;
                if (this.holeRadius != null)
                    labelPosRatio = (r - holeR)/2.0 + holeR;
                else
                    labelPosRatio = r*0.66;

                labelAt.concatenate(AffineTransform.getTranslateInstance(Math.cos(alphaMiddle)*labelPosRatio,
                           -Math.sin(alphaMiddle)*labelPosRatio));

                Rectangle2D anchor = labelAt.createTransformedShape(new Rectangle2D.Double(0,0, 1, 1)).getBounds2D();

                rg.drawRenderedImage(label.getImage(ds, fid).createRendering(RenderContextFactory.getContext()),
                        AffineTransform.getTranslateInstance(anchor.getCenterX(), anchor.getCenterY()));
            }

        }

        // Stokes must be drawn after fills 
        if (stroke != null){
            for (int i=0;i<nSlices;i++){
                stroke.draw(rg, shapes[i], ds, fid);
            }
        }

        return rg;
    }

    @Override
    public double getMaxWidth(DataSource ds, long fid) throws ParameterException, IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    private PieChartType type;
    private RealParameter radius;
    private RealParameter holeRadius;
    private boolean displayValue;
    private Stroke stroke;
    private ArrayList<Slice> slices;
}