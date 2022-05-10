package com.google;

import java.io.*;

public class Filehandler {
    /**
     * This method stores an object into a file,
     * if the file does not already exist a new one will be generated,
     * else the file will be overridden.
     * The object needs to implement 'Serializable'
     * @param filepath the path to the file
     * @param input the object to be saved
     */
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

    /**
     * This method reads the content of a file and returns it as an object
     * @param filepath the path to the file
     * @return a object of the file content
     */
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
