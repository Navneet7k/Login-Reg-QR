package lyf.navneet.in.phpapisamp.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import lyf.navneet.in.phpapisamp.R;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<JSONObject> articles;
    private Context context;

    public DataAdapter(ArrayList<JSONObject> articles, Context context) {
        this.context=context;
        this.articles=articles;}

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, final int i) {

//        viewHolder.webView.getSettings().setJavaScriptEnabled(true);

//        viewHolder.tv_name.setText(articles.get(i).getTitle());
//        final String name=articles.get(i).getTitle();
//
//        Picasso.with(context).load(articles.get(i).getUrlToImage()).into(viewHolder.img);
        viewHolder.tv_name.setText(articles.get(i).optString("name"));
        viewHolder.date_cont.setText(articles.get(i).optString("address"));
        String image=articles.get(i).optString("image");
        String pdf=articles.get(i).optString("pdf");
            Picasso.with(context).load(image).into(viewHolder.img);
//        viewHolder.webView.loadUrl(pdf);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
     //   viewHolder.tv_api_level.setText(android.get(i).getApi());
    }

    private String dateFormat(String date) {
        String dateF="",time="";
        if (date!=null) {
            String[] separated = date.split("T");
            dateF = separated[0]; // this will contain "Fruit"
        }
        return dateF;
    }
    private String timeFormat(String time) {
        String timeF="";
        if (time!=null) {
            String[] separated = time.split("T");
            timeF = separated[1]; // this will contain "Fruit"
            timeF.trim();
        }
        return timeF;
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name,tv_version,time_cont,date_cont;
//        private WebView webView;

        private ImageView img;
        public ViewHolder(View view) {
            super(view);
            img=(ImageView)view.findViewById(R.id.img);
            tv_name = (TextView)view.findViewById(R.id.tv_name);
            date_cont = (TextView)view.findViewById(R.id.date_cont);
            time_cont = (TextView)view.findViewById(R.id.time_cont);
//            webView=(WebView)view.findViewById(R.id.pdf);
//            webView.getSettings().setJavaScriptEnabled(true);
        }
    }

}