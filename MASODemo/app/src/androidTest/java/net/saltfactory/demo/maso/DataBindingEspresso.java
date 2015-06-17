package net.saltfactory.demo.maso;

import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.After;
import org.junit.Before;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by saltfactory on 6/17/15.
 */
//@RunWith(AndroidJUnit4.class)
public class DataBindingEspresso extends ActivityInstrumentationTestCase2<MainActivity> {
    private static final String STRING_TO_BE_TYPED = "홍";
    private MainActivity mainActivity;

    public DataBindingEspresso(Class<MainActivity> activityClass) {
        super(activityClass);
    }

    public DataBindingEspresso(){
        super(MainActivity.class);
    }
//    public DataBindingEspresso(Class<MainActivity> activityClass) {
//        super(activityClass);
//    }
//
//    private MainActivity mActivity;
//
//    public DataBindingEspresso() {
//        super(MainActivity.class);
//    }

//    @Rule
//    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);



//    @Before
//    public void setActivity() {
//        mainActivity = mActivityRule.getActivity();
//    }

    @Before
    protected void setUp() throws Exception {
        super.setUp();

        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mainActivity = getActivity();

    }

    @After
    protected void tearDown() throws Exception {
        super.tearDown();
    }


    public void testChangeLastName_Activity() {
        onView(withId(R.id.lastName)).check(matches(withText(STRING_TO_BE_TYPED)));
//        onView(allOf(withId(R.id.lastName), withText("Sign-in")));
    }

}
