package com.vjsm.sports.adszworld;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;


public class Y_Mix_Videos_CustomAdapter extends RecyclerView.Adapter<Y_Mix_Videos_CustomAdapter.MyViewHolder> {
        ArrayList<YUsers> personNames = new ArrayList<>();
            private Mix_Videos mListener;
        Context context;
    private YouTubeThumbnailLoader youTubeThumbnailLoader;


    public interface OnItemClickListener {
    void onItemClick(int position);
}

    public void setOnItemClickListener(Mix_Videos listener) {
        mListener =  listener;
    }

    public Y_Mix_Videos_CustomAdapter(ArrayList<YUsers> personNames, Context context) {

        this.personNames = personNames;
        this.context = context;
    }


    @Override
    public Y_Mix_Videos_CustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.yrow, parent, false);
        Y_Mix_Videos_CustomAdapter.MyViewHolder vh = new Y_Mix_Videos_CustomAdapter.MyViewHolder(v); // pass the view to View Holder

        return vh;
    }


    @Override
    public void onBindViewHolder(final Y_Mix_Videos_CustomAdapter.MyViewHolder holder, final int position) {
       // holder.name.setText(personNames.get(position).getName());
     //   holder.Description.setText(personNames.get(position).getDescripition());
       // holder.sdate.setText("தேதி: "+personNames.get(position).getStartdate());
        // holder.madtdate.setText(personNames.get(position).getMadtdate());
        //  holder.mantdate.setText(personNames.get(position).getMantdate());
            //holder.viewss.setText(personNames.get(position).getViews());




holder.Description.setText(personNames.get(position).getDec());
        holder.Description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ss=new Intent(context,Video_Playing.class);
                ss.putExtra("video",personNames.get(position).getVideoUrl());
                context.startActivity(ss);
            }
        });

       // Toast.makeText(context,personNames.get(position).getVideoUrl(),Toast.LENGTH_LONG).show();
    //    long interval = 5000 * 1000;
      //  String title="https://www.googleapis.com/youtube/v3/videos?key="+YoutubeAPIConfig.getYoutbeApi()+"&part=snippet&id="+personNames.get(position).getVideoUrl()+"";
        String urls= "http://img.youtube.com/vi/"+personNames.get(position).getVideoUrl()+"/mqdefault.jpg";



        String url = "https://img.youtube.com/vi/"+personNames.get(position).getVideoUrl()+"/0.jpg";
        Glide.with(context).load(url).into(holder.imagev);

        holder.imagev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ss=new Intent(context,Video_Playing.class);
                ss.putExtra("video",personNames.get(position).getVideoUrl());
                context.startActivity(ss);
            }
        });

        AdRequest adRequest=new AdRequest.Builder().build();
        holder.banneradview.loadAd(adRequest);





  //  holder.store.setOnClickListener(new View.OnClickListener() {
         //   @Override
         //   public void onClick(View v) {
            //   Toast.makeText(context,"Downloading",Toast.LENGTH_LONG).show();
            //    Intent s=new Intent(context,downlaodpage.class);
           //     s.putExtra("videourl",personNames.get(position).getVideoUrl());
          //      context.startActivity(s);
         //   }
     //   });
    //holder.duration.setText(durartion);
        //Log.d("videourl",personNames.get(position).getVideoUrl());


}


    @Override
    public int getItemCount() {
        return personNames.size();
    }

    public void filterList(ArrayList<YUsers> names) {
        this.personNames = names;
        notifyDataSetChanged();
    }
public class MyViewHolder extends RecyclerView.ViewHolder {
        AdView banneradview;
    YouTubeThumbnailView youTubeThumbnailView;
    TextView name, place, district, viewss, madtdate, mantdate, phone,Description,duration;
    ImageView imagev;
    ProgressBar progressBar;
    RelativeLayout relativeLayout;
    YouTubeThumbnailView thumbnailView;

    View mview;
    YouTubeThumbnailView.OnInitializedListener onInitializedListener;
    Button store;
 private DownloadManager downloadManager;
 WebView videoviews;
     VideoView videoView;
    public MyViewHolder(final View itemView) {
        super(itemView);
        mview = itemView;
        progressBar=(ProgressBar)mview.findViewById(R.id.progressBar);

     Description = (TextView) mview.findViewById(R.id.titlee);
        imagev=(ImageView)mview.findViewById(R.id.imagethump);
        MobileAds.initialize(context,"ca-app-pub-3940256099942544~3347511713");
        banneradview=(AdView)mview.findViewById(R.id.adView);




    }


}
}
