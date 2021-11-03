package sample.Figures;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.io.Serializable;

@JsonAutoDetect

public class Quadrangle extends Line implements Serializable {

    public Quadrangle() {
        setStepsCount(2);
        setChangeableProperties("x y x1 y1 lineColor lineWidth name");
    }

    @Override
    public void changeProperty(String propertyName, String value) {
        super.changeProperty(propertyName, value);
    }

    @Override
    public void draw() {
        getGc().setStroke(getLineColor());
        getGc().setLineWidth(getLineWidth());
        getGc().beginPath();
        getGc().moveTo(getX(), getY());
        getGc().lineTo(getX1(), getY());
        getGc().lineTo(getX1(), getY1());
        getGc().lineTo(getX(), getY1());
        getGc().lineTo(getX(), getY());
        getGc().closePath();
        getGc().stroke();
    }

    @Override
    public void onClick(double x, double y) {
        if (getStepsCount() == 2){
            setX(x);
            setY(y);
        } else if (getStepsCount() == 1){
            setX1(x);
            setY1(y);
            setComplete(true);
            draw();
        }
        setStepsCount(getStepsCount() - 1);
    }
}
