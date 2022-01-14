package com.example.myapplication.utils;

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
        /*Process process = Runtime.getRuntime().exec("su");
        DataOutputStream dos = new DataOutputStream(process.getOutputStream());
        String cmd = "/system/bin/" + command;
        dos.writeBytes(cmd);*/
        Process cmd = Runtime.getRuntime().exec(command);
    }
}
