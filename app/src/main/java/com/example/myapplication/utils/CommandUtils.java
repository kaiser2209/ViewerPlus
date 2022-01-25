package com.example.myapplication.utils;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.view.MotionEvent;

import androidx.annotation.RequiresApi;

import com.google.common.util.concurrent.ServiceManager;

import java.io.DataOutputStream;
import java.io.IOException;

public class CommandUtils {
    public static void sendTap(int x, int y) throws IOException {
        String command = "input tap " + x + " " + y;
        sendCommand(command);
    }

    public static void sendText(String text) throws IOException {
        text = text.replaceAll(" ", "%s");
        String command = "input keyboard text " + text;
        sendCommand(command);
    }

    public static void sendSwipe(int x1, int y1, int x2, int y2, int duration) throws IOException {
        String command = "input swipe " + x1 + " " + y1 + " " + x2 + " " + y2 + " " + duration;
        sendCommand(command);
    }

    public static void sendSwipe(int x1, int y1, int x2, int y2) throws IOException {
        sendSwipe(x1, y1, x2, y2, 300);
    }

    public static void sendCommand(String command) throws IOException {
        Process process = Runtime.getRuntime().exec("su");
        DataOutputStream dos = new DataOutputStream(process.getOutputStream());
        String cmd = "/system/bin/" + command;
        dos.writeBytes(cmd);
        dos.flush();
        dos.close();
        //Process cmd = Runtime.getRuntime().exec(command);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static GestureDescription createClick(float x, float y) {
        final int DURATION = 1;

        Path clickPath = new Path();
        clickPath.moveTo(x, y);
        GestureDescription.StrokeDescription clickStroke =
                new GestureDescription.StrokeDescription(clickPath, 0, DURATION);
        GestureDescription.Builder clickBuilder = new GestureDescription.Builder();
        clickBuilder.addStroke(clickStroke);
        return clickBuilder.build();
    }

}
