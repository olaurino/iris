/**
 * Copyright (C) 2015 Smithsonian Astrophysical Observatory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cfa.vo.iris.visualizer.stil;

import javax.swing.JPanel;
import cfa.vo.iris.IWorkspace;
import cfa.vo.iris.sed.ExtSed;
import cfa.vo.iris.sed.SedlibSedManager;
import cfa.vo.iris.visualizer.plotter.PlotPreferences;
import cfa.vo.iris.visualizer.plotter.SegmentLayer;
import cfa.vo.iris.visualizer.preferences.SedPreferences;
import cfa.vo.iris.visualizer.preferences.VisualizerComponentPreferences;
import cfa.vo.sedlib.Segment;
import uk.ac.starlink.ttools.plot2.geom.PlaneAspect;
import uk.ac.starlink.ttools.plot2.geom.PlaneSurfaceFactory;
import uk.ac.starlink.ttools.plot2.task.PlanePlot2Task;
import uk.ac.starlink.ttools.plot2.task.PlotDisplay;
import uk.ac.starlink.ttools.task.MapEnvironment;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.border.BevelBorder;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.awt.GridLayout;
import java.awt.Insets;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StilPlotter extends JPanel {

    private static final Logger logger = Logger
            .getLogger(StilPlotter.class.getName());

    private static final long serialVersionUID = 1L;
    
    // default STILTS parameter for creating plot displays. If true, the
    // plotter is optimized for data that may change on the fly.
    private static final boolean DATA_MAY_CHANGE = true;

    private PlotDisplay<PlaneSurfaceFactory.Profile, PlaneAspect> display;

    private IWorkspace ws;
    private SedlibSedManager sedManager;
    private ExtSed currentSed;
    private VisualizerComponentPreferences preferences;
    
    private MapEnvironment env;
    
    public StilPlotter() {
        setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        setBackground(Color.WHITE);
        setLayout(new GridLayout(1, 0, 0, 0));
    }
    
    public StilPlotter(IWorkspace ws, 
            VisualizerComponentPreferences preferences) {
        this.ws = ws;
        this.sedManager = (SedlibSedManager) ws.getSedManager();
        this.preferences = preferences;
        
        setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        setBackground(Color.WHITE);
        setLayout(new GridLayout(1, 0, 0, 0));

        reset(null, true);
    }

    /**
     * 
     * @param sed
     *            Sed to reset plot to
     * @param dataMayChange
     *            indicates if the data on the plot may change while while the
     *            current display is active.
     */
    public void reset(ExtSed sed, boolean dataMayChange) {
        
        // get the fixed parameter from the selected SED
        boolean fixed = this.getPlotPreferences(sed).getFixed();
        
        resetPlot(sed, fixed, dataMayChange);
    }

    /**
     * Redraws the current plot in place. Useful for changes to the plot
     * preferences.
     *
     */
    public void redraw(boolean dataMayChange) {
        // If there's no display then don't change anything
        if (display == null) {
            return;
        }
        
        resetPlot(currentSed, true, dataMayChange);
    }
    
    /**
     * Resets the plot.
     * 
     * TODO: Allow users to fix a plot in place either from plot preferences or from the
     *  PlotterView.
     *  
     * @param sed - the sed to plot
     * @param fixed - if set to true, the plot bounds will not change
     * @param dataMayChange - if the data in the sed will change (usually true)
     */
    private void resetPlot(ExtSed sed,
                           boolean fixed, 
                           boolean dataMayChange) 
    {

        // Clear the display if it's available
        setupForPlotDisplayChange();

        // Update the current SED
        if (sed == null) {
            sed = sedManager.getSelected();
        }
        this.currentSed = sed;
        
        // Setup new plot component
        boolean cached = !dataMayChange;
        display = createPlotComponent(currentSed, cached);
        
        // Set the bounds using the current SED's aspect if the plot is fixed
        if (fixed) {
            PlaneAspect existingAspect = this.getPlotPreferences().getAspect();
            display.setAspect(existingAspect);
        }
        
        // Add the display to the plot view
        updatePlotDisplay();
    }
    
    /**
     * Change the plotting space between logarithmic and linear. One of the axes
     * can be logarithmic, while the other is linear.
     * @param plotType the plot type to use. Must be one of the 
     * PlotPreferences.PlotType enums. Choices are LOG, LINEAR, XLOG, and YLOG.
     */
    public void changePlotType(PlotPreferences.PlotType plotType) {
                
        try {
            this.getPlotPreferences().setPlotType(plotType);
            reset(currentSed, DATA_MAY_CHANGE);
        } catch (EnumConstantNotPresentException ex) {
            logger.log(Level.WARNING, ex.getMessage());
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }        
    }
    
    public void setGridOn(boolean on) {
        try {
            this.getPlotPreferences().setShowGrid(on);
            reset(currentSed, DATA_MAY_CHANGE);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Zoom in or out of plot by a given scale factor.
     * @param zoomFactor 
     */
    public void zoom(double zoomFactor) {
        
        throw new UnsupportedOperationException("The zoom functionality is"
                + "currently unsupported.");

        // TODO: this algorithm is BAD! Need to implement a better one.
//        double xmax = this.getPlotDisplay().getAspect().getXMax();
//        double xmin = this.getPlotDisplay().getAspect().getXMin();
//        double ymax = this.getPlotDisplay().getAspect().getYMax();
//        double ymin = this.getPlotDisplay().getAspect().getYMin();
//        
//        double[] ylimits = new double[] {ymin*zoomFactor, ymax-ymax*(zoomFactor-1)};
//        double[] xlimits = new double[] {xmin*zoomFactor, xmax-xmax*(zoomFactor-1)};
//        
//        PlaneAspect zoomedAspect = new PlaneAspect(xlimits, ylimits);
//        
//        this.getPlotDisplay().setAspect(zoomedAspect);
    }
    
    /**
     * Hide the error bars from the plot display.
     */
    public void hideErrorBars() {
        
    }
    
    public StilPlotter setWorkSpace(IWorkspace ws) {
        this.ws = ws;
        return this;
    }
    
    public IWorkspace getWorkSpace() {
        return this.ws;
    }
    
    public StilPlotter setVisualizerPreferences(VisualizerComponentPreferences prefs) {
        this.preferences = prefs;
        return this;
    }
    
    public VisualizerComponentPreferences getVisualizerPreferences() {
        return this.preferences;
    }
    
    public StilPlotter setSedManager(SedlibSedManager sedManager) {
        this.sedManager = sedManager;
        return this;
    }
    
    public SedlibSedManager getSedManager() {
        return this.sedManager;
    }
    
    public ExtSed getSed() {
        return currentSed;
    }

    public Map<Segment, SegmentLayer> getSegmentsMap() {
        return Collections.unmodifiableMap(preferences
                .getSedPreferences(currentSed).getAllSegmentPreferences());
    }

    public PlotDisplay<PlaneSurfaceFactory.Profile, PlaneAspect> getPlotDisplay() {
        return display;
    }

    /**
     * Get the value of env
     *
     * @return the value of env
     */
    protected MapEnvironment getEnv() {
        return env;
    }

    /**
     * 
     * @param sed
     *            the SED to plot
     * @param cached
     *            If true, cache the environment. Should be false if the data
     *            might change.
     * @return
     */
    protected PlotDisplay<PlaneSurfaceFactory.Profile, PlaneAspect> 
        createPlotComponent(ExtSed sed, boolean cached) 
    {
        logger.info("Creating new plot from selected SED");

        try {
            setupMapEnvironment(sed);
            return createPlotComponent(env, cached);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 
     * @param env
     *            Plot display environment to use
     * @param cached
     *            If true, cache the environment. Should be false if the data
     *            might change.
     * @return PlotDisplay
     * @throws Exception
     */
    protected PlotDisplay<PlaneSurfaceFactory.Profile, PlaneAspect> createPlotComponent(
            MapEnvironment env, boolean cached) throws Exception {

        logger.log(Level.FINE, "plot environment:");
        logger.log(Level.FINE, ReflectionToStringBuilder.toString(env));
        
        return new PlanePlot2Task().createPlotComponent(env, cached);
    }

    protected void setupMapEnvironment(ExtSed sed) throws IOException {

        env = new MapEnvironment();
        env.setValue("type", "plot2plane");
        env.setValue("insets", new Insets(50, 80, 50, 50));
        // TODO: force numbers on Y axis to only be 3-5 digits long. Keeps
        // Y-label from falling off the jpanel. Conversely, don't set "insets"
        // and let the plotter dynamically change size to keep axes labels
        // on the plot.

        // Add high level plot preferences
        PlotPreferences pp = getPlotPreferences(sed);
        for (String key : pp.getPreferences().keySet()) {
            env.setValue(key, pp.getPreferences().get(key));
        }

        if (sed != null) {
            // set title of plot if available
            env.setValue("title", sed.getId());

            // Add segments and segment preferences
            addSegmentLayers(sed, env);
        }
    }

    private void addSegmentLayers(ExtSed sed, MapEnvironment env)
            throws IOException {
        if (sed == null) {
            logger.info("No SED selected, returning empty plot");
            return;
        }

        logger.info(String.format("Plotting SED with %s segments...",
                sed.getNamespace()));

        SedPreferences prefs = preferences.getSedPreferences(sed);
        for (int i = 0; i < sed.getNumberOfSegments(); i++) {
            SegmentLayer layer = prefs.getSegmentPreferences(sed.getSegment(i));
            for (String key : layer.getPreferences().keySet()) {
                env.setValue(key, layer.getPreferences().get(key));
            }
        }
    }
    
    /**
     * Sets PlotDisplay up for new changes, like changing a SED color,
     * switching from linear to logarithmic plotting, etc.
     */
    private void setupForPlotDisplayChange() {
        if (display != null) {
            display.removeAll();
            remove(display);
            
            this.getPlotPreferences().setAspect(display.getAspect());
        }
    }
    
    /**
     * Add and update the PlotDisplay.
     */
    private void updatePlotDisplay() {
        add(display, BorderLayout.CENTER);
        display.revalidate();
        display.repaint();
    }
    
    public PlotPreferences getPlotPreferences() {
        if (this.preferences.getSedPreferences(currentSed) != null) {
            return preferences.getSedPreferences(currentSed).getPlotPreferences();
        } else {
            return preferences.getPlotPreferences();
        }
    }
    
    public PlotPreferences getPlotPreferences(ExtSed sed) {
        if (this.preferences.getSedPreferences(sed) != null) {
            return preferences.getSedPreferences(sed).getPlotPreferences();
        } else {
            return preferences.getPlotPreferences();
        }
    }
}