package cfa.vo.iris;

import cfa.vo.iris.sed.ExtSed;
import org.astrogrid.samp.Message;
import org.astrogrid.samp.client.SampException;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IrisInitialization implements ISampAppInitialization, IAppInitialization {
    private static final String NAME = "Iris";
    private static final String DESCRIPTION = "The VAO/SAO SED Analysis Tool";
    private static final String ICON_PATH = IrisInitialization.class.getResource("/iris_button_tiny.png").toString();
    private static final boolean WITH_GUI = true;
    private static final String RESOURCE_ROOT = "sedImporter/";

    private ISampManager sampManager;
    private boolean isSampEnabled = !System.getProperty("samp", "true").toLowerCase().equals("false");;
    private File configurationDir = new File(System.getProperty("user.home") + "/.vao/iris/");
    private boolean isOSX = System.getProperty("os.name").toLowerCase().startsWith("mac os x");

    private URL helpUrl;

    private Logger logger = Logger.getLogger(IrisInitialization.class.getName());

    public IrisInitialization() {
        String url = null;
        try {
            url = System.getenv("IRIS_DOC");
            this.helpUrl =  new URL(url);
        } catch (MalformedURLException ex) {
            System.err.println("Invalid URL: "+ url);
            logger.log(Level.SEVERE, null, ex);
        }

        if (isSampEnabled) {
            sampManager = new SampManager(NAME, DESCRIPTION, ICON_PATH);
            try {
                sampManager.start(RESOURCE_ROOT, WITH_GUI);
            } catch (Exception ex) {
                System.err.println("SAMP Error. Disabling SAMP support.");
                System.err.println("Error message: " + ex.getMessage());
                logger.log(Level.SEVERE, null, ex);
                isSampEnabled = false;
            }
        }
    }

    @Override
    public void init() throws Exception {
        if (!configurationDir.exists()) {
            configurationDir.mkdirs();
        }
    }

    @Override
    public boolean isSampEnabled() {
        return isSampEnabled;
    }

    @Override
    public ISampManager getSampManager() {
        if (!isSampEnabled) {
            throw new IllegalStateException("This application is not SAMP-Enabled");
        }
        return sampManager;
    }

    @Override
    public File getConfigurationDir() {
        return configurationDir;
    }

    @Override
    public URL getHelpURL() {
        return helpUrl;
    }

    public boolean isOSX() {
        return isOSX;
    }
}
