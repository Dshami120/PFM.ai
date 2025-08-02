package storage;

import java.io.*;
import java.util.*;

public class FileSystem {
    private static FileSystem instance = null;

    private FileSystem() {}

    public static FileSystem getInstance() {
        if (instance == null) {
            instance = new FileSystem();
        }
        return instance;
    }

    public <T> void saveList(String filename, List<T> data) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(data);
        } catch (IOException e) {
            System.out.println("Error saving file: " + filename);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> loadList(String filename) {
        File file = new File(filename);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (List<T>) in.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public void saveMap(String filename, Map<String, ?> map) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(map);
        } catch (IOException e) {
            System.out.println("Error saving map: " + filename);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> Map<String, T> loadMap(String filename) {
        File file = new File(filename);
        if (!file.exists()) return new HashMap<>();
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (Map<String, T>) in.readObject();
        } catch (Exception e) {
            return new HashMap<>();
        }
    }
}
