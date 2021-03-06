/**
 * Copyright (C) 2012 Smithsonian Astrophysical Observatory
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
 * IrisImporterApp.java
 */
package cfa.vo.iris;

import cfa.vo.iris.test.TestBuilder;
import cfa.vo.iris.test.TestLogger;
import cfa.vo.iris.test.TestSSAServer;
import cfa.vo.iris.test.vizier.VizierClient;
import cfa.vo.sed.builder.SedBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.UIManager;

/**
 * The main class of the application.
 */
public class Iris extends AbstractIrisApplication {

    @Override
    public URL getSAMPIcon() {
        return getClass().getResource("/iris_button_tiny.png");
    }

    @Override
    public JDialog getAboutBox() {
        return new About(false);
    }

    public static void main(String[] args) {
        launch(Iris.class, args);
    }

    @Override
    public String getName() {
        return "Iris";
    }

    @Override
    public String getDescription() {
        return "The VAO SED Analysis Tool";
    }

    @Override
    public URL getDesktopIcon() {
        return getClass().getResource("/Iris_logo.png");
    }

    @Override
    public URL getHelpURL() {
        try {
            String url = System.getenv("IRIS_DOC");
            return new URL(url);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Iris.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void setProperties(List<String> properties) {
        Logger.getLogger("").setLevel(Level.OFF);
        for (String prop : properties) {
            if (prop.equals("test")) {
                getComponentLoader().loadComponent(TestBuilder.class);
                getComponentLoader().loadComponent(TestLogger.class);
                getComponentLoader().loadComponent(TestSSAServer.class);
            }

            if (prop.equals("lnf")) {
                try {
                    System.out.println("Setting cross platform Look and Feel...");
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                } catch (Exception ex) {
                    System.out.println("Failed to set the Look and Feel");
                    Logger.getLogger(Iris.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (prop.equals("vizier")) {
                getComponentLoader().loadComponent(VizierClient.class);
            }


            if (prop.equals("debug")) {
                Logger.getLogger("").setLevel(Level.ALL);
            }
            if (prop.equals("ssa")) {
                SedBuilder.SSA = true;
            }
        }

    }

    @Override
    protected URL getComponentsFileLocation() {
        return Iris.class.getResource("/components");
    }
}
