package temple.edu.webBrowserApp;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class PageViewerFragment extends Fragment {

    private WebView webView;
    private addLinkListener listener;


    public PageViewerFragment() {
        // Required empty public constructor
    }


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
    }

    public void searchPressed(String url){
        webView.loadUrl(url);
    }

    public void backPressed(){
        webView.goBack();
    }

    public void forwardPressed(){
        webView.goForward();
    }

    public interface addLinkListener{
        public void addLink(String link);
    }

}