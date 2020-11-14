package temple.edu.webBrowserApp;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class PageViewerFragment extends Fragment implements Parcelable {

    private WebView webView;
    private addLinkListener listener;

    public Context context;

    public PageViewerFragment() {
        // Required empty public constructor
    }


    protected PageViewerFragment(Parcel in) {
        webView.restoreState(in.readBundle(getClass().getClassLoader()));
    }

    public static final Creator<PageViewerFragment> CREATOR = new Creator<PageViewerFragment>() {
        @Override
        public PageViewerFragment createFromParcel(Parcel in) {
            return new PageViewerFragment(in);
        }

        @Override
        public PageViewerFragment[] newArray(int size) {
            return new PageViewerFragment[size];
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("pageViewer", "new create ");


    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_page_viewer, container, false);


        webView = (WebView) view.findViewById(R.id._webViewer);
        Log.i("webView", "onCreateView: " + webView.getTitle());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                listener.addLink(url);
            }
        });

        if (savedInstanceState != null){
            webView.restoreState(savedInstanceState);
        }


        return view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        webView.saveState(outState);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try {
            listener = (addLinkListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(e.getLocalizedMessage());
        }
        this.context = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }

    public void searchPressed(String url){
        webView.loadUrl(url);
    }

    public String getTitle(){
        return webView.getTitle();
    }

    public void backPressed(){
        webView.goBack();
    }

    public void forwardPressed(){
        webView.goForward();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();
        webView.saveState(bundle);
        dest.writeBundle(bundle);
    }

    public interface addLinkListener{
        public void addLink(String link);
    }

}