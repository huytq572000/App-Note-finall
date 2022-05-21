package com.example.appnote.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.example.appnote.fragment.FragmentSearch;
import com.example.appnote.fragment.FragmentGhiChu;
import com.example.appnote.fragment.FragmentLoiNhac;
import com.example.appnote.fragment.FragmentThungRac;

public class ViewPagerAdapter extends FragmentPagerAdapter {


    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new FragmentGhiChu();
            case 1: return new FragmentLoiNhac();
            case 2: return new FragmentThungRac();
            case 3: return new FragmentSearch();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
