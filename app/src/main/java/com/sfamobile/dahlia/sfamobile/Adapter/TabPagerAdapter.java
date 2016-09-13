package com.sfamobile.dahlia.sfamobile.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sfamobile.dahlia.sfamobile.Fragments.LeadsFragment;
import com.sfamobile.dahlia.sfamobile.Fragments.MeetingsFragment;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
	int mNumOfTabs;

	public TabPagerAdapter(FragmentManager fm, int NumOfTabs) {
		super(fm);
		this.mNumOfTabs = NumOfTabs;
	}

	@Override
	public Fragment getItem(int position) {

		switch (position) {
		case 0:
			LeadsFragment tab1 = new LeadsFragment();
			return tab1;
		case 1:
			MeetingsFragment tab2 = new MeetingsFragment();
			return tab2;

		default:
			return null;
		}
	}

	@Override
	public int getCount() {
		return mNumOfTabs;
	}
}