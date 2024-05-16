package org.maplibre.android.plugins.offline;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.maplibre.android.plugins.offline.model.OfflineDownloadOptions;
import org.maplibre.android.plugins.offline.offline.OfflinePlugin;
import org.maplibre.android.plugins.testapp.R;
import org.maplibre.android.plugins.testapp.activity.offline.OfflineDownloadActivity;

import java.util.List;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@Ignore
public class OfflinePluginTest {

    @Rule
    public ActivityTestRule<OfflineDownloadActivity> rule = new ActivityTestRule<>(OfflineDownloadActivity.class);

    private OfflinePlugin plugin;

    @Before
    public void setUp() {
        plugin = OfflinePlugin.getInstance(rule.getActivity());
    }

    @Test
    public void sanity() {
        assertThat(plugin, notNullValue());
    }

    @Test
    public void getActiveDownloads_doesReflectCorrectListSize() {
        // Ensure our current list is empty
        List<OfflineDownloadOptions> options = plugin.getActiveDownloads();
        assertThat(options.size(), equalTo(0));

        // Initiate the offline download and ensure the active download list has one item
        onView(withId(R.id.fabStartDownload)).perform(click());
        options = plugin.getActiveDownloads();
        assertThat(options.size(), equalTo(1));
    }


}
