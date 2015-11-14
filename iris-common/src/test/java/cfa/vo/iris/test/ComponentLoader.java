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
package cfa.vo.iris.test;

import cfa.vo.iris.IWorkspace;
import cfa.vo.iris.IrisApplication;
import cfa.vo.iris.IrisComponent;
import cfa.vo.iris.test.unit.ApplicationStub;
import cfa.vo.iris.test.unit.StubWorkspace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Test utility class for loading components in an isolated application and workspace environment
 */
public class ComponentLoader {
    private List<IrisComponent> components = new ArrayList<>();
    private List<String> failures = new ArrayList<>();

    public ComponentLoader(Collection<Class<? extends IrisComponent>> componentClasses) {
        for (Class<? extends IrisComponent> compClass : componentClasses) {
            try {
                components.add(compClass.newInstance());
            } catch (Exception ex) {
                String message = "Could not construct component " + compClass.getName();
                System.err.println(message);
                Logger.getLogger(ComponentLoader.class.getName()).log(Level.SEVERE, message, ex);
                failures.add(compClass.getName());
            }
        }
    }

    /**
     * Return an unmodifiable list of failure strings
     * @return an unmodifiable list of strings
     */
    public List<String> getFailures() {
        return Collections.unmodifiableList(failures);
    }

    /**
     * Return an unmodifiable list of components in this ComponentLoader
     * @return an unmodifiable list of IrisComponents
     */
    public List<IrisComponent> getComponents() {
        return Collections.unmodifiableList(components);
    }

    /**
     * initialize components in this ComponentLoader. A single instance of IrisApplication
     * and IWorkspace is shared among the initialized components.
     */
    public void init() {
        IrisApplication app = new ApplicationStub();
        IWorkspace ws = new StubWorkspace();

        for (IrisComponent comp: components) {
            try {
                comp.initCli(app);
                comp.init(app, ws);
            } catch (Exception ex) {
                String message = "cannot initialize component " + comp.getName();
                System.err.println(message);
                Logger.getLogger(ComponentLoader.class.getName()).log(Level.SEVERE, message, ex);
                failures.add(comp.getName());
            }
        }
    }

    /**
     * Shutdown components in this ComponentLoader
     */
    public void shutdown() {
        for (IrisComponent comp: components) {
            try {
                comp.shutdown();
            } catch (Exception ex) {
                String message = "cannot cleanly shutdown component " + comp.getName();
                System.err.println(message);
                Logger.getLogger(ComponentLoader.class.getName()).log(Level.SEVERE, message, ex);
                failures.add(comp.getName());
            }
        }
    }


}
