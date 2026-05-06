package com.example.waypoint;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class SpotAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Spot> spots;

    public SpotAdapter(Context context, ArrayList<Spot> spots) {
        this.context = context;
        this.spots = spots;
    }

    @Override
    public int getCount() {
        return spots.size();
    }

    @Override
    public Object getItem(int position) {
        return spots.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.spot_item, parent, false);
        }

        Spot spot = (Spot) getItem(position);

        View moodStrip = convertView.findViewById(R.id.view_mood_strip);
        ImageView ivSpotType = convertView.findViewById(R.id.iv_spot_type);
        TextView tvName = convertView.findViewById(R.id.tv_spot_name);
        TextView tvMoodTag = convertView.findViewById(R.id.tv_mood_tag);
        TextView tvCityDate = convertView.findViewById(R.id.tv_city_date);

        tvName.setText(spot.getSpotName());
        tvMoodTag.setText(spot.getMood());
        tvCityDate.setText(spot.getCity() + " · " + spot.getDateAdded());

        int moodColor;
        switch (spot.getMood()) {
            case "Exciting":
                moodColor = ContextCompat.getColor(context, R.color.mood_exciting);
                break;
            case "Nostalgic":
                moodColor = ContextCompat.getColor(context, R.color.mood_nostalgic);
                break;
            case "Dreamy":
                moodColor = ContextCompat.getColor(context, R.color.mood_dreamy);
                break;
            case "Chaotic":
                moodColor = ContextCompat.getColor(context, R.color.mood_chaotic);
                break;
            case "Peaceful":
            default:
                moodColor = ContextCompat.getColor(context, R.color.mood_peaceful);
                break;
        }

        moodStrip.setBackgroundColor(moodColor);
        tvMoodTag.setTextColor(moodColor);

        int iconRes;
        switch (spot.getSpotType()) {
            case "City":
                iconRes = R.drawable.ic_city;
                break;
            case "Nature":
                iconRes = R.drawable.ic_nature;
                break;
            case "Cafe":
                iconRes = R.drawable.ic_cafe;
                break;
            case "Culture":
                iconRes = R.drawable.ic_culture;
                break;
            case "Forest":
                iconRes = R.drawable.ic_forest;
                break;
            case "Beach":
            default:
                iconRes = R.drawable.ic_beach;
                break;
        }
        ivSpotType.setImageResource(iconRes);

        return convertView;
    }
}