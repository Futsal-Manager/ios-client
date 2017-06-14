package com.futsal.manager.EmbeddedCommunicationModule;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.futsal.manager.LogModule.LogManager;
import com.futsal.manager.R;

import static com.futsal.manager.DefineManager.EMBEDDED_SYSTEM_DEVICE_SOCKET;
import static com.futsal.manager.DefineManager.LOG_LEVEL_ERROR;
import static com.futsal.manager.DefineManager.LOG_LEVEL_INFO;
import static com.futsal.manager.DefineManager.NEW_DEVICE_FOUNDED;

/**
 * Created by stories2 on 2017. 6. 14..
 */

public class EmbeddedSystemFinderVer2 extends Dialog {

    TextView txtBluetoothStatus;
    ListView listOfBluetoothDevices;
    EmbeddedSystemFinderProcesserVer2 embeddedSystemFinderProcesserVer2;
    Activity embeddedSystemFinderVer2;

    public EmbeddedSystemFinderVer2(Context context) {
        super(context);
    }

    public EmbeddedSystemFinderVer2(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected EmbeddedSystemFinderVer2(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public void dismiss() {
        try {
            embeddedSystemFinderProcesserVer2.StopSearchDevices();
        }
        catch (Exception err) {
            LogManager.PrintLog("EmbeddedSystemFinderVer2", "dismiss", "Error: " + err.getMessage(), LOG_LEVEL_ERROR);
        }
        super.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.embedded_system_finder_ver2);

        InitLayout();
    }

    void InitLayout() {

        embeddedSystemFinderVer2 = getOwnerActivity();

        txtBluetoothStatus = (TextView)findViewById(R.id.txtBluetoothStatus);
        listOfBluetoothDevices = (ListView) findViewById(R.id.listOfBluetoothDevices);

        embeddedSystemFinderProcesserVer2 = new EmbeddedSystemFinderProcesserVer2(embeddedSystemFinderVer2, bluetoothDeviceFinderLayoutHandler);

        if(EMBEDDED_SYSTEM_DEVICE_SOCKET != null) {
            try {
                EMBEDDED_SYSTEM_DEVICE_SOCKET.close();
            }
            catch (Exception err) {
                LogManager.PrintLog("EmbeddedSystemFinderVer2", "InitLayout", "Error: " + err.getMessage(), LOG_LEVEL_ERROR);
            }
            EMBEDDED_SYSTEM_DEVICE_SOCKET = null;
        }

        embeddedSystemFinderProcesserVer2.InitBluetoothFinder();
    }

    Handler bluetoothDeviceFinderLayoutHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NEW_DEVICE_FOUNDED:
                    LogManager.PrintLog("EmbeddedSystemFinderVer2", "handleMessage", "new device founded", LOG_LEVEL_INFO);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };
}
