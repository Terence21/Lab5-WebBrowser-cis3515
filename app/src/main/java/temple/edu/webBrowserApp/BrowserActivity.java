package temple.edu.webBrowserApp;

import android.graphics.pdf.PdfDocument;
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
            this.setTitle(R.string.BrowserActivity);

            if (savedInstanceState == null) {
                if (urls == null) {
                    urls = new ArrayList<>();
                    position = -1;
                }



                Fragment pageControl = PageControlFragment.newInstance(this, position, urls);
                Fragment pageView = new PageViewerFragment();

                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction()
                        .add(R.id.page_control, pageControl, getResources().getString(R.string.control))
                        .addToBackStack(null)
                        .add(R.id.page_viewer, pageView, getResources().getString(R.string.viewer))
                        .addToBackStack(null)
                        .commit();
            }
        }


    @Override
    public void onSaveInstanceState(Bundle outstate){
        super.onSaveInstanceState(outstate);
        outstate.putStringArrayList(getResources().getString(R.string.urls), urls);
        outstate.putInt(getResources().getString(R.string.position), position);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        this.urls = savedInstanceState.getStringArrayList(getResources().getString(R.string.urls));
        this.position = savedInstanceState.getInt(getResources().getString(R.string.position));
        if (urls == null){
            urls = new ArrayList<>();
            position = -1;
            
        }

       // PageControlFragment pageControl = PageControlFragment.newInstance(position, urls);
        PageControlFragment pageControl = (PageControlFragment) getSupportFragmentManager().findFragmentByTag(getResources().getString(R.string.control));
        pageControl.updateMembers(urls,position);
        PageViewerFragment pageView = (PageViewerFragment) getSupportFragmentManager().findFragmentByTag(getResources().getString(R.string.viewer));

        if (position >= 0) {
            pageView.defineUrl(urls.get(position));
        }


        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .detach(pageControl)
                .detach(pageView)
                .attach(pageControl)
                .attach(pageView)
                .commit();
    }

    @Override
    public void searchPressed(int position, ArrayList <String> urls) {
        this.urls = urls;
        this.position = position;

       // PageViewerFragment pvf = new PageViewerFragment();
        PageViewerFragment pvf = (PageViewerFragment) getSupportFragmentManager().findFragmentByTag(getResources().getString(R.string.viewer));
        pvf.defineUrl(urls.get(position));
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .detach(pvf)
                .attach(pvf)
                .commit();

    }

    @Override
    public void backPressed(int position, ArrayList <String> urls) {
        this.urls = urls;
        this.position = position;
        PageViewerFragment pvf = (PageViewerFragment) getSupportFragmentManager().findFragmentByTag(getResources().getString(R.string.viewer));
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
        PageViewerFragment pvf = (PageViewerFragment) getSupportFragmentManager().findFragmentByTag(getResources().getString(R.string.viewer));
        pvf.defineUrl(urls.get(position));
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .detach(pvf)
                .attach(pvf)
                .commit();
    }

    @Override
    public void addLink(String link) {

        //urls.add(correctURL(link));
        urls.add(link);
        position++;
        PageControlFragment pvf = (PageControlFragment) getSupportFragmentManager().findFragmentByTag(getResources().getString(R.string.control));
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

        if (!badURL.endsWith(".com") && !badURL.endsWith(".org") && !badURL.endsWith(".edu") && !badURL.endsWith(".gov")){
            correctURL += ".com";
        }
        return correctURL;
    }
}
