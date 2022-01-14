package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.myapplication.services.MainService;
import com.example.myapplication.utils.CommandUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import android.view.Menu;
import android.view.MenuItem;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this, MainService.class));

        finish();
/*
        FirebaseApp.initializeApp(this);

        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword("charles.hrf@gmail.com", "charles")
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Firestore", "Login Efetuado");

                        FirebaseFirestore db = FirebaseFirestore.getInstance();

                        db.collection("teste").document("primeiro")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()) {
                                            Log.d("Firestore", task.getResult().getId() + "=>" + task.getResult().get("nome"));
                                        }
                                    }


                                });

                        db.collection("teste").addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if(error != null) {
                                    Log.d("Erro", "Erro Firestore");
                                    return;
                                }

                                int x, y;

                                for(DocumentChange d : value.getDocumentChanges()) {
                                    switch (d.getType()) {
                                        case ADDED:
                                            x = d.getDocument().get("x", Integer.class);
                                            y = d.getDocument().get("y", Integer.class);
                                            try {
                                                CommandUtils.sendTap(x, y);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                            break;
                                        case MODIFIED:
                                            x = d.getDocument().get("x", Integer.class);
                                            y = d.getDocument().get("y", Integer.class);
                                            try {
                                                CommandUtils.sendTap(x, y);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                            Log.d("Pos", x + ", " + y);
                                            break;
                                        case REMOVED:
                                            Log.d("Remoção", "Teste remoção");
                                            break;
                                    }
                                }
                            }


                        });
                    }
                });*/

        //finish();
        //this.moveTaskToBack(true);


        /*binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Process su = Runtime.getRuntime().exec("su");
                    //Process clique = Runtime.getRuntime().exec("/system/bin/ input tap 120 120");
                    //DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());
                    //InputStream response = su.getInputStream();
                    //Process touch = Runtime.getRuntime().exec("input tap 600 450");
                    //CommandUtils.sendTap(600, 450);
                    //CommandUtils.sendText("Teste%sNovo%sNovo");
                    CommandUtils.sendSwipe(600, 450, 600, 450, 500);
                    CommandUtils.sendTap(600, 430);
                    //Process texto = Runtime.getRuntime().exec("input text Teste Novo");

                    //outputStream.writeBytes("/system/bin/ input tap 600 500");
                    //outputStream.flush();
                    //su.waitFor();
                    //Log.d("Teste", "Resposta " + readFully(response));
                    //outputStream.writeBytes("exit\n");
                    //outputStream.flush();
                    //outputStream.close();
                    Log.d("Teste", "Executou!");
                    Log.d("Teste", "Clique executado!");

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    public static String readFully(InputStream is) throws IOException {
        Log.d("Teste", String.valueOf(is.available()));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = is.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return baos.toString("UTF-8");
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

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}