package cfa.vo.iris;

import cfa.vo.interop.SAMPController;
import cfa.vo.interop.SimpleSAMPMessage;
import cfa.vo.iris.interop.SedSAMPController;
import cfa.vo.iris.sed.ExtSed;
import org.astrogrid.samp.Message;
import org.astrogrid.samp.client.SampException;

public class SampManager implements ISampManager {
    private SedSAMPController sampController;

    public SampManager(String name, String description, String iconPath) {
        sampController = new SedSAMPController(name, description, iconPath);
    }

    @Override
    public void sendSedMessage(ExtSed sed) throws SampException {
        sampController.sendSedMessage(sed);
    }

    @Override
    public void sendSampMessage(Message msg) throws SampException {
        sampController.sendMessage(new SimpleSAMPMessage(msg));
    }

    @Override
    public SAMPController getSAMPController() {
        return sampController;
    }

    @Override
    public void start(String resourceRoot, boolean gui) throws Exception {
        sampController.startWithResourceServer(resourceRoot, gui);
    }
}
