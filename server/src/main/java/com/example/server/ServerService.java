package com.example.server;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import IInterface.IPerformTask;
import IInterface.Information;


public class ServerService extends Service {

    public Binder mBinder = new IPerformTask.Stub() {

        @Override
        public Information getInfo() {
            Information info = new Information();
            info.age = 18;
            info.name = "peter";
            info.isSingle = true;
            return info;
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