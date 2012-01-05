/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cfa.vo.iris.desktop;

import cfa.vo.iris.IWorkspace;
import cfa.vo.iris.sed.ISedManager;
import cfa.vo.iris.sed.SedlibSedManager;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;

/**
 *
 * @author olaurino
 */
public class IrisWorkspace implements IWorkspace {

    private IrisDesktop mainview;

    private ISedManager sedManager;

    public IrisWorkspace(IrisDesktop desktop) {
        this.mainview = desktop;
        sedManager = new SedlibSedManager();
    }

    @Override
    public ISedManager getSedManager() {
        return sedManager;
    }

    @Override
    public JFrame getRootFrame() {
        return mainview;
    }

    @Override
    public JDesktopPane getDesktop() {
        return mainview.getDesktopPane();
    }

    private static final JFileChooser fileChooser = new JFileChooser();

    /**
     * Get the value of fileChooser
     *
     * @return the value of fileChooser
     */
    @Override
    public JFileChooser getFileChooser() {
        return fileChooser;
    }

    @Override
    public void addFrame(JInternalFrame frame) {
        this.getDesktop().add(frame);
    }

    @Override
    public void removeFrame(JInternalFrame frame) {
        this.getDesktop().remove(frame);
    }

}