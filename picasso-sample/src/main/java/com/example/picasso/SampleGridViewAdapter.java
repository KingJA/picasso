package com.example.picasso;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.ContentValues.TAG;
import static android.widget.ImageView.ScaleType.CENTER_CROP;
import static java.lang.System.load;

final class SampleGridViewAdapter extends BaseAdapter {
    private final Context context;
    private final List<String> urls = new ArrayList<>();

    public SampleGridViewAdapter(Context context) {
        this.context = context;

        // Ensure we get a different ordering of images on each run.
        Collections.addAll(urls, Data.URLS);
        Collections.shuffle(urls);

        // Triple up the list.
        ArrayList<String> copy = new ArrayList<>(urls);
        urls.addAll(copy);
        urls.addAll(copy);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SquaredImageView view = (SquaredImageView) convertView;
        if (view == null) {
            view = new SquaredImageView(context);
            view.setScaleType(CENTER_CROP);
        }

        // Get the image URL for the current position.
        String url = getItem(position);

        // Trigger the download of the URL asynchronously into the image view.
        Picasso.with(context) //
                .load(url) //
                .placeholder(R.drawable.placeholder) //
                .error(R.drawable.error) //
                .fit() //
                .tag(context) //
                .into(view, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e(TAG, "onError: "+e.getMessage() );
                    }
                });

        return view;
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public String getItem(int position) {
        return urls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
