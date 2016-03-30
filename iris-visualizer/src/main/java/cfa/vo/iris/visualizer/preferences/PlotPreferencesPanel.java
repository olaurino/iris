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
package cfa.vo.iris.visualizer.preferences;

import cfa.vo.iris.visualizer.plotter.PlotPreferences;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 *
 * @author jbudynk
 */
public class PlotPreferencesPanel extends javax.swing.JPanel {

    final private PlotPreferences prefs;
    
    /**
     * Creates new form PlotPreferencesPanel
     * @param prefs global plot preferences
     */
    public PlotPreferencesPanel(PlotPreferences prefs) {
        
        this.prefs = prefs;
        
        initComponents();
        
        // plot range components
        boolean plotRangeOptionsVisible = this.prefs.getFixed();
        fixedCheckBox.setSelected(plotRangeOptionsVisible);
        setPlotRangeOptionsVisible(fixedCheckBox.isSelected());
        
        // Action listener to show plot range options.
        // TODO: Update the panel size when the check box is selected.
        fixedCheckBox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent ie) {
                if (fixedCheckBox.isSelected()) {
                    setPlotRangeOptionsVisible(true);
                    fixedCheckBox.setText("Fixed");
                } else {
                    setPlotRangeOptionsVisible(false);
                    fixedCheckBox.setText("Automatic");
                }
            }
        });
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

        gridLinesButtonGroup = new javax.swing.ButtonGroup();
        plotTypeLabel = new javax.swing.JLabel();
        gridLinesLabel = new javax.swing.JLabel();
        plotRangeLabel = new javax.swing.JLabel();
        showErrorsCheckBox = new javax.swing.JCheckBox();
        plotTypeComboBox = new javax.swing.JComboBox();
        gridLinesOnRadioButton = new javax.swing.JRadioButton();
        gridLinesOffRadioButton = new javax.swing.JRadioButton();
        fixedCheckBox = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        xminLabel = new javax.swing.JLabel();
        yminTextField = new javax.swing.JTextField();
        ymaxTextField = new javax.swing.JTextField();
        xmaxTextField = new javax.swing.JTextField();
        ymaxLabel = new javax.swing.JLabel();
        xminTextField = new javax.swing.JTextField();
        yminLabel = new javax.swing.JLabel();
        xmaxLabel = new javax.swing.JLabel();
        xunitComboBox = new javax.swing.JComboBox();
        yunitComboBox = new javax.swing.JComboBox();

        plotTypeLabel.setText("Plot type:");

        gridLinesLabel.setText("Grid lines:");

        plotRangeLabel.setText("Plot range:");

        showErrorsCheckBox.setText("Show errors");

        plotTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Log", "Linear", "XLog", "YLog" }));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${prefs}"), plotTypeComboBox, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        gridLinesButtonGroup.add(gridLinesOnRadioButton);
        gridLinesOnRadioButton.setText("On");

        gridLinesButtonGroup.add(gridLinesOffRadioButton);
        gridLinesOffRadioButton.setText("Off");

        fixedCheckBox.setText("Automatic");

        jPanel1.setVisible(false);

        xminLabel.setText("xmin:");

        yminTextField.setText("jTextField2");

        ymaxTextField.setText("jTextField4");

        xmaxTextField.setText("jTextField3");

        ymaxLabel.setText("ymax:");

        xminTextField.setText("jTextField1");

        yminLabel.setText("ymin:");

        xmaxLabel.setText("xmax:");

        xunitComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        yunitComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(yminLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(yminTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(xminLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(xminTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(ymaxLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ymaxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(xmaxLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(xmaxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(yunitComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(xunitComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(xminLabel)
                    .addComponent(xminTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(xunitComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(xmaxLabel)
                    .addComponent(xmaxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(yminLabel)
                    .addComponent(yminTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yunitComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ymaxLabel)
                    .addComponent(ymaxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(showErrorsCheckBox)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(plotTypeLabel)
                            .addComponent(gridLinesLabel))
                        .addGap(58, 58, 58)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(gridLinesOnRadioButton)
                                .addGap(18, 18, 18)
                                .addComponent(gridLinesOffRadioButton))
                            .addComponent(plotTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(plotRangeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fixedCheckBox))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(plotTypeLabel)
                    .addComponent(plotTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gridLinesLabel)
                    .addComponent(gridLinesOnRadioButton)
                    .addComponent(gridLinesOffRadioButton))
                .addGap(18, 18, 18)
                .addComponent(showErrorsCheckBox)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(plotRangeLabel)
                    .addComponent(fixedCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * set the plot range options visible. Should be done when plot range is
     * fixed.
     */
    private void setPlotRangeOptionsVisible(boolean visible) {
        jPanel1.setVisible(visible);
   }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox fixedCheckBox;
    private javax.swing.ButtonGroup gridLinesButtonGroup;
    private javax.swing.JLabel gridLinesLabel;
    private javax.swing.JRadioButton gridLinesOffRadioButton;
    private javax.swing.JRadioButton gridLinesOnRadioButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel plotRangeLabel;
    private javax.swing.JComboBox plotTypeComboBox;
    private javax.swing.JLabel plotTypeLabel;
    private javax.swing.JCheckBox showErrorsCheckBox;
    private javax.swing.JLabel xmaxLabel;
    private javax.swing.JTextField xmaxTextField;
    private javax.swing.JLabel xminLabel;
    private javax.swing.JTextField xminTextField;
    private javax.swing.JComboBox xunitComboBox;
    private javax.swing.JLabel ymaxLabel;
    private javax.swing.JTextField ymaxTextField;
    private javax.swing.JLabel yminLabel;
    private javax.swing.JTextField yminTextField;
    private javax.swing.JComboBox yunitComboBox;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
