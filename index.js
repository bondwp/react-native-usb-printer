import React, { NativeModules } from 'react-native';


import EPToolkit from 'escpos-printer-toolkit';

var RNPrinter = NativeModules.RNPrinter;

var getUSBDeviceList = () => RNPrinter.getUSBDeviceList();

var connectPrinter = (vendorId, productId) => RNPrinter.connectPrinter(vendorId, productId);

var printText = (text) => {
  let options = {
    beep: false, 
    cut: false, 
    tailingLine: false,
    encoding: 'GBK'
  }
  const buffer = EPToolkit.exchange_text(text, options)
  RNPrinter.printRawData(buffer.toString("base64"))
}

var printBillTextWithCut = (billText) => {
  let options = {
    beep: true, 
    cut: true, 
    encoding: 'GBK',
    tailingLine: true
  }
  const buffer = EPToolkit.exchange_text(billText, options)
  RNPrinter.printRawData(buffer.toString("base64"))
}

var closeConn = () => RNPrinter.closeConn();


export const RNUSBPrinter = {
  getUSBDeviceList,
  connectPrinter,
  printText,
  printBillTextWithCut,
  closeConn
}