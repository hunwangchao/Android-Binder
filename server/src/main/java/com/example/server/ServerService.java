package com.example.server;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import IInterface.IPerformTask;


public class ServerService extends Service {

    public Binder mBinder = new IPerformTask.Stub() {
        @Override
        public int add(int a, int b) {
            return a + b;
        }

        @Override
        public IBinder asBinder() {
            return  mBinder;
        }
    };
    @Override
    public IBinder onBind(Intent intent) {
        return  mBinder;
    }
}