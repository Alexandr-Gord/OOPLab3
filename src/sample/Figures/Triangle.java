package sample.Figures;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;

@JsonAutoDetect
//@JsonTypeName("Triangle")
public class Triangle extends Line implements Serializable {
    private double x2;
    private double y2;

    public Triangle() {
        setStepsCount(3);
        setChangeableProperties("x y x1 y1 x2 y2 lineColor lineWidth name");
    }

    @Override
    public void changeProperty(String propertyName, String value) {
        super.changeProperty(propertyName, value);
        switch (propertyName){
            case "x2":
                setX2(Double.parseDouble(value));
                break;
            case "y2":
                setY2(Double.parseDouble(value));
        }
    }

    @Override
    public void draw() {
        getGc().setStroke(getLineColor());
        getGc().setLineWidth(getLineWidth());
        getGc().beginPath();
        getGc().moveTo(getX(), getY());
        getGc().lineTo(getX1(), getY1());
        getGc().lineTo(getX2(), getY2());
        getGc().closePath();
        getGc().stroke();
    }

    @Override
    public void onClick(double x, double y) {
        if (getStepsCount() == 3){
            setX(x);
            setY(y);
        } else if (getStepsCount() == 2){
            setX1(x);
            setY1(y);
        } else if (getStepsCount() == 1){
            setX2(x);
            setY2(y);
            setComplete(true);
            draw();
        }
        setStepsCount(getStepsCount() - 1);
    }

    public double getX2() {
        return x2;
    }

    public double getY2() {
        return y2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }
}
