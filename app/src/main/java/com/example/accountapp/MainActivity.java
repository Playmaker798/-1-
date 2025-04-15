package com.example.accountapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.accountapp.databinding.ActivityMainBinding;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 设置 Toolbar
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }

        // 设置导航
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_activity_main);
        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            
            Set<Integer> topLevelDestinations = new HashSet<>();
            topLevelDestinations.add(R.id.navigation_transactions);
            topLevelDestinations.add(R.id.navigation_accounts);
            topLevelDestinations.add(R.id.navigation_categories);
            topLevelDestinations.add(R.id.navigation_statistics);
            
            AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(topLevelDestinations).build();
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(binding.navView, navController);
        }
    }
} 