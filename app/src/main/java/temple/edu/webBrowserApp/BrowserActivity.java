package temple.edu.webBrowserApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.WebMenuListener, PageViewerFragment.addLinkListener, BrowserControlFragment.TabListener , PagerFragment.ViewerListener {

    // get webView in ViewPager... what is being returned by getPager()??

    FragmentManager fm;
    PageControlFragment pageControlFragment;
    BrowserControlFragment browserControlFragment;
    PagerFragment pagerFragment;

    ArrayList<PageViewerFragment> pageViewerFragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("BrowserActivity");

        // handle title


        if (getSupportFragmentManager().findFragmentById(R.id.page_view) == null && getSupportFragmentManager().findFragmentById(R.id.page_control) == null) {
            pageViewerFragments = new ArrayList<>();
            pageViewerFragments.add(new PageViewerFragment());
            pageControlFragment = new PageControlFragment();
            pagerFragment = new PagerFragment();
            browserControlFragment = new BrowserControlFragment();

            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction()
                    .add(R.id.browser_control, browserControlFragment)
                    .add(R.id.page_control, pageControlFragment)
                    .add(R.id.page_display, pagerFragment)
                    .commit();
        }
        else{
            pageViewerFragments = savedInstanceState.getParcelable("viewer_list");
            pageControlFragment = (PageControlFragment) getSupportFragmentManager().findFragmentById(R.id.page_control);
            browserControlFragment = (BrowserControlFragment) getSupportFragmentManager().findFragmentById(R.id.browser_control);
            pagerFragment = (PagerFragment) getSupportFragmentManager().findFragmentById(R.id.page_display);
        }


    }




    @Override
    public void createNewTab() {
        pageViewerFragments.add(new PageViewerFragment());
        pagerFragment.notifyDataSetChange();
        pagerFragment.changePage(pageViewerFragments.size()-1);

    }

    @Override
    public void searchPressed(String url) {
//        pageViewerFragment.searchPressed(url);
       // pagerFragment.getPager().searchPressed(url);
        url = correctURL(url);
        pageViewerFragments.get(pagerFragment.currnetItem()).searchPressed(url);
        pageControlFragment.updateMembers(url);
        pagerFragment.notifyDataSetChange();

    }

    @Override
    public void backPressed() {
        //pageViewerFragment.backPressed();
        //pagerFragment.getPager().backPressed();
        pageViewerFragments.get(pagerFragment.currnetItem()).backPressed();
        pagerFragment.notifyDataSetChange();

    }

    @Override
    public void forwardPressed() {
        //pageViewerFragment.forwardPressed();
       // pagerFragment.getPager().forwardPressed();
        pageViewerFragments.get(pagerFragment.currnetItem()).forwardPressed();
        pagerFragment.notifyDataSetChange();
    }

    @Override
    public void addLink(String link) {
       pageControlFragment.updateMembers(link);

    }

    public static String correctURL(String badURL) {
        String correctURL = "";
        if (!badURL.startsWith("http://") && !badURL.startsWith("https://")) {
            correctURL = "https://";
        }
        if (!badURL.contains("www.")) {
            if (badURL.contains("https://")) {
                badURL = badURL.replace("https://", "");
            } else if (badURL.contains("http://")) {
                badURL = badURL.replace("http://", "");
            }

            correctURL = "https://www.";
        }
        correctURL += badURL;

        if (!badURL.endsWith(".com") && !badURL.endsWith(".org") && !badURL.endsWith(".edu") && !badURL.endsWith(".gov")) {
            correctURL += ".com";
        }
        return correctURL;
    }

    @Override
    public ArrayList<PageViewerFragment> getViewer() {
        return pageViewerFragments;
    }
}
