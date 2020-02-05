package com.gonzaloandcompany.woldquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.gonzaloandcompany.woldquiz.models.User;
import com.gonzaloandcompany.woldquiz.ui.notifications.IUserListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements IUserListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_ranking)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.filterIcon) {
            //programar el filtro
        } else if (id == R.id.searchIcon) {
            //programar un buscar
        } else if (id == R.id.quizIcon) {
            //programar intent para ir al quiz
        } else if (id == R.id.logout){
            Intent loginActivity = new Intent(this, LoginActivity.class);
            startActivity(loginActivity);
        } else if (id == R.id.perfil){
            //programar para ir al perfil
        }
        return super.onOptionsItemSelected(item);
    }


    public void onUserClick(User u) {

    }

    //cerrar aplicación sin volver al login
    public void onBackPressed() {
        this.finish();
    }


}
