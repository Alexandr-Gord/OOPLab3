package sample.Serializers;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BinarySerializer<T> {
    private ArrayList<T> serializableContent;

    public BinarySerializer() {}

    public void serialize() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("serialize.ser"));
        for(T object : serializableContent){
            oos.writeObject(object);
        }
        oos.flush();
        oos.close();
    }

    public ArrayList<T> deserialize() throws IOException, ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("serialize.ser"));
        ArrayList<T> list = new ArrayList<>();
        try {
            while (true){
                list.add((T)ois.readObject());
            }
        } catch (EOFException e){
            ois.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<T> getSerializableContent() {
        return serializableContent;
    }

    public void setSerializableContent(ArrayList<T> serializableContent) {
        this.serializableContent = serializableContent;
    }
}
