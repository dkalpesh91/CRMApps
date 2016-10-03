package com.sfamobile.dahlia.sfamobile.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.sfamobile.dahlia.sfamobile.R;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 30-Sep-16.
 */
public class ContactDocumentAdapter extends BaseAdapter {

    Context mContext;
    HashMap<String, String> imageId = new HashMap<>();
    private String[] mKeys;

    public ContactDocumentAdapter(Context mContext, HashMap<String, String> imageId) {
        this.imageId = imageId;
        this.mContext = mContext;
        mKeys = imageId.keySet().toArray(new String[imageId.size()]);

    }

    @Override
    public int getCount() {
        return imageId.size();
    }

    @Override
    public Object getItem(int position) {
        return imageId.get(mKeys[position]);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String Value = getItem(position).toString();
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.card_image_layout, null);

            ImageView imageView = (ImageView) grid.findViewById(R.id.image_name);

            imageView.setImageURI(Uri.parse(Value));

        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}
