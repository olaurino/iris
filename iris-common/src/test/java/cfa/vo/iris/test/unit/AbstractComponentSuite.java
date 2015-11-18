package cfa.vo.iris.test.unit;

import cfa.vo.iris.IrisComponentInterface;
import cfa.vo.iris.test.ComponentLoader;
import org.junit.*;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.util.ArrayList;
import java.util.List;

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
            List<Class<? extends IrisComponentInterface>> comps = new ArrayList<>();
            comps.add(getComponentClass());
            loader = new ComponentLoader(comps);
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
    protected abstract Class<?extends IrisComponentInterface> getComponentClass();
}
