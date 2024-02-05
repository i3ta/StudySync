package spr2024.cs2340.group9.studysync;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import spr2024.cs2340.group9.studysync.databinding.ActivityMainBinding;
import spr2024.cs2340.group9.studysync.notifications.NotificationDao;
import spr2024.cs2340.group9.studysync.notifications.NotificationDatabase;
import spr2024.cs2340.group9.studysync.notifications.NotificationDatabaseHelper;
import spr2024.cs2340.group9.studysync.notifications.NotificationRequest;
import spr2024.cs2340.group9.studysync.notifications.NotificationWorker;

/**
 * Main activity.
 */
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        spr2024.cs2340.group9.studysync.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setSupportActionBar(binding.toolbar);
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Get a reference to the BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Set the listener for the bottom navigation view
        bottomNavigationView.setOnItemSelectedListener(navListener);

        // Clear running notification workers
        WorkManager manager = WorkManager.getInstance(getApplicationContext());
        NotificationDatabaseHelper.init(getApplicationContext());
        NotificationRequest[] requests = NotificationDatabaseHelper.get();
        for (NotificationRequest req: requests) {
            manager.cancelWorkById(req.id);
        }

        // Start notification loop
        manager.enqueue(OneTimeWorkRequest.from(NotificationWorker.class));
    }

    /**
     * Lambda for BottomNavigationView listener.
     */
    private final NavigationBarView.OnItemSelectedListener navListener = (item) -> {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        int itemId = item.getItemId();

        if (itemId == R.id.upcoming) {
            navController.navigate(R.id.upcomingFragment);
        } else if (itemId == R.id.todo) {
            navController.navigate(R.id.toDoListFragment);
        } else if (itemId == R.id.exams) {
            navController.navigate(R.id.examFragment);
        } else if (itemId == R.id.courses) {
            navController.navigate(R.id.courseFragment);
        }

        return true;
    };

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

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}