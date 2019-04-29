package com.techtopus.greendrive;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyAdapter2 extends FragmentPagerAdapter {
    public Context myContext;
    public int totalTabs;
String fullname,start,dest,stop1,stop2,stop3,time,date;
String vno,model,type;
    public MyAdapter2(FragmentManager fm, Context myContext, int totalTabs, String start, String dest,
                      String stop1, String stop2, String stop3, String time, String date,
    String vno,String model,String type) {
        super(fm);
        this.myContext = myContext;
        this.totalTabs = totalTabs;

        this.start = start;
        this.dest = dest;
        this.stop1 = stop1;
        this.stop2 = stop2;
        this.stop3 = stop3;
        this.time = time;
        this.date = date;
        this.vno=vno;
        this.model=model;
        this.type=type;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new tripfragment(start,stop1,stop2,stop3,dest,time,date);
            case 1:

                return new carfragment(vno,model,type);
            default:
                return null;
       }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
