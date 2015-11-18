package cfa.vo.iris;

import java.util.List;

public interface IComponent {
    /**
     * This method is invoked to initialize the component. If the component has to launch windows, frames or
     * background services, this is the right method to do so. Otherwise the component will be called only if a callback
     * is invoked.
     *
     * @param init A reference to the application initialization object
     * @param workspace A reference to the application's workspace
     */
    void init(IAppInitialization init, IWorkspace workspace);

    /**
     * Return the name of this component. This name might be listed in a widget along with the other registered components.
     * @return The component's name as a String.
     */
    String getName();

    /**
     * Get e description for this component. The description might be listed in a widget along with the other
     * registered components.
     *
     * @return The component's description as a String.
     */
    String getDescription();

    /**
     * Get a command line interface object for this component.
     * @return A CLI object
     */
    ICommandLineInterface getCli();

    /**
     * Initialize the Command Line Application interface
     * @param init Reference to the enclosing application initialization object
     */
    void initCli(IAppInitialization init);

    /**
     * The component can contribute menu items and desktop buttons to the enclosing GUI applications
     * by providing a list of MenuItems.
     *
     * @return A list of the menu items this component will contribute to the application.
     */
    List<IMenuItem> getMenus();

    /**
     * Callback invoked when the component is shutdown
     */
    void shutdown();
}
