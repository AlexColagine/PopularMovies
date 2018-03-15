package com.example.android.popularmovies.UI;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.Fragments.Favorite;
import com.example.android.popularmovies.Fragments.PopularRated;
import com.example.android.popularmovies.R;

import static com.example.android.popularmovies.Utils.Utils.QUERY_MOVIE;
import static com.example.android.popularmovies.Utils.Utils.QUERY_POPULAR_PATH;
import static com.example.android.popularmovies.Utils.Utils.QUERY_TOP_RATED_PATH;


public class MainActivity extends AppCompatActivity {

    public TabLayout tabLayout;
    public TextView tabOne;
    public TextView tabTwo;
    public TextView tabThree;
    private int[] imageResId = {
            R.drawable.most_popular_image,
            R.drawable.top_rated_image,
            R.drawable.favorite_image,
            R.drawable.most_popular_image_yellow,
            R.drawable.top_rated_image_yellow,
            R.drawable.favorite_image_yellow
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /**
         * The {@link android.support.v4.view.PagerAdapter} that will provide
         fragments for each of the sections. We use a
         {@link FragmentPagerAdapter} derivative, which will keep every
         loaded fragment in memory. If this becomes too memory intensive, it
         may be best to switch to a
         {@link android.support.v4.app.FragmentStatePagerAdapter}.
         *  _____________________________________________________________________
         * Create the adapter that will return a fragment for each of the three
         * primary sections of the activity.
         */
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        /**
         * The {@link ViewPager} that will host the section contents.
         * ________________________________________________________
         * Set up the ViewPager with the sections adapter.
         */
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        setupTabIcons();

        QUERY_MOVIE = QUERY_POPULAR_PATH;
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @SuppressLint("NewApi")
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        QUERY_MOVIE = QUERY_POPULAR_PATH;
                        updateUiMostPopular();
                        break;
                    case 1:
                        QUERY_MOVIE = QUERY_TOP_RATED_PATH;
                        updateUiTopRated();
                        break;
                    case 2:
                        updateUiFavorite();
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @SuppressLint("NewApi")
    private void updateUiMostPopular() {
        tabOne.setCompoundDrawablesWithIntrinsicBounds(imageResId[3], 0, 0, 0);
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(imageResId[1], 0, 0, 0);
        tabThree.setCompoundDrawablesWithIntrinsicBounds(imageResId[2], 0, 0, 0);

        tabOne.setTextColor(Color.WHITE);
        tabTwo.setTextColor(getColor(R.color.colorToolbar));
        tabThree.setTextColor(getColor(R.color.colorToolbar));
    }

    @SuppressLint("NewApi")
    private void updateUiTopRated() {
        tabOne.setCompoundDrawablesWithIntrinsicBounds(imageResId[0], 0, 0, 0);
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(imageResId[4], 0, 0, 0);
        tabThree.setCompoundDrawablesWithIntrinsicBounds(imageResId[2], 0, 0, 0);

        tabOne.setTextColor(getColor(R.color.colorToolbar));
        tabTwo.setTextColor(Color.WHITE);
        tabThree.setTextColor(getColor(R.color.colorToolbar));
    }

    @SuppressLint("NewApi")
    private void updateUiFavorite() {
        tabOne.setCompoundDrawablesWithIntrinsicBounds(imageResId[0], 0, 0, 0);
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(imageResId[1], 0, 0, 0);
        tabThree.setCompoundDrawablesWithIntrinsicBounds(imageResId[5], 0, 0, 0);

        tabOne.setTextColor(getColor(R.color.colorToolbar));
        tabTwo.setTextColor(getColor(R.color.colorToolbar));
        tabThree.setTextColor(Color.WHITE);
    }

    @SuppressLint({"InflateParams", "NewApi"})
    private void setupTabIcons() {

        tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.name_fragment, null);
        tabOne.setText(R.string.most_popular);
        tabOne.setTextColor(Color.WHITE);
        tabOne.setCompoundDrawablesWithIntrinsicBounds(imageResId[3], 0, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.name_fragment, null);
        tabTwo.setText(R.string.top_rated);
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(imageResId[1], 0, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.name_fragment, null);
        tabThree.setText(R.string.favorite);
        tabThree.setCompoundDrawablesWithIntrinsicBounds(imageResId[2], 0, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        final int PAGE_COUNT = 3;

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new PopularRated();
                case 1:
                    return new PopularRated();
                case 2:
                    return new Favorite();
            }
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return PAGE_COUNT;
        }
    }
}
