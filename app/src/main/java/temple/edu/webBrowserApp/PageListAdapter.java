package temple.edu.webBrowserApp;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PageListAdapter extends BaseAdapter {
    Context context;
    ArrayList<PageViewerFragment> pageViewerFragments;
    public PageListAdapter(Context context, ArrayList<PageViewerFragment> pageViewerFragments){
        this.context = context;
        this.pageViewerFragments = pageViewerFragments;
    }
    @Override
    public int getCount() {
        if (pageViewerFragments != null) {
            return pageViewerFragments.size();
        } else{
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return pageViewerFragments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null){
            textView = new TextView(context);
        } else{
            textView = (TextView) convertView;
        }
        textView.setText(pageViewerFragments.get(position).getTitle());
        textView.setGravity(Gravity.CENTER);

        return textView;

    }
}
