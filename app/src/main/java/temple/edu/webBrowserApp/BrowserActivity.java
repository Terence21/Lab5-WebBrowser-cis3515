package temple.edu.webBrowserApp;

import android.util.Log;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Stack;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.WebMenuListener {

    ArrayList <String> urls;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (urls == null){
            urls = new ArrayList<>();
            position = -1;
        }

        Fragment pageControl = PageControlFragment.newInstance(position, urls);
        Fragment pageView = new PageViewerFragment();
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.page_control,pageControl)
                .add(R.id.page_viewer, pageView, "viewer")
                .commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outstate){
        super.onSaveInstanceState(outstate);
        outstate.putStringArrayList("urls", urls);
        outstate.putInt("position", position);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        this.urls = savedInstanceState.getStringArrayList("urls");
        this.position = savedInstanceState.getInt("position");
    }

    @Override
    public void searchPressed(int position, ArrayList <String> urls) {
        this.urls = urls;
        this.position = position;
        /*PageViewerFragment pvf = (PageViewerFragment) getSupportFragmentManager().findFragmentByTag("viewer");
        pvf.defineUrl(urls.get(position));
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .detach(pvf)
                .attach(pvf)
                .commit();*/
        PageViewerFragment pvf = new PageViewerFragment();
        pvf.defineUrl(urls.get(position));
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.page_viewer, pvf)
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void backPressed(int position, ArrayList <String> urls) {
        this.urls = urls;
        this.position = position;
        PageViewerFragment pvf = (PageViewerFragment) getSupportFragmentManager().findFragmentByTag("viewer");
        pvf.defineUrl(urls.get(position));
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .detach(pvf)
                .attach(pvf)
                .commit();
    }

    @Override
    public void forwardPressed(int position, ArrayList <String> urls) {
        this.urls = urls;
        this.position = position;
        PageViewerFragment pvf = (PageViewerFragment) getSupportFragmentManager().findFragmentByTag("viewer");
        pvf.defineUrl(urls.get(position));
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .detach(pvf)
                .attach(pvf)
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