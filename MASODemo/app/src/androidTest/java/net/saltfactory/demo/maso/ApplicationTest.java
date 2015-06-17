package net.saltfactory.demo.maso;

import android.app.Application;
import android.test.ApplicationTestCase;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public ApplicationTest (Class<Application> applicationClass) {
        super(applicationClass);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        createApplication();
    }

    public void testMyApp() throws Exception {
        // Retrieves a file in the res/xml folder named test.xml
//        XmlPullParser xml = getContext().getResources().getXml(R.xml.test);
//        assertNull(xml);
    }
}

