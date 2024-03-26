package ru.mixinspace.r_en_audio.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import ru.mixinspace.r_en_audio.R;

public class TopBarFragment extends Fragment {

    public TopBarFragment() {
        super(R.layout.top_bar_fragment);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout backButton = view.findViewById(R.id.back_button);
        LinearLayout menuButton = view.findViewById(R.id.menu_button);

        backButton.setOnClickListener(v -> getActivity().finish());

        menuButton.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Создано для Итогого проекта. \nМарьясов Михаил \nМАОУ \"Гимназия №5\" им. Л.В.Усыниной \n2024 г.");
            builder.setTitle("О приложении");
            builder.setCancelable(true);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        });
    }
}
