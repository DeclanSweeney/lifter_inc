package com.example.lifter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Tabs setup for the fragments to be displayed within for more
 * user friendly UI
 */
public class ChatTabsAccessorAdapter extends FragmentPagerAdapter {

    public ChatTabsAccessorAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    /**
     * Initialises the fragments within the view
     * @param position
     * @return position of currently selected fragment
     */
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
//                 case 3:
//                     return new RequestsFragment();
                 default:
                     return null;
             }
    }

    @Override
    public int getCount() {
        return 3;
    }

    /**
     * Sets display name for fragments in position
     * @param position
     * @return
     */
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
//            case 3:
//                return "Requests";
            default:
                return null;
        }
    }
}
