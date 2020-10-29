package temple.edu.webBrowserApp;

import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Stack;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.WebMenuListener, PageViewerFragment.addLinkListener {


    ArrayList <String> urls;
    int position;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            if (savedInstanceState == null) {
                Log.i("create", "onCreate: created");
                if (urls == null) {
                    urls = new ArrayList<>();
                    position = -1;


                    Fragment pageControl = PageControlFragment.newInstance(position, urls);
                    Fragment pageView = new PageViewerFragment();

                    FragmentManager fm = getSupportFragmentManager();
                    fm.beginTransaction()
                            .add(R.id.page_control, pageControl, "control")
                            .addToBackStack(null)
                            .add(R.id.page_viewer, pageView, "viewer")
                            .addToBackStack(null)
                            .commit();
                }
            }
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
        if (urls == null){
            urls = new ArrayList<>();
            position = -1;
        }

        PageControlFragment pageControl = PageControlFragment.newInstance(position, urls);
        pageControl.updateMembers(urls,position);
        PageViewerFragment pageView = new PageViewerFragment();

        pageView.defineUrl(urls.get(position));
        Log.i("urls", "onRestoreInstanceState: " + urls.get(position));

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.page_control,pageControl, "control")
                .addToBackStack(null)
                .replace(R.id.page_viewer, pageView, "viewer")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void searchPressed(int position, ArrayList <String> urls) {
        this.urls = urls;
        this.position = position;

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

    @Override
    public void addLink(String link) {
        /*while (position != urls.size()-1){
            urls.remove(urls.size()-1);
        }*/
        urls.add(correctURL(link));
        position++;
        PageControlFragment pvf = (PageControlFragment) getSupportFragmentManager().findFragmentByTag("control");
        pvf.updateMembers(urls, position);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .detach(pvf)
                .attach(pvf)
                .commit();

    }

    public static String correctURL(String badURL){
        String correctURL = "";
        if (!badURL.startsWith("http://") && !badURL.startsWith("https://")){
            correctURL = "https://";
        }
        if (!badURL.contains("www.")){
            if (badURL.contains("https://")) {
                badURL = badURL.replace("https://", "");
            } else if (badURL.contains("http://")){
                badURL = badURL.replace("http://", "");
            }

            correctURL = "https://www.";
        }
        correctURL += badURL;

        if (!badURL.endsWith(".com")){
            correctURL += ".com";
        }
        return correctURL;
    }
}
