package com.ysw.welcomePager;

import android.animation.ArgbEvaluator;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.ysw.R;

public class WelcomeActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private WelcomAdapter welcomAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private AppCompatButton buttonFinish;
    private ImageButton buttonPre;
    private ImageButton buttonNext;
    private ImageView[] indicators;

    private int bgColors[];

    private int currentPager;
    private SharedPreferences sp;

    private static final int MSG_DATA_INSERT_FINISH = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp= PreferenceManager.getDefaultSharedPreferences(this);
        //判断是不是第一次启动
        if (sp.getBoolean("fristLunch",true)){
            setContentView(R.layout.welcome_activity_main);
            //做必要的应用数据预加载
            new InitCompaniesDataTask().execute();
            initView();
            initData();
            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                    ArgbEvaluator().evaluate(float fraction, Object startValue, Object endValue)
                    int colorUpdate= (int) new ArgbEvaluator().evaluate(positionOffset,
                            bgColors[position],bgColors[position==2?position:position+1]);
                    mViewPager.setBackgroundColor(colorUpdate);
                }

                @Override
                public void onPageSelected(int position) {
                    currentPager=position;
                    updateColor(position);
                    mViewPager.setBackgroundColor(bgColors[position]);
                    buttonPre.setVisibility(position == 0 ? View.GONE : View.VISIBLE);
                    buttonNext.setVisibility(position == 2 ? View.GONE : View.VISIBLE);
                    buttonFinish.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
                    if (position==2){
                        buttonFinish.setBackgroundResource(R.color.light_blue_500);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            buttonFinish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor=sp.edit();
                    editor.putBoolean("fristLunch",false);
                    editor.apply();
                    notFristLunchApp();
                }
            });
            buttonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentPager+=1;
                    //public void setCurrentItem(int item, boolean smoothScroll)
                    mViewPager.setCurrentItem(currentPager,true);
                }
            });
            buttonPre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentPager-=1;
                    mViewPager.setCurrentItem(currentPager,true);
                }
            });
        }else {
            notFristLunchApp();
            finish();
        }

    }

    private void initView(){
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        welcomAdapter = new WelcomAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(welcomAdapter);
        buttonFinish= (AppCompatButton) findViewById(R.id.btnFinish);
        buttonFinish.setText(R.string.onboarding_wait_lunch);
        //预处理工作未完成之前先设置为点击无效
        buttonFinish.setEnabled(false);
        buttonNext = (ImageButton) findViewById(R.id.imgbtnNext);
        buttonPre = (ImageButton) findViewById(R.id.imgbtnpre);
        indicators = new ImageView[] {(ImageView) findViewById(R.id.imgIndicator0),
                (ImageView) findViewById(R.id.imgIndicator1),
                (ImageView) findViewById(R.id.imgIndicator2)};

    }

    private void initData(){
        bgColors=new int[]{ContextCompat.getColor(this,R.color.colorPrimary),
                ContextCompat.getColor(this, R.color.cyan_500),
                ContextCompat.getColor(this, R.color.light_blue_500)};
    }

    private void updateColor(int position){
        for (int i=0;i<indicators.length;i++){
            indicators[i].setBackgroundResource(i==position ? R.drawable.wel_onboarding_indicator_selected : R.drawable.wel_onboarding_indicator_unselected);
        }
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_DATA_INSERT_FINISH:
                    buttonFinish.setText(R.string.onboarding_finish_button_description);
                    buttonFinish.setEnabled(true);
                    break;
            }
        }
    };

    private void notFristLunchApp(){
        /*Intent intent=new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);*/
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
     * 这里可以进行应用第一次启动所以加载的必要操作
     */
    public class   InitCompaniesDataTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... params) {
            //具体请求
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            handler.sendEmptyMessage(MSG_DATA_INSERT_FINISH);
        }
    }
    /**
     * A placeholder fragment containing a simple view.
     *//*
    public static class PlaceholderFragment extends Fragment {
        *//**
         * The fragment argument representing the section number for this
         * fragment.
         *//*
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        *//**
         * Returns a new instance of this fragment for the given section
         * number.
         *//*
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
            View rootView = inflater.inflate(R.layout.welcome_fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    *//**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     *//*
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }*/
}
