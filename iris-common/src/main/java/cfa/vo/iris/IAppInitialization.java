package cfa.vo.iris;

import java.io.File;
import java.net.URL;

public interface IAppInitialization {
    /**
     * Get a pointer to the directory that contains all the configuration files for this component.
     *
     * @return A File instance for the configuration directory.
     */
    File getConfigurationDir();

    URL getHelpURL();

    void init() throws Exception;
}
