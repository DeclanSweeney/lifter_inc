package com.example.lifter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ChatTabsAccessorAdapter extends FragmentPagerAdapter {

    public ChatTabsAccessorAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
             switch (position) {
                 case 0:
                     return new ChatFragment();
                 case 1:
                     return new GroupsFragment();
                 case 2:
                     return new ContactsFragment();
                 default:
                     return null;
             }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Chat";
            case 1:
                return "Groups";
            case 2:
                return "Contacts";
            default:
                return null;
        }
    }
}
