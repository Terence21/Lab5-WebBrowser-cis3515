package temple.edu.webBrowserApp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Stack;


public class PageViewerFragment extends Fragment {

    private WebView webView;
    private String input_url;




    Handler content = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            webView.loadUrl(input_url);
            return false;
        }
    });

    public PageViewerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("pageViewer", "new create ");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_page_viewer, container, false);

        webView = (WebView) view.findViewById(R.id._webViewer);
        Log.i("message", "run: message sent " + input_url);
        Thread thread = new Thread(){
            @Override
            public void run(){
                Message message = Message.obtain();
                message.obj = input_url;
                content.sendMessage(message);
            }
        };
        thread.start();

        return view;
    }



    public void defineUrl(String url) {
            this.input_url = url;
    }

}