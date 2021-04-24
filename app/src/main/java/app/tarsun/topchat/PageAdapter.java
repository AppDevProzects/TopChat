package app.tarsun.topchat;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {

    int numberOfTabs;

    public PageAdapter(@NonNull FragmentManager fm, int numberOftabs) {
        super(fm, numberOftabs);
        numberOfTabs = numberOftabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        System.out.println(position);
        switch (position){
            case 0:
                return new ChatsFragment();
            case 1:
                return new ExploreFragment();
            case 2:
                return new StatusFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
