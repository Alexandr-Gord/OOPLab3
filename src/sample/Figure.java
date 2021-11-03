package sample;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.Figures.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

@JsonAutoDetect
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Point.class, name = "Point"),
        @JsonSubTypes.Type(value = Circle.class, name = "Circle"),
        @JsonSubTypes.Type(value = Ellipse.class, name = "Ellipse"),
        @JsonSubTypes.Type(value = Line.class, name = "Line"),
        @JsonSubTypes.Type(value = Quadrangle.class, name = "Quadrangle"),
        @JsonSubTypes.Type(value = Triangle.class, name = "Triangle")
})

public abstract class Figure implements Serializable {

    private String name = "unnamed";
    private double lineWidth = 1;
    private SerializableColor sLineColor;
    private SerializableColor sFillColor;
    @JsonIgnore
    private transient Color lineColor = Color.BLACK;
    @JsonIgnore
    private transient Color fillColor = Color.BLACK;
    private boolean isComplete = false;
    private int stepsCount = 1;
    @JsonIgnore
    private transient GraphicsContext gc;

    private String changeableProperties = "";

    public Figure() {}

    public void draw(){}
    public void onClick(double x, double y){}

    public void changeProperty(String propertyName, String value){}

    private void readObject(ObjectInputStream input) throws IOException, ClassNotFoundException {
        input.defaultReadObject();
        lineColor = Color.rgb((int) (sLineColor.getR() * 255), (int) (sLineColor.getG() * 255), (int) (sLineColor.getB() * 255));
//        fillColor = Color.rgb((int) sFillColor.getR(), (int) sFillColor.getG(), (int) sFillColor.getB());
    }
    private void writeObject(ObjectOutputStream output) throws IOException, ClassNotFoundException {
        output.defaultWriteObject();
    }

    public void setLineColor(Color lineColor) {
        this.lineColor = lineColor;
        this.sLineColor = new SerializableColor();
        sLineColor.setColor(lineColor);
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
        this.sFillColor = new SerializableColor();
        sFillColor.setColor(fillColor);
    }

    public void setLineWidth(double width){
        this.lineWidth = width;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public void setStepsCount(int stepsCount) {
        this.stepsCount = stepsCount;
    }

    public void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setChangeableProperties(String changeableProperties) {
        this.changeableProperties = changeableProperties;
    }

    public Color getLineColor() {
        return lineColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public double getLineWidth() {
        return lineWidth;
    }

    public int getStepsCount() {
        return stepsCount;
    }

    public GraphicsContext getGc() {
        return gc;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public String getName() {
        return name;
    }

    public String getChangeableProperties() {
        return changeableProperties;
    }

    public SerializableColor getsLineColor() {
        return sLineColor;
    }

    public SerializableColor getsFillColor() {
        return sFillColor;
    }
}
