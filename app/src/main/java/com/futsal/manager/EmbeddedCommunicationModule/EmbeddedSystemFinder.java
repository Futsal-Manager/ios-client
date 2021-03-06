package com.futsal.manager.EmbeddedCommunicationModule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.futsal.manager.LogModule.LogManager;
import com.futsal.manager.R;
import com.victor.loading.newton.NewtonCradleLoading;

import java.util.ArrayList;

import static com.futsal.manager.DefineManager.BLUETOOTH_CONNECTION_FAILURE;
import static com.futsal.manager.DefineManager.EMBEDDED_SYSTEM_BLUETOOTH_DEVICE_LIST;
import static com.futsal.manager.DefineManager.EMBEDDED_SYSTEM_DEVICE;
import static com.futsal.manager.DefineManager.EMBEDDED_SYSTEM_DEVICE_SOCKET;
import static com.futsal.manager.DefineManager.ENABLE_BLUETOOTH_MODULE_USER_ACCESS_ACCEPT;
import static com.futsal.manager.DefineManager.LOG_LEVEL_ERROR;
import static com.futsal.manager.DefineManager.SEARCH_EMBEDDED_SYSTEM;

/**
 * Created by stories2 on 2017. 5. 14..
 */

public class EmbeddedSystemFinder extends Activity {

    EmbeddedSystemFinderProcesser embeddedSystemFinderProcesser;
    NewtonCradleLoading newtonCradleLoading;
    TextView txtWaitStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.embedded_system_finder);

        InitProcess();
    }

    void InitProcess() {
        txtWaitStatus = (TextView) findViewById(R.id.txtWaitStatus);
        newtonCradleLoading = (NewtonCradleLoading) findViewById(R.id.newton_cradle_loading);
        newtonCradleLoading.start();

        BLUETOOTH_CONNECTION_FAILURE = false;

        EMBEDDED_SYSTEM_BLUETOOTH_DEVICE_LIST = new ArrayList<BluetoothDeviceItemModel>();

        embeddedSystemFinderProcesser = new EmbeddedSystemFinderProcesser(this, txtWaitStatus);
        embeddedSystemFinderProcesser.InitBluetoothFinder();

        if(EMBEDDED_SYSTEM_DEVICE != null) {
            EMBEDDED_SYSTEM_DEVICE = null;
        }

        if(EMBEDDED_SYSTEM_DEVICE_SOCKET != null) {
            try {
                EMBEDDED_SYSTEM_DEVICE_SOCKET.close();
                EMBEDDED_SYSTEM_DEVICE_SOCKET = null;
            }
            catch (Exception err) {
                LogManager.PrintLog("EmbeddedSystemFinder", "InitProcess", "Error: " + err.getMessage(), LOG_LEVEL_ERROR);
            }
        }
    }

    @Override
    protected void onDestroy() {
        embeddedSystemFinderProcesser.StopSearchDevices();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ENABLE_BLUETOOTH_MODULE_USER_ACCESS_ACCEPT:
                if(resultCode == Activity.RESULT_OK) {
                    embeddedSystemFinderProcesser.SearchBluetoothDevice();
                }
                else {
                    embeddedSystemFinderProcesser.ShowWarningDialog();
                }
                break;
            case SEARCH_EMBEDDED_SYSTEM:
                if(requestCode == Activity.RESULT_OK) {

                }
                else {

                }
                break;
        }
    }
}
