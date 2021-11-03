package sample.Figures;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.io.Serializable;

@JsonAutoDetect

public class Ellipse extends Circle implements Serializable {

    private double radius2;
    private double rotationAngle;

    public Ellipse() {
        setStepsCount(3);
        setChangeableProperties("x y radius radius2 lineColor lineWidth name");
    }

    @Override
    public void changeProperty(String propertyName, String value) {
        super.changeProperty(propertyName, value);
        switch (propertyName){
            case "radius2":
                setRadius2(Double.parseDouble(value));
        }
    }

    @Override
    public void draw() {
        getGc().setStroke(getLineColor());
        getGc().setLineWidth(getLineWidth());
        getGc().beginPath();
        getGc().strokeOval(getX() - getRadius(), getY() - getRadius2(), getRadius() * 2, getRadius2() * 2);
        getGc().closePath();
        getGc().stroke();
    }

    @Override
    public void onClick(double x, double y) {
        if (getStepsCount() == 3){
            setX(x);
            setY(y);
        } else if (getStepsCount() == 2){
            setRadius(Math.sqrt(Math.abs((getX() - x) * (getX() - x)) + Math.abs((getY() - y) * (getY() - y))));
            rotationAngle = Math.atan((getY() - y) / (getX() - x));
        } else if (getStepsCount() == 1){
            setRadius2(Math.abs(getY() - y));
            setComplete(true);
            draw();
        }
        setStepsCount(getStepsCount() - 1);
    }

    public double getRadius2() {
        return radius2;
    }

    public void setRadius2(double radius2) {
        this.radius2 = radius2;
    }
}
