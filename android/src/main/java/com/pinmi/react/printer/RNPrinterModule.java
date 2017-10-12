package com.pinmi.react.printer;


import android.hardware.usb.UsbDevice;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.pinmi.react.printer.adapter.USBPrinterAdapter;

import java.util.List;

/**
 * Created by bondwp322 on 2017/10/9.
 */

public class RNPrinterModule extends ReactContextBaseJavaModule {


    private USBPrinterAdapter adapter;
    public  RNPrinterModule(ReactApplicationContext reactContext){
        super(reactContext);
        this.adapter = USBPrinterAdapter.getInstance();
        this.adapter.init(reactContext);
    }

    @Override
    public String getName() {
        return "RNPrinter";
    }


    @ReactMethod
    public void getUSBDeviceList(Promise promise) {
        List<UsbDevice> usbDevices = adapter.getDeviceList();
        WritableArray pairedDeviceList = Arguments.createArray();
        for (UsbDevice usbDevice : usbDevices) {
            WritableMap deviceMap = Arguments.createMap();
            deviceMap.putString("device_name", usbDevice.getDeviceName());
            deviceMap.putInt("device_id", usbDevice.getDeviceId());
            deviceMap.putInt("vendor_id", usbDevice.getVendorId());
            deviceMap.putInt("product_id", usbDevice.getProductId());
            pairedDeviceList.pushMap(deviceMap);
        }
        promise.resolve(pairedDeviceList);
    }


    @ReactMethod
    public void connectPrinter(Integer vendorId, Integer productId, Promise promise) {
        if(!adapter.selectDevice(vendorId, productId)){
            promise.resolve(false);
        }else{
            promise.resolve(true);
        }
    }


    @ReactMethod
    public void closeConn(Promise promise) {
        adapter.closeConnectionIfExists();
        promise.resolve(null);
    }




    @ReactMethod
    public void printText(String text) {
        adapter.printText(text);
    }

    @ReactMethod
    public void printRawData(String base64Data) {
        adapter.printRawData(base64Data);
    }

}

