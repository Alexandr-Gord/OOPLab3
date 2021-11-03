package sample;

import sample.Figures.*;

import java.io.Serializable;
import java.util.ArrayList;

public class FigureTypes {

    private ArrayList<Figure> figureTypes = new ArrayList<>();

    public FigureTypes() {
        addType(new Point());
        addType(new Line());
        addType(new Circle());
        addType(new Triangle());
        addType(new Quadrangle());
        addType(new Ellipse());
    }

    public void addType(Figure figure){
        figureTypes.add(figure);
    }

    public void remove(){

    }

    public ArrayList<Figure> getFigureTypes() {
        return figureTypes;
    }
}
