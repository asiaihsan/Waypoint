package com.example.waypoint;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpotsFragment extends Fragment {

    private TextView tvStats;
    private TextView tvEmpty;
    private ListView lvSpots;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spots, container, false);
        tvStats = view.findViewById(R.id.tvStats);
        tvEmpty = view.findViewById(R.id.tvEmpty);
        lvSpots = view.findViewById(R.id.lvSpots);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            ArrayList<Spot> spots = activity.getSpots();
            SpotAdapter adapter = new SpotAdapter(getContext(), spots);
            lvSpots.setAdapter(adapter);

            if (spots.isEmpty()) {
                tvEmpty.setVisibility(View.VISIBLE);
                tvStats.setText("");
            } else {
                tvEmpty.setVisibility(View.GONE);
                updateStats(spots);
            }

            lvSpots.setOnItemClickListener((parent, view, position, id) -> {
                Spot spot = spots.get(position);
                Intent intent = new Intent(getContext(), PlaceDetailActivity.class);
                intent.putExtra("placeName", spot.getSpotName());
                intent.putExtra("city", spot.getCity());
                intent.putExtra("note", spot.getNote());
                intent.putExtra("placeType", spot.getSpotType());
                intent.putExtra("mood", spot.getMood());
                intent.putExtra("dateAdded", spot.getDateAdded());
                startActivity(intent);
            });
        }
    }

    private void updateStats(ArrayList<Spot> spots) {
        int count = spots.size();
        HashMap<String, Integer> moodCounts = new HashMap<>();
        HashMap<String, Integer> typeCounts = new HashMap<>();

        for (Spot spot : spots) {
            moodCounts.put(spot.getMood(), moodCounts.getOrDefault(spot.getMood(), 0) + 1);
            typeCounts.put(spot.getSpotType(), typeCounts.getOrDefault(spot.getSpotType(), 0) + 1);
        }

        String topMood = getMostCommon(moodCounts);
        String topType = getMostCommon(typeCounts);

        String stats = count + " spots saved · Top mood: " + topMood + " · Favourite type: " + topType;
        tvStats.setText(stats);
    }

    private String getMostCommon(Map<String, Integer> counts) {
        String mostCommon = "";
        int maxCount = -1;
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostCommon = entry.getKey();
            }
        }
        return mostCommon;
    }
}