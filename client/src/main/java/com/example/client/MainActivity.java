package com.example.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import IInterface.IPerformTask;
import IInterface.Information;

public class MainActivity extends AppCompatActivity {

    private Button startIpc;
    private Button getRemoteProxy;

    public IPerformTask mBinder;
    private static final String SERVER_PACKAGE_NAME = "com.example.server";
    private static final String SERVICE_NAME = SERVER_PACKAGE_NAME + ".ServerService";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startIpc = findViewById(R.id.start_ipc);
        startIpc.setOnClickListener(v -> {
            try {
                Information information = mBinder.getInfo();
                Toast.makeText(getApplicationContext(), "Information\n" + information.name +"\n" + information.age + "\n" + information.isSingle, Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });
        getRemoteProxy = findViewById(R.id.get_server_binder);
        getRemoteProxy.setOnClickListener(v -> getRemoteProxy());
    }


    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = IPerformTask.Stub.asInterface(service);
            Toast.makeText(getApplicationContext(), "Remote proxy is" + mBinder, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void getRemoteProxy() {
        Intent intent = new Intent();
        intent.setClassName(SERVER_PACKAGE_NAME, SERVICE_NAME);
        getApplicationContext().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }
}