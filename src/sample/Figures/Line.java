package sample.Figures;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;

@JsonAutoDetect
//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
//@JsonTypeName("Line")
public class Line extends Point implements Serializable {

    private double x1;
    private double y1;

    public Line() {
        setStepsCount(2);
        setChangeableProperties("x y x1 y1 lineColor lineWidth name");
    }

    @Override
    public void changeProperty(String propertyName, String value) {
        super.changeProperty(propertyName, value);
        switch (propertyName){
            case "x1":
                setX1(Double.parseDouble(value));
                break;
            case "y1":
                setY1(Double.parseDouble(value));
        }
    }

    @Override
    public void draw() {
        getGc().setStroke(getLineColor());
        getGc().setLineWidth(getLineWidth());
        getGc().beginPath();
        getGc().moveTo(getX(), getY());
        getGc().lineTo(x1, y1);
        getGc().closePath();
        getGc().stroke();
    }

    @Override
    public void onClick(double x, double y) {
        if (getStepsCount() == 2){
            setX(x);
            setY(y);
        } else if (getStepsCount() == 1){
            x1 = x;
            y1 = y;
            setComplete(true);
            draw();
        }
        setStepsCount(getStepsCount() - 1);
    }

    public double getX1() {
        return x1;
    }

    public double getY1() {
        return y1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }
}
