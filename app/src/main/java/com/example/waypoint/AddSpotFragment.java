package com.example.waypoint;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddSpotFragment extends Fragment {

    private String selectedType = null;
    private String selectedMood = null;

    private TextInputEditText etName, etCity, etNote;
    private MaterialButton[] typeButtons, moodButtons;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_spot, container, false);

        etName = view.findViewById(R.id.et_name);
        etCity = view.findViewById(R.id.et_city);
        etNote = view.findViewById(R.id.et_note);

        setupTypeButtons(view);
        setupMoodButtons(view);

        view.findViewById(R.id.btnSave).setOnClickListener(v -> saveSpot());

        return view;
    }

    private void setupTypeButtons(View view) {
        typeButtons = new MaterialButton[]{
                view.findViewById(R.id.btnTypeBeach),
                view.findViewById(R.id.btnTypeCity),
                view.findViewById(R.id.btnTypeNature),
                view.findViewById(R.id.btnTypeCafe),
                view.findViewById(R.id.btnTypeCulture),
                view.findViewById(R.id.btnTypeForest)
        };

        String[] types = {"Beach", "City", "Nature", "Cafe", "Culture", "Forest"};

        for (int i = 0; i < typeButtons.length; i++) {
            final int index = i;
            typeButtons[i].setOnClickListener(v -> {
                selectedType = types[index];
                updateButtonStates(typeButtons, typeButtons[index]);
            });
        }
    }

    private void setupMoodButtons(View view) {
        moodButtons = new MaterialButton[]{
                view.findViewById(R.id.btnMoodPeaceful),
                view.findViewById(R.id.btnMoodExciting),
                view.findViewById(R.id.btnMoodNostalgic),
                view.findViewById(R.id.btnMoodDreamy),
                view.findViewById(R.id.btnMoodChaotic)
        };

        String[] moods = {"Peaceful", "Exciting", "Nostalgic", "Dreamy", "Chaotic"};

        for (int i = 0; i < moodButtons.length; i++) {
            final int index = i;
            moodButtons[i].setOnClickListener(v -> {
                selectedMood = moods[index];
                updateButtonStates(moodButtons, moodButtons[index]);
            });
        }
    }

    private void updateButtonStates(MaterialButton[] buttons, MaterialButton activeBtn) {
        for (MaterialButton btn : buttons) {
            if (btn == activeBtn) {
                btn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.amber)));
                btn.setTextColor(ContextCompat.getColor(requireContext(), R.color.navy));
                btn.setStrokeWidth(0);
            } else {
                btn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), android.R.color.transparent)));
                btn.setTextColor(ContextCompat.getColor(requireContext(), R.color.amber));
                btn.setStrokeColor(ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.amber)));
                btn.setStrokeWidth(2);
            }
        }
    }

    private void saveSpot() {
        String name = etName.getText().toString().trim();
        String city = etCity.getText().toString().trim();
        String note = etNote.getText().toString().trim();

        if (name.isEmpty() || city.isEmpty() || note.isEmpty() || selectedType == null || selectedMood == null) {
            Toast.makeText(getContext(), "Please complete all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        String date = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());

        Spot spot = new Spot(name, city, note, selectedType, selectedMood, date);

        MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.getSpots().add(0, spot);

            SharedPreferences prefs = activity.getSharedPreferences("waypoint_prefs", Context.MODE_PRIVATE);
            int level = prefs.getInt("explorerLevel", 0);
            prefs.edit().putInt("explorerLevel", level + 1).apply();

            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainer, new SpotsFragment())
                    .commit();
        }
    }
}