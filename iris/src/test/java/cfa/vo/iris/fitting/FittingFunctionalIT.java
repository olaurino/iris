package cfa.vo.iris.fitting;

import cfa.vo.iris.gui.widgets.ModelViewerPanel;
import cfa.vo.iris.test.IrisAppResource;
import cfa.vo.iris.test.unit.AbstractUISpecTest;
import cfa.vo.iris.test.unit.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.uispec4j.*;
import org.uispec4j.assertion.UISpecAssert;
import org.uispec4j.interception.PopupMenuInterceptor;
import org.uispec4j.interception.WindowHandler;
import org.uispec4j.interception.WindowInterceptor;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FittingFunctionalIT extends AbstractUISpecTest {
    @Rule
    public IrisAppResource appResource = new IrisAppResource(false, true);

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private String examplesUrlString;
    private String templateLibUrlString;
    private String templateUrlString;
    private String functionUrlString;

    private Window window;
    private Desktop desktop;
    private Window fittingView;
    private Tree modelsTree;
    private Tree availableTree;
    private TextBox modelExpression;

    private final String TABLE="tablemodel";
    private final String FUNCTION="usermodel";
    private final String TEMPLATE="template";

    @Before
    public void setUp() throws Exception {
        window = appResource.getAdapter().getMainWindow();
        desktop = window.getDesktop();

        // Set up tests/examples directory.
        examplesUrlString = tempFolder.getRoot().getParentFile().getAbsolutePath()+"/examples";
        templateLibUrlString = examplesUrlString+"/sed_templates.dat";
        functionUrlString = examplesUrlString+"/mypowlaw.py";
        templateUrlString = examplesUrlString+"/sed_temp_data.dat";

        Path templatePath = Paths.get(templateLibUrlString);
        Charset charset = StandardCharsets.UTF_8;

        String content = new String(Files.readAllBytes(templatePath), charset);
        content = content.replaceAll("<YOUR DIRECTORY PATH HERE>", examplesUrlString);
        Files.write(templatePath, content.getBytes(charset));
    }

    @Test
    public void testFittingThread() throws Exception {
        installModels();
        loadSed();
        setupModelExpression();
        fit();
        fitCustomModel();
    }

    private void installModels() throws Exception {
        // Menu is built dynamically, so it might not be ready when the test runs
        TestUtils.invokeWithRetry(50, 100, new Runnable() {
            @Override
            public void run() {
                window.getMenuBar().getMenu("Tools").getSubMenu("Fitting Tool").getSubMenu("Custom Models Manager").click();
            }
        });
        Window modelsManager = desktop.getWindow("Custom Fit Models Manager");

        modelsManager.getRadioButton("Template Library").click();
        TextBox nextField = modelsManager.getTextBox("jTextField1");
        nextField.setText(templateLibUrlString);
        nextField = modelsManager.getTextBox("jTextField2");
        nextField.setText("idx,refer");
        nextField = modelsManager.getTextBox("jTextField3");
        nextField.setText("0.0,5000");
        nextField = modelsManager.getTextBox("jTextField4");
        nextField.setText("-0.5,5000");
        nextField = modelsManager.getTextBox("jTextField5");
        nextField.setText("0.0,5000");
        nextField = modelsManager.getTextBox("jTextField6");
        nextField.setText("False,False");
        nextField = modelsManager.getTextBox("jTextField7");
        nextField.setText("test_template");

        Button installButton = modelsManager.getButton("install");
        installButton.click();

        modelsManager.getRadioButton("Python Function").click();
        nextField = modelsManager.getTextBox("jTextField1");
        nextField.setText(functionUrlString);
        nextField = modelsManager.getTextBox("jTextField2");
        nextField.setText("ref,ampl,index");
        nextField = modelsManager.getTextBox("jTextField3");
        nextField.setText("5000,1.0,-0.5");
        nextField = modelsManager.getTextBox("jTextField4");
        nextField.setText("5000,1e-38,-1.0");
        nextField = modelsManager.getTextBox("jTextField5");
        nextField.setText("5000,3e38,1.0");
        nextField = modelsManager.getTextBox("jTextField6");
        nextField.setText("True,False,False");
        nextField = modelsManager.getTextBox("jTextField8");
        nextField.setText("mypowlaw");
        nextField = modelsManager.getTextBox("jTextField7");
        nextField.setText("test_function");

        installButton.click();

        modelsManager.getRadioButton("Table").click();
        nextField = modelsManager.getTextBox("jTextField1");
        nextField.setText(templateUrlString);
        nextField = modelsManager.getTextBox("jTextField2");
        nextField.setText("ampl");
        nextField = modelsManager.getTextBox("jTextField3");
        nextField.setText("1.0");
        nextField = modelsManager.getTextBox("jTextField4");
        nextField.setText("1e-38");
        nextField = modelsManager.getTextBox("jTextField5");
        nextField.setText("3e38");
        nextField = modelsManager.getTextBox("jTextField6");
        nextField.setText("False");
        nextField = modelsManager.getTextBox("jTextField7");
        nextField.setText("test_table");

        installButton.click();

        Tree modelsTree = modelsManager.getTree("jTree1");
        modelsTree.contains("templates/test_template").check();
        modelsTree.contains("functions/test_function").check();
        modelsTree.contains("tables/test_table").check();

    }

    private void loadSed() throws Exception {
        window.getMenuBar().getMenu("File").getSubMenu("Load File").click();
        Window loader = desktop.getWindow("Load an input File");
        loader.getRadioButton("Location on Disk").click();
        loader.getInputTextBox("diskTextBox").setText(examplesUrlString+"/3c273.xml");
        loader.getComboBox().select("VOTable");
        loader.getButton("Load Spectrum/SED").click();

        Window builder = desktop.getWindow("SED Builder");
        builder.getListBox().click(0);
        builder.getTable().contentEquals(new String[][]{{"3C 273", "187.28, 2.0524", "NASA/IPAC Extragalactic Database (NED)", "474"}}).check();
    }

    private void setupModelExpression() throws Exception {
        window.getMenuBar().getMenu("Tools").getSubMenu("Fitting Tool").getSubMenu("Fitting Tool").click();
        fittingView = desktop.getWindow("Fitting Tool");
        availableTree = fittingView.getTree("availableTree");
        modelsTree = fittingView.getTree("modelsTree");
        modelExpression = fittingView.getTextBox("modelExpressionField");
        final TextBox status = fittingView.getTextBox("statusField");

        modelExpression.textEquals("No Model").check();
        status.textEquals("Invalid Model Expression").check();

        availableTree.contains("User Model Components/tables/test_table").check();
        availableTree.contains("User Model Components/functions/test_function").check();
        availableTree.contains("User Model Components/templates/test_template").check();
        availableTree.contains("Preset Model Components/powerlaw").check();

        availableTree.doubleClick("User Model Components/tables/test_table");
        modelsTree.contains(TABLE+".m1").check();
        availableTree.doubleClick("User Model Components/functions/test_function");
        modelsTree.contains(FUNCTION+".m2").check();
        availableTree.doubleClick("User Model Components/templates/test_template");
        modelsTree.contains(TEMPLATE+".m3").check();
        availableTree.doubleClick("Preset Model Components/powerlaw");
        modelsTree.contains("powerlaw.m4").check();

        modelExpression.textEquals("m1 + m2 + m3 + m4").check();
        status.textIsEmpty().check();

        modelExpression.setText("foo bar");
        status.textEquals("Invalid Model Expression").check();

        modelExpression.setText("m1 + m2 + m3 + m4");
        status.textIsEmpty().check();

        removeModel(TABLE+".m1");

        UISpecAssert.not(modelsTree.contains("test_table.m1"));

        modelExpression.textEquals("m1 + m2 + m3 + m4").check();
        status.textEquals("Invalid Model Expression").check();

        modelExpression.setText("m2 + m3 + m4");
        status.textIsEmpty().check();

        removeModel(TEMPLATE+".m3");
        removeModel(FUNCTION+".m2");
        removeModel("powerlaw.m4");
        status.textEquals("Invalid Model Expression").check();

        modelExpression.setText("");

        availableTree.doubleClick("Preset Model Components/powerlaw");
        modelsTree.contains("powerlaw.m5").check();
        modelExpression.setText("m5");
        status.textEquals("").check();
    }

    private void fit() {
        TextBox name = fittingView.getTextBox("Par Name");
        TextBox val = fittingView.getTextBox("Par Val");
        TextBox min = fittingView.getTextBox("Par Min");
        TextBox max = fittingView.getTextBox("Par Max");
        CheckBox frozen = fittingView.getCheckBox("Par Frozen");

        UISpecAssert.not(name.isEditable());
        val.isEditable().check();
        min.isEditable().check();
        max.isEditable().check();
        frozen.isEnabled().check();

        modelsTree.expandAll();

        // Try with click
        modelsTree.click("powerlaw.m5/m5.refer");
        name.textEquals("m5.refer").check();
        val.textEquals("5000.0").check();
        min.textEquals("1.1754943508222875E-38").check();
        max.textEquals("3.4028234663852886E38").check();
        frozen.isSelected().check();

        // Try with select, simulating a selection that does not involve clicks
        modelsTree.select("powerlaw.m5/m5.ampl");
        name.textEquals("m5.ampl").check();
        val.textEquals("1.0").check();
        min.textEquals("1.1754943508222875E-38").check();
        max.textEquals("3.4028234663852886E38").check();
        UISpecAssert.not(frozen.isSelected());

        modelsTree.select("powerlaw.m5/m5.index");
        name.textEquals("m5.index").check();
        val.textEquals("-0.5").check();
        min.textEquals("-10.0").check();
        max.textEquals("10.0").check();
        UISpecAssert.not(frozen.isSelected());

        val.isEnabled().check();
        min.isEnabled().check();
        max.isEnabled().check();
        frozen.isEnabled().check();

        fittingView.getComboBox("optimizationCombo").select("LevenbergMarquardt");
        fittingView.getComboBox("statisticCombo").select("LeastSquares");

        fittingView.getButton("Fit").click();

        UISpecAssert.waitUntil(UISpecAssert.not(val.textEquals("-0.5")), 1000);

        val.textContains("-0.467").check();
        modelsTree.select("powerlaw.m5/m5.ampl");
        val.textContains("1.852").check();

        TextBox np = fittingView.getInputTextBox("Number of Points");
        np.textEquals("474").check();
        TextBox dof = fittingView.getInputTextBox("Degrees of Freedom");
        dof.textEquals("472").check();

        WindowInterceptor
                .init(fittingView.getButton("Compute").triggerClick())
                .process(new WindowHandler() {
                    @Override
                    public Trigger process(Window window) throws Exception {
                        window.titleEquals("SEDException").check();
                        window.getTextBox("OptionPane.label").textEquals("cannot estimate confidence limits with LeastSq").check();
                        return window.getButton().triggerClick();
                    }
                })
                .run();

        fittingView.getComboBox("statisticCombo").select("Chi2");
        fittingView.getButton("Fit").click();

        UISpecAssert.waitUntil(np.textEquals("363"), 1000);

        fittingView.getButton("Compute").click();

        Table confTable = fittingView.getTable();
        String[] columns = {"Parameter", "Lower Limit", "Upper Limit"};
        UISpecAssert.waitUntil(UISpecAssert.not(confTable.isEmpty()), 1000);

        Assert.assertEquals(confTable.getContentAt(0, 0), "m5.ampl");
        Assert.assertEquals(confTable.getContentAt(1, 0), "m5.index");
        confTable.columnCountEquals(3);
        Assert.assertArrayEquals(columns, confTable.getHeader().getColumnNames());
    }

    private void fitCustomModel() {
        modelsTree = fittingView.getTree("modelsTree");
        removeModel("powerlaw.m5");
        availableTree.doubleClick("User Model Components/tables/test_table");
        availableTree.doubleClick("User Model Components/functions/test_function");
        availableTree.doubleClick("User Model Components/templates/test_template");

        modelExpression.setText("m6 + m7 + m8");
        fittingView.getButton("Fit").click();

        assertFitSucceeded();
    }

    private void assertFitSucceeded() {
        UISpecAssert.waitUntil(fittingView.getTextBox("status").textEquals(ModelViewerPanel.FIT_SUCCEEDED), 1000);

    }

    private void removeModel(String m) {
        PopupMenuInterceptor.run(
                modelsTree.triggerRightClick(m))
                .getSubMenu("Remove")
                .click();
    }
}