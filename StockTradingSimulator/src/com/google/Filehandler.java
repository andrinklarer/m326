package com.google;

import java.io.*;

public class Filehandler {
    public static void writeFile(String filepath, Object input) {
        File file = new File(filepath);
        try {
            if (file.createNewFile())
                System.out.println("File " + file.getName() + " created");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(input);
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object readFile(String filepath) {
        File file = new File(filepath);
        Object fileContent = null;

        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            fileContent = ois.readObject();
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
        }

        return fileContent;
    }
}
