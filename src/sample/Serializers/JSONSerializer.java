package sample.Serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import sample.Figure;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JSONSerializer<T> {
    private ArrayList<T> serializableContent;

    public JSONSerializer() {}

    public void serialize() throws IOException {
        File jsonFile = new File("serialize.json");
        //ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("serialize.json"));
   //     StringWriter writer = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        StringBuilder sb = new StringBuilder("");
       // mapper.writeValue(jsonFile, serializableContent);
        sb.append("[");
        for (Object o: serializableContent){
            sb.append(mapper.writeValueAsString(o));
            sb.append(",");
        }
        sb.deleteCharAt(sb.lastIndexOf(","));
        sb.append("]");

        //System.out.println(sb);

    }


    public ArrayList<T> deserialize() throws IOException, ClassNotFoundException{
        //ObjectInputStream ois = new ObjectInputStream(new FileInputStream("serialize.ser"));
        ArrayList<T> list = new ArrayList<>();
        ArrayList<String> jsonlist;
        File jsonFile = new File("serialize.json");
        ObjectMapper mapper = new ObjectMapper();
      //  list = mapper.readValue(jsonFile, List<Figure>.class);
       // jsonlist = mapper.readValue(jsonFile, ArrayList.class);
      //  System.out.println(jsonlist.get(0));
      //  System.out.println(jsonlist.get(1));

        return list;
    }



    public ArrayList<T> getSerializableContent() {
        return serializableContent;
    }

    public void setSerializableContent(ArrayList<T> serializableContent) {
        this.serializableContent = serializableContent;
    }

}
