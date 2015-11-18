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
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cfa.vo.iris;

import java.util.List;
import org.astrogrid.samp.client.MessageHandler;

/**
 *
 * Interface implemented by Iris components. By implementing this interface the components
 * allow the framework to retrieve the information needed to run and initialize them.
 *
 * @author olaurino
 */
public interface IrisComponentInterface extends IComponent {
    /**
     * The component can register any number of SAMP message listeners by providing a list of them.
     *
     * @return A list of the SAMP message listeners that have to be registered to the SAMP hub.
     */
    List<MessageHandler> getSampHandlers();

    /**
     * This method is invoked to initialize the component. If the component has to launch windows, frames or
     * background services, this is the right method to do so. Otherwise the component will be called only if a callback
     * is invoked.
     *
     * @param init A reference to the application initialization object
     * @param workspace A reference to the application's workspace
     */
    void init(IrisInitialization init, IWorkspace workspace);

    /**
     * Initialize the Command Line Application interface
     * @param init Reference to the enclosing application initialization object
     */
    void initCli(IrisInitialization init);

}
