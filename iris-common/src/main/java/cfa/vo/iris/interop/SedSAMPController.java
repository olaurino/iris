/*
 *  Copyright 2011 Smithsonian Astrophysical Observatory.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
package cfa.vo.iris.interop;

import cfa.vo.interop.SAMPController;
import cfa.vo.interop.SAMPFactory;
import cfa.vo.interop.SAMPMessage;
import cfa.vo.iris.sed.ExtSed;
import cfa.vo.sedlib.Sed;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.astrogrid.samp.client.SampException;

/**
 * Extension of SAMPController that can handle SedMessages.
 *
 * @author olaurino
 */
public class SedSAMPController extends SAMPController {

    /**
     * This constructor just calls the parent constructor.
     * 
     * @param name
     * @param description
     * @param iconUrl
     */
    public SedSAMPController(String name, String description, String iconUrl) {
        super(name, description, iconUrl);
    }

    /**
     * This convenience method, when provided with a Sed and an Id to it, builds a new
     * SedMessage, creates a new resource to be served by the internal HTTPServer and
     * sends the SedMessage to the hub.
     *
     * @param sed
     * @param sedId
     * @throws SampException
     */
    public void sendSedMessage(Sed sed, String sedId) throws SampException {
        try {
            String filename = URLEncoder.encode(sedId + ".xml", "UTF-8");
            URL url = addResource(filename, new SedServerResource(sed));
            sendMessage(new SedMessage(sedId, url));
        } catch (UnsupportedEncodingException ex) {
            throw new SampException(ex);
        }
    }

    public void sendSedMessage(ExtSed sed) throws SampException {
        try {

            for (int i = 1; i < sed.getNumberOfSegments()+1; i++) {

                String n = i==1 ? "" : "Segment"+i;
                String id = sed.getId()+n;
                Sed s = new Sed();

                s.addSegment(sed.getSegment(i-1));

                VaoMessage msg = (VaoMessage) SAMPFactory.get(VaoMessage.class);
                msg.setName(id);
                msg.setTableId(id);
                msg.getVaoPayload().setMessageType("sed");
                msg.getVaoPayload().setSenderId("iris");
                String filename = URLEncoder.encode(id + ".vot", "UTF-8");
                URL url = addResource(filename, new SedServerResource(s));
                msg.setUrl(url.toString());

                SAMPMessage message = SAMPFactory.createMessage("table.load.votable", msg, VaoMessage.class);
                sendMessage(message);
            }

        } catch (Exception ex) {
            Logger.getLogger(SedSAMPController.class.getName()).log(Level.SEVERE, null, ex);
            throw new SampException(ex);
        }

    }
}
