/*
 * Copyright 2016 Chandra X-Ray Observatory.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cfa.vo.iris.visualizer.plotter;

import cfa.vo.iris.sed.quantities.SPVYUnit;
import cfa.vo.iris.sed.quantities.XUnit;
import cfa.vo.iris.visualizer.preferences.SedModel;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 *
 * Unit selection widget. X-units are on the left split pane, Y-units are
 * on the right side.
 */
@SuppressWarnings("serial")
public class UnitsWidget extends javax.swing.JPanel {

    /**
     * Creates new form UnitsWidget
     * @param plotter 
     */
    public UnitsWidget() {
        initComponents();
    }
    
    public void updateCurrentUnits(List<SedModel> sedModels) {
        if (CollectionUtils.isEmpty(sedModels)) {
            return;
        }
        
        String x = sedModels.get(0).getXunits();
        String y = sedModels.get(0).getYunits();
        
        // TODO: This check may not be necessary when we support multiple SEDs, but
        // leaving this here as an extra check that the logic elsewhere is correct.
        for (SedModel model : sedModels) {           
            if (!StringUtils.equals(x, model.getXunits()) ||
                !StringUtils.equals(y, model.getYunits())) 
            {
                throw new IllegalArgumentException("Units changes requires all models to have the same units!");
            }
        }
        
        this.setXunit(x);
        this.setYunit(y);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        xunits = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        yunits = new javax.swing.JList();

        jSplitPane1.setDividerLocation(200);
        jSplitPane1.setResizeWeight(0.5);
        jSplitPane1.setPreferredSize(new java.awt.Dimension(550, 200));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("X Units"));

        xunits.setModel(new XUnitsListModel());
        xunits.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        xunits.setName("xunitsList"); // NOI18N

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${xunit}"), xunits, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(xunits);

        jSplitPane1.setLeftComponent(jScrollPane1);

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("Y Units"));

        yunits.setModel(new YUnitsListModel());
        yunits.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        yunits.setName("yunitsList"); // NOI18N

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${yunit}"), yunits, org.jdesktop.beansbinding.BeanProperty.create("selectedElement"));
        bindingGroup.addBinding(binding);

        jScrollPane2.setViewportView(yunits);

        jSplitPane1.setRightComponent(jScrollPane2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                .addContainerGap())
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JList xunits;
    private javax.swing.JList yunits;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    /**
     * Update the units of the provided SedModels, as passed through
     * by the visualizer data model.
     */
    public void updateUnits(List<SedModel> sedModels) {
        // if SED is null, don't do anything.
        if (CollectionUtils.isEmpty(sedModels)) {
            return;
        }
        
        // fire Visualizer event to update plot and MB
        for (SedModel model : sedModels) {
            model.setUnits(xunit, yunit);
        }
        
        this.setVisible(false);
    }
    
    /*
     *
     * getters and setters
     *
     */
    
    private String xunit;
    public static final String PROP_XUNIT = "xunit";

    /**
     * Get the value of xunit
     *
     * @return the value of xunit
     */
    public String getXunit() {
        return xunit;
    }

    /**
     * Set the value of xunit
     *
     * @param xunit new value of xunit
     */
    public void setXunit(String xunit) {
        String oldXunit = this.xunit;
        this.xunit = xunit;
        firePropertyChange(PROP_XUNIT, oldXunit, xunit);
    }

    private String yunit;
    public static final String PROP_YUNIT = "yunit";

    /**
     * Get the value of yunit
     *
     * @return the value of yunit
     */
    public String getYunit() {
        return yunit;
    }

    /**
     * Set the value of yunit
     *
     * @param yunit new value of yunit
     */
    public void setYunit(String yunit) {
        String oldYunit = this.yunit;
        this.yunit = yunit;
        firePropertyChange(PROP_YUNIT, oldYunit, yunit);
    }
    
    /*
     * end of getters and setters
     *
     */
    
    /*
     * List Models for Y and X units
     *
     */
    
    private class XUnitsListModel extends AbstractListModel {

        List<String> xunits = new ArrayList<>();
        
        public XUnitsListModel() {
            for (XUnit unit : XUnit.values()) {
                xunits.add(unit.getString());
            }
        }
        @Override
        public int getSize() {
            return xunits.size();
        }

        @Override
        public Object getElementAt(int i) {
            return xunits.get(i);
        }
    }
    
    private class YUnitsListModel extends AbstractListModel {

        List<String> yunits = new ArrayList<>();
        
        public YUnitsListModel() {
            for (SPVYUnit unit : SPVYUnit.values()) {
                yunits.add(unit.getString());
            }
        }
        @Override
        public int getSize() {
            return yunits.size();
        }

        @Override
        public Object getElementAt(int i) {
            return yunits.get(i);
        }
    }
}
