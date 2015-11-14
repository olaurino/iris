package cfa.vo.iris.test.unit;

import cfa.vo.iris.IrisComponent;
import cfa.vo.iris.test.ComponentLoader;
import org.junit.*;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
public abstract class AbstractComponentSuite {
    private ComponentLoader loader;

    /**
     * This rule allows the component loader to be initialized only once for the whole suite.
     * This works around the fact that @BeforeClass and @AfterClass methods need to be static.
     */
    @Rule
    public ExternalResource resource = new ExternalResource() {
        @Override
        protected void before() {
            loader = new ComponentLoader(new Class[]{getComponentClass()});
            loader.init();
        }
        @Override
        protected void after() {
            loader.shutdown();
        }
    };

    /**
     * Return the class of the component to load as part of this
     * @return
     */
    protected abstract Class<?extends IrisComponent> getComponentClass();
}
