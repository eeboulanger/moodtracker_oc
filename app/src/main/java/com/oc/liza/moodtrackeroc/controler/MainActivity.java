package com.oc.liza.moodtrackeroc.controler;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.oc.liza.moodtrackeroc.R;
import com.oc.liza.moodtrackeroc.model.Mood;
import com.oc.liza.moodtrackeroc.utils.CommentPopUp;
import com.oc.liza.moodtrackeroc.utils.MoodListManager;
import com.oc.liza.moodtrackeroc.utils.SharePopUp;
import com.oc.liza.moodtrackeroc.view.ScreenSlide;
import com.oc.liza.moodtrackeroc.view.VerticalViewPager;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private VerticalViewPager mViewPager;
    private String mComment = "";
    private Mood mMood;
    private MoodListManager mMoodListManager;
    private final Context ctx = this;
    private Calendar c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getResources().getStringArray(R.array.colorArray);
        initButtons();
        initSlideScreen();
        mMoodListManager = new MoodListManager(this);
    }

    private void initButtons() {

        //Button to add a comment
        ImageButton commentBtn = findViewById(R.id.commentButton);
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mood = mViewPager.getCurrentItem();
                new CommentPopUp(ctx, mood);

            }
        });

        //Button to share your mood by SMS
        ImageButton shareBtn = findViewById(R.id.shareButton);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Mood> moods = mMoodListManager.getMoodList();
                mMood = moods.get(moods.size() - 1);
                new SharePopUp(ctx, mMood);

            }
        });

        //Button to access history
        ImageButton historyBtn = findViewById(R.id.historyButton);
        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent history = new Intent(MainActivity.this, History.class);
                startActivity(history);
            }
        });

    }

    private void initSlideScreen() {
        // Set slide up and down function
        mViewPager = findViewById(R.id.pager);
        ScreenSlide adapter = new ScreenSlide(this);
        mViewPager.setAdapter(adapter);

        //Put the happy smiley as first image when app is launched
        mViewPager.setCurrentItem(3);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                createMood();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    private void createMood() {
        c = Calendar.getInstance();
        int mood = mViewPager.getCurrentItem();
        Mood m = new Mood(mood, c, mComment);
        mMoodListManager.addMood(m);
    }

}


