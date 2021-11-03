package sample;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import javafx.scene.paint.Color;

import java.io.Serializable;

@JsonAutoDetect
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SerializableColor.class, name = "SerializableColor"),
})

public class SerializableColor implements Serializable {
    private double r = 0;
    private double g = 0;
    private double b = 0;

    public SerializableColor() {
    }

    public void setColor(Color color){
        r = color.getRed();
        g = color.getGreen();
        b = color.getBlue();
    }

    public double getR() {
        return r;
    }

    public double getG() {
        return g;
    }

    public double getB() {
        return b;
    }

    public void setR(double r) {
        this.r = r;
    }

    public void setG(double g) {
        this.g = g;
    }

    public void setB(double b) {
        this.b = b;
    }
}
