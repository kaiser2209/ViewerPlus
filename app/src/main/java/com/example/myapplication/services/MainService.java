package com.example.myapplication.services;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.utils.CommandUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.concurrent.Executor;

public class MainService extends Service {
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Serviço", "Serviço Iniciado");
        FirebaseApp.initializeApp(getApplicationContext());

        mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword("charles.hrf@gmail.com", "charles")
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Firestore", "Login Efetuado");

                        FirebaseFirestore db = FirebaseFirestore.getInstance();

                        /*db.collection("teste").document("primeiro")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()) {
                                            Log.d("Firestore", task.getResult().getId() + "=>" + task.getResult().get("nome"));
                                        }
                                    }


                                });*/

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
                                                Log.d("Comando", "Comando Enviado");
                                                CommandUtils.sendTap(x, y);
                                                //Process cmd = Runtime.getRuntime().exec(new String[]{"su", "/system/bin/ input tap 600 200"});
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
                });


        return super.onStartCommand(intent, flags, startId);
    }
}
