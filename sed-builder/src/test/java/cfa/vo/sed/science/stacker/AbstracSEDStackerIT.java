package cfa.vo.sed.science.stacker;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;

import cfa.vo.interop.SAMPController;
import cfa.vo.interop.SAMPFactory;
import cfa.vo.iris.interop.SedSAMPController;

/**
 * Abstract class for integration testing of SAMP integration. Tests will fail if they are
 * unable to connect to the SAMP hub.
 * 
 */
public abstract class AbstracSEDStackerIT {
    
    private static final Logger logger = Logger.getLogger(AbstracSEDStackerIT.class.getName());
    
    private static final int SAMP_CONN_RETRIES = 3;
    protected static final double EPSILON = 0.00001;

    protected double[] x1;
    protected double[] y1;
    protected double[] yerr1;
    protected double[] x2;
    protected double[] y2;
    protected double[] yerr2;
    protected double[] x3;
    protected double[] y3;
    protected double[] yerr3;

    protected SegmentPayload segment1;
    protected SegmentPayload segment2;
    protected SegmentPayload segment3;

    protected static SAMPController controller;
    
    @BeforeClass
    public static void beforeClass() throws Exception {
        startSamp();
    }
    
    @AfterClass
    public static void afterClass() throws Exception {
        terminate();
    }
    
    @Before
    public void setUp() throws Exception {
        initVariables();
    }
    
    @After
    public void tearDown() {
        terminate();
    }
    
    protected void initVariables() throws Exception {
        x1 = new double[] { 5, 1, 10, 15, 50, 100 };
        y1 = new double[] { 0.5, 0.1, 1.0, 1.5, 5.0, 10.0 };
        yerr1 = new double[] {0.05, 0.01, 0.1, 0.15, 0.5, 1.0};

        x2 = new double[] { 2, 4, 5, 8, 10 };
        y2 = new double[] { 1, 2, 3, 4, 5 };
        yerr2 = new double[] { 0.1, 0.2, 0.3, 0.4, 0.5 };

        x3 = new double[] {0.5, 3.0, 1.5, 5.0, 10.5, 21.0};
        y3 = new double[] {5.0, 7.0, 15.0, 4.5, 13.5, 10.5};
        yerr3 = new double[] {0.5, 0.7, 1.5, 0.45, 1.35, 1.05};

        segment1 = (SegmentPayload) SAMPFactory.get(SegmentPayload.class);
        segment1.setX(x1);
        segment1.setY(y1);
        segment1.setYerr(yerr1);
        segment1.setZ(0.1);
        segment1.setId("Sed1");

        segment2 = (SegmentPayload) SAMPFactory.get(SegmentPayload.class);
        segment2.setX(x2);
        segment2.setY(y2);
        segment2.setYerr(yerr2);
        segment2.setZ(0.2);
        segment2.setId("Sed2");

        segment3 = (SegmentPayload) SAMPFactory.get(SegmentPayload.class);
        segment3.setX(x3);
        segment3.setY(y3);
        segment3.setYerr(yerr3);
        segment3.setZ(0.3);
        segment3.setId("Sed3");
    }
    
    private static void startSamp() throws Exception {
        // Start the SAMP controller
        controller = new SedSAMPController("SEDStacker", "SEDStacker", AbstracSEDStackerIT.class.getResource("/tools_tiny.png")
                .toString());
        controller.setAutoRunHub(false);
        controller.start(false);
        
        // Wait for start
        Thread.sleep(2000);

        int count = 0;
        while (!controller.isConnected()) {
            if (++count > SAMP_CONN_RETRIES) {
                String msg = "Failed to connect to SAMP, failing Unit tests";
                logger.info(msg);
                Assert.fail(msg);
            }
            logger.info("waiting connection");
            Thread.sleep(1000);
        }
    }

    protected static void terminate() {
        controller.stop();
    }
}
