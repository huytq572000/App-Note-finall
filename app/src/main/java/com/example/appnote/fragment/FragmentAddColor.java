package com.example.appnote.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appnote.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FragmentAddColor extends BottomSheetDialogFragment{
    private AddColorListener addColorListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewDialog =inflater.inflate(R.layout.fragment_add_color,container,false);
        return viewDialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final View view1 = view.findViewById(R.id.view_color1);
        final View view2 = view.findViewById(R.id.view_color2);
        final View view3 = view.findViewById(R.id.view_color3);
        final View view4 = view.findViewById(R.id.view_color4);
        final View view5= view.findViewById(R.id.view_color5);
        final View view6 = view.findViewById(R.id.view_color6);
        final View view7 = view.findViewById(R.id.view_color7);

        view1.setOnClickListener(v1 -> {
            if(addColorListener != null) {
                addColorListener.onColorClick(getResources().getColor(R.color.white));
            }
        });
        view2.setOnClickListener(v2 ->{
            if(addColorListener != null) {
                addColorListener.onColorClick(getResources().getColor(R.color.red));
            }
        });
        view3.setOnClickListener(v3 ->{
            if(addColorListener != null) {
                addColorListener.onColorClick(getResources().getColor(R.color.vang));
            }
        });
        view4.setOnClickListener(v4 ->{
            if(addColorListener != null) {
                addColorListener.onColorClick(getResources().getColor(R.color.cam));
            }
        });
        view5.setOnClickListener(v5 ->{
            if(addColorListener != null) {
                addColorListener.onColorClick(getResources().getColor(R.color.teal_700));
            }
        });
        view6.setOnClickListener(v6 ->{
            if(addColorListener != null) {
                addColorListener.onColorClick(getResources().getColor(R.color.xanhla));
            }
        });
        view7.setOnClickListener(v7 ->{
            if(addColorListener != null) {
                addColorListener.onColorClick(getResources().getColor(R.color.purple_200));
            }
        });
    }

    public void setAddColorListener(AddColorListener addColorListener) {
        this.addColorListener = addColorListener;
    }

    public interface AddColorListener{
       void onColorClick(int x);
    }
}
