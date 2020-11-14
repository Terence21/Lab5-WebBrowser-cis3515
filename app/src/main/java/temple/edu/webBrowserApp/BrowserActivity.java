package temple.edu.webBrowserApp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.WebMenuListener, PageViewerFragment.addLinkListener {


    FragmentManager fm;
    PageControlFragment pageControlFragment;
    PageViewerFragment pageViewerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // handle title
        fm = getSupportFragmentManager();

        Fragment tmpFragment;

        if ((tmpFragment = fm.findFragmentById(R.id.page_control)) instanceof  PageControlFragment){
            pageControlFragment = (PageControlFragment) tmpFragment;
        }
        else {
            pageControlFragment = new PageControlFragment();
            fm.beginTransaction()
                    .add(R.id.page_control, pageControlFragment)
                    .commit();
        }

        if ((tmpFragment = fm.findFragmentById(R.id.page_view)) instanceof PageViewerFragment){
            pageViewerFragment = (PageViewerFragment) tmpFragment;
        } else{
            pageViewerFragment = new PageViewerFragment();
            fm.beginTransaction()
                    .add(R.id.page_display, pageViewerFragment)
                    .commit();
        }
    }


    @Override
    public void searchPressed(String url) {
        pageViewerFragment.searchPressed(url);

    }

    @Override
    public void backPressed() {
        pageViewerFragment.backPressed();
    }

    @Override
    public void forwardPressed() {
        pageViewerFragment.forwardPressed();
    }

    @Override
    public void addLink(String link) {
        pageControlFragment.updateMembers(link);

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
