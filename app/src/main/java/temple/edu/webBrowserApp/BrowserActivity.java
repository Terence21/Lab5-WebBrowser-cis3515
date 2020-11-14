package temple.edu.webBrowserApp;

import android.content.res.Configuration;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class BrowserActivity extends AppCompatActivity implements PageListFragment.changePageListener, PageControlFragment.WebMenuListener, PageViewerFragment.addLinkListener, BrowserControlFragment.TabListener , PagerFragment.ViewerListener {

    // get webView in ViewPager... what is being returned by getPager()??

    FragmentManager fm;
    PageControlFragment pageControlFragment;
    BrowserControlFragment browserControlFragment;
    PagerFragment pagerFragment;
    PageListFragment pageListFragment;
    ArrayList<PageViewerFragment> pageViewerFragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("BrowserActivity");

        // handle title

        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        fm = getSupportFragmentManager();
        if (getSupportFragmentManager().findFragmentById(R.id.page_display) == null && getSupportFragmentManager().findFragmentById(R.id.page_control) == null) {
            pageViewerFragments = new ArrayList<>();
            pageViewerFragments.add(new PageViewerFragment());
            pageControlFragment = new PageControlFragment();
            pagerFragment = new PagerFragment();
            browserControlFragment = new BrowserControlFragment();


            FragmentTransaction ft = fm.beginTransaction();
           // fm.beginTransaction()
            ft
                    .add(R.id.browser_control, browserControlFragment)
                    .add(R.id.page_control, pageControlFragment)
                    .add(R.id.page_display, pagerFragment);
                    if (isLandscape){
                        pageListFragment = new PageListFragment();
                        ft.add(R.id.page_list, pageListFragment);


                    }
                    ft.commit();
        }
        else{
            pageViewerFragments = savedInstanceState.getParcelableArrayList("viewer_list");
            pageControlFragment = (PageControlFragment) getSupportFragmentManager().findFragmentById(R.id.page_control);
            browserControlFragment = (BrowserControlFragment) getSupportFragmentManager().findFragmentById(R.id.browser_control);
            pagerFragment = (PagerFragment) getSupportFragmentManager().findFragmentById(R.id.page_display);

            if (isLandscape){
                pageListFragment = new PageListFragment();
                FragmentTransaction ft = fm.beginTransaction();
                ft
                        .add(R.id.page_list,pageListFragment)
                        .commit();
            } else{
                pageListFragment = (PageListFragment) fm.findFragmentById(R.id.page_list);
            }
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("viewer_list", pageViewerFragments);
    }

    @Override
    protected void onRestoreInstanceState( Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        pageViewerFragments = savedInstanceState.getParcelableArrayList("viewer_list");
    }

    @Override
    public void createNewTab() {
        pageViewerFragments.add(new PageViewerFragment());
        pagerFragment.notifyDataSetChange();
        pagerFragment.changePage(pageViewerFragments.size()-1);
        if (pageListFragment != null){
            pageListFragment.notifyDataSetChange();
        }

    }

    @Override
    public void searchPressed(String url) {
//        pageViewerFragment.searchPressed(url);
       // pagerFragment.getPager().searchPressed(url);
        url = correctURL(url);

        pageViewerFragments.get(pagerFragment.currnetItem()).searchPressed(url);
        pageControlFragment.updateMembers(url);
        pagerFragment.notifyDataSetChange();
        if (pageListFragment != null){
            pageListFragment.notifyDataSetChange();
        }
        setTitle(pageViewerFragments.get(pagerFragment.currnetItem()).getTitle());

    }

    @Override
    public void backPressed() {
        //pageViewerFragment.backPressed();
        //pagerFragment.getPager().backPressed();
        pageViewerFragments.get(pagerFragment.currnetItem()).backPressed();
        setTitle(pageViewerFragments.get(pagerFragment.currnetItem()).getTitle());
        pagerFragment.notifyDataSetChange();
        if (pageListFragment != null){
            pageListFragment.notifyDataSetChange();
        }

    }

    @Override
    public void forwardPressed() {
        //pageViewerFragment.forwardPressed();
       // pagerFragment.getPager().forwardPressed();
        pageViewerFragments.get(pagerFragment.currnetItem()).forwardPressed();
        setTitle(pageViewerFragments.get(pagerFragment.currnetItem()).getTitle());
        pagerFragment.notifyDataSetChange();
        if (pageListFragment != null){
            pageListFragment.notifyDataSetChange();
        }
    }

    @Override
    public void addLink(String link) {
       pageControlFragment.updateMembers(link);
        if (pageListFragment != null){
            pageListFragment.notifyDataSetChange();
        }

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

    @Override
    public void changePage(int pageNum) {
        pagerFragment.changePage(pageNum);
        pageListFragment.notifyDataSetChange();
    }
}
