package com.example.CSWordApp;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MotionEvent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GestureDetectorCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.CSWordApp.Adapters.RulesAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.LinkedHashMap;
import java.util.Map;

public class RulesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    private GestureDetectorCompat swipeListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Правила");
        setSupportActionBar(toolbar);

        Drawable backArrow = ResourcesCompat.getDrawable(this.getResources(), R.drawable.ic_baseline_arrow_back_32, null);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(backArrow);

        swipeListener = new GestureDetectorCompat(this, new SwipeGestureListener(this));

        Map<String, String> rulesTextContent= getRulesContent();
        viewPager = this.findViewById(R.id.view_pager);
        viewPager.setAdapter(new RulesAdapter(this, rulesTextContent));

        tabLayout = findViewById(R.id.tabs);
        String[] tabNames = getResources().getStringArray(R.array.tab_names);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(tabNames[position]);
        });
        tabLayoutMediator.attach();

    }

    private Map<String,String> getRulesContent() {
        Resources resources = this.getResources();
        Map<String, String> rulesContent = new LinkedHashMap<String, String>();
        rulesContent.put(resources.getString(R.string.article_top), resources.getString(R.string.article_content));
        rulesContent.put(resources.getString(R.string.noun_top), resources.getString(R.string.noun_content));
        rulesContent.put(resources.getString(R.string.adjective_top), null);
        rulesContent.put(resources.getString(R.string.adverb_top), null);
        rulesContent.put(resources.getString(R.string.verb_top), null);
        rulesContent.put(resources.getString(R.string.conditionals_top), null);

        return rulesContent;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
        return swipeListener.onTouchEvent(ev);
    }
}