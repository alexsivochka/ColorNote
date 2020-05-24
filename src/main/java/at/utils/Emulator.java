package at.utils;

import io.qameta.allure.Step;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Emulator {

    private void executeCommand(String command) {
        Runtime rt = Runtime.getRuntime();
        try {
            rt.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Step("Запуск эмулятора")
    public void start(String emulatorName) {
        Set<String> availableEmulators = getAvailableEmulators();
        for (String name: availableEmulators) {
            if (name.equalsIgnoreCase(emulatorName)) {
                String command= String.format("%s/tools/emulator -avd %s",System.getenv("ANDROID_HOME"),emulatorName);
                executeCommand(command);
                break;
            }
        }
    }

    @Step("Закрытие эмулятора")
    public void close() {
        String command= "adb emu kill";
        executeCommand(command);
    }


    private static Set<String> getAvailableEmulators() {
        Set<String> emulatorList = new HashSet<>();

        Process p = null;
        String command= System.getenv("ANDROID_HOME") + "/tools/emulator -list-avds";
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        BufferedReader buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String emulatorName = "";

        while (true) {
            try {
                if (!((emulatorName = buf.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            emulatorList.add(emulatorName);
        }
        return emulatorList;
    }
}


