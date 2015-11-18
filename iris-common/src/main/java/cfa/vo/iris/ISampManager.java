package cfa.vo.iris;

import cfa.vo.interop.SAMPController;
import cfa.vo.iris.sed.ExtSed;
import org.astrogrid.samp.Message;
import org.astrogrid.samp.client.SampException;

public interface ISampManager {
    void sendSedMessage(ExtSed sed) throws SampException;

    void sendSampMessage(Message msg) throws SampException;

    SAMPController getSAMPController();

    void start(String resourceRoot, boolean gui) throws Exception;
}
