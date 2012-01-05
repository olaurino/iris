/**
 * Copyright (C) Smithsonian Astrophysical Observatory
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SedImporterMainView.java
 *
 * Created on May 6, 2011, 3:53:29 PM
 */

package cfa.vo.iris.desktop;

import cfa.vo.iris.gui.NarrowOptionPane;
import cfa.vo.interop.SAMPConnectionListener;
import cfa.vo.iris.IMenuItem;
import cfa.vo.iris.IrisAbstractApplication;
import cfa.vo.iris.IrisComponent;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import org.astrogrid.samp.client.MessageHandler;
import org.jdesktop.application.Action;

/**
 *
 * @author olaurino
 */
public class IrisDesktop extends JFrame {

    private List<DesktopButton> buttons = new ArrayList();
    
    private List<IrisComponent> components;

//    private About aboutBox = new About(IrisDesktop.getInstance(), false);
    private JDialog aboutBox;

    IrisAbstractApplication app;

    /** Creates new form SedImporterMainView */
    public IrisDesktop(IrisAbstractApplication app) throws Exception {
        initComponents();

        this.app = app;

        aboutBox = app.getAboutBox();

        components = app.getComponents();

        setTitle(app.getName());

        desktopPane.setDesktopManager(new BoundDesktopManager(desktopPane));
        desktopPane.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
        

        if(app.isSampEnabled()) {
            for(IrisComponent component : components)
                for(MessageHandler handler : component.getSampHandlers())
                    app.addMessageHandler(handler);

            app.addConnectionListener(new SampStatusListener());
            
        }
        
        sampIcon.setVisible(app.isSampEnabled());

        int c=0;
        for(IrisComponent component : components) {
            int cf = 0;
            JMenu cMenu = null;
            for(IMenuItem item : component.getMenus()) {
                IrisMenuItem i = new IrisMenuItem(item);
                if(i.getMenu().equals("File"))
                    fileMenu.add(i, c+cf++);
                else {
                    if(cMenu==null)
                        cMenu = new JMenu(component.getName());
                    cMenu.add(i);
                }
                if(item.isOnDesktop()) {
                    DesktopButton b = new DesktopButton(item);
                    buttons.add(b);
                    desktopPane.add(b, javax.swing.JLayeredPane.DEFAULT_LAYER);
                }
            }
            if(cf>0)
                fileMenu.add(new JSeparator(), (c++)+(cf++));
            if(cMenu!=null)
                toolsMenu.add(cMenu);
            paintButtons();

        }

        this.setLocationRelativeTo(null);
        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);

        this.repaint();

//        int[] bo = getVaoBounds();
//        jLabel2.setBounds(bo[0], bo[1], bo[2], bo[3]);
        
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(IrisDesktop.this,

                        "Do you really want to close " + getTitle() + "?",

                        "Close Confirmation",

                        JOptionPane.YES_NO_OPTION,

                        JOptionPane.QUESTION_MESSAGE,

                        null, null, null);

