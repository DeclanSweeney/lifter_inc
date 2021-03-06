package com.example.lifter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class ChatActivity extends AppCompatActivity {
    private Toolbar chatToolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ChatTabsAccessorAdapter tabsAccessorAdapter;

    /**
     * Activity for linking the Main to the Chat side of the application
     * Initialises a Tab viewer with the fragments for different parts of
     * the Chat functionality (Single chat, group chat and contacts)
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        chatToolbar = findViewById(R.id.chat_page_toolbar);
        setSupportActionBar(chatToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Chat");

        viewPager = findViewById(R.id.chat_tabs_pager);
        tabsAccessorAdapter = new ChatTabsAccessorAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabsAccessorAdapter);
        tabLayout = findViewById(R.id.chat_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
