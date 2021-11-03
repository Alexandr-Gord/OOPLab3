package sample.Figures;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import javafx.scene.paint.Color;
import sample.Figure;

import java.awt.*;
import java.io.Serializable;

@JsonAutoDetect

public class Point extends Figure implements Serializable {

    private double x;
    private double y;

    public Point() {
        setStepsCount(1);
        setChangeableProperties("x y lineColor lineWidth name");
    }

    @Override
    public void changeProperty(String propertyName, String value) {
        switch (propertyName){
            case "x":
                setX(Double.parseDouble(value));
                break;
            case "y":
                setY(Double.parseDouble(value));
                break;
            case "lineColor":
                String[] color = value.split(" ");
                setLineColor(Color.rgb(Integer.parseInt(color[0]), Integer.parseInt(color[1]), Integer.parseInt(color[2])));
                break;
            case "lineWidth":
                setLineWidth(Double.parseDouble(value));
                break;
            case "name":
                setName(value);
        }
    }

    public void draw() {
        getGc().setFill(getLineColor());
        getGc().beginPath();
        getGc().fillOval(x, y, getLineWidth(), getLineWidth());
        getGc().closePath();
        getGc().stroke();
    }

    public void onClick(double x, double y) {
        if (getStepsCount() == 1){
            this.x = x;
            this.y = y;
            draw();
            setComplete(true);
        }
        setStepsCount(getStepsCount() - 1);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

}
