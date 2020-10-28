package temple.edu.webBrowserApp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.WebMenuListener {

    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment pageControl = PageControlFragment.newInstance("url");
        Fragment pageView = PageViewerFragment.newInstance("test", "test");
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.page_control,pageControl)
                .add(R.id.page_viewer, pageView)
                .commit();
    }

    @Override
    public void buttonPressed(String url) {
        PageViewerFragment fragment = new PageViewerFragment();
        fragment.defineUrl(url);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id._webViewer, fragment)
                .addToBackStack("add")
                .commit();

    }
}
/*
TODO:
    1. create the back and forward buttons to handle going to a previous or next page
    2. ** you need to implement a storage/data structure to hold the previous websites and append to the textview (url)
    3. solve for clicking links and updating the editText with the link clicked
    4. handle device orientation
    5. handle poor url
 */