                if (confirm == 0) {

                System.exit(0);

              }
            }
        });
        if (app.MAC_OS_X) {
            try {
                // Generate and register the OSXAdapter, passing it a hash of all the methods we wish to
                // use as delegates for various com.apple.eawt.ApplicationListener methods
                OSXAdapter.setQuitHandler(this, getClass().getDeclaredMethod("quit", (Class[])null));
                OSXAdapter.setAboutHandler(this, getClass().getDeclaredMethod("about", (Class[])null));
//                OSXAdapter.setPreferencesHandler(this, getClass().getDeclaredMethod("preferences", (Class[])null));
//                OSXAdapter.setFileHandler(this, getClass().getDeclaredMethod("loadImageFile", new Class[] { String.class }));
            } catch (Exception e) {
                System.err.println("Error while loading the OSXAdapter:");
                e.printStackTrace();
            }
        }

        
    }

    @Override
    public void repaint(int i1, int i2, int i3, int i4) {
        super.repaint(i1, i2, i3, i4);
        int[] b = getVaoBounds();
        jLabel2.setBounds(b[0], b[1], b[2], b[3]);
        paintButtons();
        desktopPane.setLayer(jLabel2, 0);
    }

    private int[] getVaoBounds() {
        int xb = (this.getWidth()-jLabel2.getWidth())/2;
        int yb = (this.getHeight()-jLabel2.getHeight())/2;
        int xf = xb+jLabel2.getWidth()/2;
        int yf = yb+jLabel2.getHeight()/2;
        return new int[]{xb, yb, xf, yf};
    }


    private void paintButtons() {
        int width = this.getWidth();
        int xl = 100;
        int yl = 150;
        int baseX = 20-xl;
        int baseY = 20;
        for(DesktopButton b : buttons) {
            baseX = baseX + xl;
            if(width>100) {
                if(baseX+xl>width) {
                    baseY = baseY+yl;
                    baseX = 20;
                }
            } else {
                baseY = baseY+170;
            }


            b.setBounds(baseX, baseY, baseX + xl, baseY + yl);
            desktopPane.setLayer(b, 1);

        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        desktopPane = new javax.swing.JDesktopPane();
        sampIcon = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        exitMenuItem = new javax.swing.JMenuItem();
        toolsMenu = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        desktopPane.setBackground(new java.awt.Color(0, 102, 102));
        desktopPane.setForeground(new java.awt.Color(255, 255, 255));
        desktopPane.setAutoscrolls(true);
        desktopPane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        desktopPane.setName("sedDesktop"); // NOI18N
        desktopPane.setPreferredSize(new java.awt.Dimension(1024, 768));
        desktopPane.setSize(new java.awt.Dimension(1036, 693));

        sampIcon.setForeground(new java.awt.Color(255, 255, 255));
        sampIcon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sampIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/connect_no.png")));
        sampIcon.setText("SAMP status: disconnected");
        sampIcon.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        sampIcon.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sampIcon.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        sampIcon.setBounds(50, 490, 210, 120);
        desktopPane.add(sampIcon, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/vao.png")));
        jLabel2.setBounds(350, 340, 330, 150);
        desktopPane.add(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jToolBar1.setRollover(true);

        fileMenu.setText("File");

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance().getContext().getActionMap(IrisDesktop.class, this);
        exitMenuItem.setAction(actionMap.get("exit")); // NOI18N
        exitMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/process_stop.png")));
        fileMenu.add(exitMenuItem);

        jMenuBar1.add(fileMenu);

        toolsMenu.setText("Tools");
        jMenuBar1.add(toolsMenu);

        jMenu1.setText("Interop");

        jCheckBoxMenuItem1.setText("Run Hub Automatically");
        jCheckBoxMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/redo.png")));

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, org.jdesktop.beansbinding.ELProperty.create("${sampAutoHub}"), jCheckBoxMenuItem1, org.jdesktop.beansbinding.BeanProperty.create("selected"));
        bindingGroup.addBinding(binding);

        jMenu1.add(jCheckBoxMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Help");
        jMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showAbout(evt);
            }
        });

        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iris_button_tiny.png")));
        jMenuItem5.setText("About SedImporter");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showAbout(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(desktopPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1036, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, desktopPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE)
        );

        getAccessibleContext().setAccessibleName("");

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void showLink(URL url) {
        try {
            java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
            desktop.browse(url.toURI());
        } catch (Exception ex) {
            try {
                HelpBrowser browser = new HelpBrowser(url);
                desktopPane.add(browser);
                browser.show();
            } catch (Exception ex2) {
                JEditorPane jta = new JEditorPane();
                jta.setBackground(NarrowOptionPane.getRootFrame().getBackground());
                jta.setContentType("text/html");
                jta.setText("<html><body>SedImporter couldn't open your default browser. You can use this link directly: <br/>"
                                        + url+"</body></html>");
                jta.setEditable(false);

                NarrowOptionPane.showMessageDialog(null,
                    jta,
                    "Desktop communication error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void showAbout(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showAbout
        aboutBox.setLocation((int)desktopPane.getWidth()/2-125, (int)desktopPane.getHeight()/2-110);
        aboutBox.setVisible(true);
    }//GEN-LAST:event_showAbout

//    public static IrisDesktop getInstance() {
//        return MainViewHolder.INSTANCE;
//    }



    public JDesktopPane getDesktopPane() {
        return desktopPane;
    }

//    /**
//    * @param args the command line arguments
//    */
//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                IrisDesktop.this.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel sampIcon;
    private javax.swing.JMenu toolsMenu;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    void setComponents(List<IrisComponent> components) {
        this.components = components;
    }
    // End of variables declaration

//    private static class MainViewHolder {
//        private static final IrisDesktop INSTANCE = new IrisDesktop();
//    }

    

    public boolean quit() {
        int confirm = NarrowOptionPane.showOptionDialog(this,

                        "Do you really want to close " + getTitle() + "?",

                        "Close Confirmation",

                        JOptionPane.YES_NO_OPTION,

                        JOptionPane.QUESTION_MESSAGE,

                        null, null, null);
        return (confirm == NarrowOptionPane.YES_OPTION);
    }

    public void about() {
        showAbout(null);
    }

    private boolean sampConnected = false;

    /**
     * Get the value of sampConnected
     *
     * @return the value of sampConnected
     */
    public boolean isSampConnected() {
        return sampConnected;
    }

    private static final Icon sampNo = new ImageIcon(IrisDesktop.class.getResource("/connect_no.png"));
    private static final Icon sampYes = new ImageIcon(IrisDesktop.class.getResource("/connect_established.png"));

    /**
     * Set the value of sampConnected
     *
     * @param sampConnected new value of sampConnected
     */
    public void setSampConnected(boolean sampConnected) {
        if(sampConnected!=this.sampConnected) {
            this.sampConnected = sampConnected;
            sampIcon.setIcon(sampConnected ? sampYes : sampNo);
            sampIcon.setText( sampConnected ? "SAMP status: connected" : "SAMP status: disconnected" );
            sampIcon.setForeground(sampConnected? new Color(0, 200, 0) : Color.WHITE);
        }
    }

    private boolean sampAutoHub = true;
    public static final String PROP_SAMPAUTOHUB = "sampAutoHub";

    /**
     * Get the value of sampAutoHub
     *
     * @return the value of sampAutoHub
     */
    public boolean isSampAutoHub() {
        return sampAutoHub;
    }

    /**
     * Set the value of sampAutoHub
     *
     * @param sampAutoHub new value of sampAutoHub
     */
    public void setSampAutoHub(boolean sampAutoHub) {
        boolean oldSampAutoHub = this.sampAutoHub;
        this.sampAutoHub = sampAutoHub;
        app.setAutoRunHub(sampAutoHub);
        firePropertyChange(PROP_SAMPAUTOHUB, oldSampAutoHub, sampAutoHub);
    }

    

    

    private class SampStatusListener implements SAMPConnectionListener {

        @Override
        public void run(boolean status) {
            setSampConnected(status);
        }

    }

    

    @Action
    public void exit() {
        if (quit())
            app.exitApp();
    }

}