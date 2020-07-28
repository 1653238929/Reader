package com.example.reader.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class MfrangmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    private FragmentManager fragmentManger;
    public MfrangmentAdapter(FragmentManager fm, List<Fragment> list){
        super(fm);
        this.fragmentManger =fm;
        if(list==null)return;
        this.list=list;
    }
    @Override
    public Fragment getItem(int i) {
        return list==null?null:list.get(i);
    }

    @Override
    public int getCount() {
        return list==null?null:list.size();
    }
}
