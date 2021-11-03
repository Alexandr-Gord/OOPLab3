package sample.Figures;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.io.Serializable;

@JsonAutoDetect

public class Circle extends Point implements Serializable {
    private double radius;

    public Circle() {
        setStepsCount(2);
        setChangeableProperties("x y radius lineColor lineWidth name");
    }

    @Override
    public void changeProperty(String propertyName, String value) {
        super.changeProperty(propertyName, value);
        switch (propertyName){
            case "x":
                setX(Double.parseDouble(value));
                break;
            case "y":
                setY(Double.parseDouble(value));
                break;
            case "radius":
                setRadius(Double.parseDouble(value));
                break;
            case "lineColor":
                break;
            case "name":
                setName(value);
        }
    }

    @Override
    public void draw() {
        getGc().setStroke(getLineColor());
        getGc().setLineWidth(getLineWidth());
        getGc().beginPath();
        getGc().strokeOval(getX() - radius, getY() - radius, radius * 2, radius * 2);
        getGc().closePath();
        getGc().stroke();
    }

    @Override
    public void onClick(double x, double y) {
        if (getStepsCount() == 2){
            setX(x);
            setY(y);
        } else if (getStepsCount() == 1){
            radius = Math.sqrt(Math.abs((getX() - x) * (getX() - x)) + Math.abs((getY() - y) * (getY() - y)));
            setComplete(true);
            draw();
        }
        setStepsCount(getStepsCount() - 1);
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
