package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import sample.Serializers.BinarySerializer;
import sample.Serializers.JSONSerializer;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    private double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
    private double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
    private HBox mainPane = new HBox();
    private Scene mainScene;
    private Stage primaryStage;
    private Stage changeStage;
    private final Canvas canvas = new Canvas();
    private GraphicsContext gc = canvas.getGraphicsContext2D();
    private FigureTypes types = new FigureTypes();
    private ArrayList<Figure> figureList = new ArrayList<>();
    private Figure drawSelected;
    private Figure changeSelected;
    private TextField propertyLine;
    private ChoiceBox<String> figureChoiceBox = new ChoiceBox<>();
    private ChoiceBox<String> propertyChoiceBox = new ChoiceBox<>();
    private int figureCount = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        createMainWindow();
    }

    private void update() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        figureChoiceBox.getItems().clear();
        for (Figure f : figureList) {
            f.draw();
            figureChoiceBox.getItems().add(f.getName());
        }
    }

    private void removeFigure(Figure figure) {
        if (figureList.remove(figure)) {
            update();
        }
    }

    private void fillFigureList(ArrayList<Figure> figures) {
        figureList.clear();
        for (Figure figure : figures) {
            figureCount++;
            figure.setName(figure.getClass().getSimpleName() + "_" + figureCount);
            figureList.add(figure);
            figureChoiceBox.getItems().add(figure.getClass().getSimpleName() + "_" + figureCount);
        }
    }

    private ImageView createImageView(String imgName) {
        ImageView imageView = new ImageView(new Image(this.getClass().getResource("resources/images/" + imgName).toString()));
        imageView.setFitHeight(35);
        imageView.setFitWidth(35);
        return imageView;
    }

    private int getSelectedButtonNumber(ToggleGroup toggleGroup) {
        int i = 0;
        for (Toggle toggle : toggleGroup.getToggles()) {
            if (toggle.isSelected()) {
                return i;
            }
            i++;
        }
        return -1;
    }

    private Figure getFigureByName(String name) {
        for (Figure figure : figureList) {
            if (figure.getName().equals(name)) return figure;
        }
        return null;
    }

    private void createMainWindow() {
        primaryStage.setTitle("OOP");
        primaryStage.setResizable(false);
        primaryStage.setMaximized(true);
        primaryStage.setFullScreenExitHint("");

        ColorPicker lineColorPicker = new ColorPicker();
        lineColorPicker.setValue(Color.BLACK);
        lineColorPicker.setTooltip(new Tooltip("Line color"));
        lineColorPicker.setCursor(Cursor.HAND);

        ToggleGroup toggleGroup = new ToggleGroup();

        VBox vBox = new VBox(10);
        vBox.getChildren().add(new VBox(20));

        for (Figure figure : types.getFigureTypes()) {
            ToggleButton btn = new ToggleButton("", createImageView(figure.getClass().getSimpleName() + ".png"));
            btn.setTooltip(new Tooltip(figure.getClass().getSimpleName()));
            btn.setCursor(Cursor.HAND);
            btn.setPrefSize(50, 50);
            btn.setToggleGroup(toggleGroup);
            vBox.getChildren().add(btn);
        }

        Button btnSave = new Button("Save");
        btnSave.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                JSONSerializer<Figure> serializer = new JSONSerializer<>();
                //BinarySerializer<Figure> serializer = new BinarySerializer<>();

                serializer.setSerializableContent(figureList);
                try {
                    serializer.serialize();
                } catch (IOException e) {
                    System.out.println(e);
                }


            }
        });

        Button btnLoad = new Button("Load");
        btnLoad.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                figureChoiceBox.getItems().clear();
                figureCount = 0;
                JSONSerializer<Figure> serializer = new JSONSerializer<>();
                //BinarySerializer<Figure> serializer = new BinarySerializer<>();
                try {
                    fillFigureList(serializer.deserialize());
                    for (Figure figure : figureList) {
                        figure.setGc(gc);
                        figure.draw();
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }


            }
        });

        figureChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if ((Integer) t1 != -1) {
                    changeSelected = getFigureByName(figureChoiceBox.getItems().get((Integer) t1));
                    String[] properties = changeSelected.getChangeableProperties().split(" ");
                    //System.out.println(properties[0]);
                    propertyChoiceBox.getItems().clear();
                    for (int i = 0; i < properties.length; i++) {
                        propertyChoiceBox.getItems().add(properties[i]);
                    }
                }
            }
        });

        Button btnChange = new Button("Change");
        btnChange.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                createChangeWindow();
            }
        });

        vBox.getChildren().add(lineColorPicker);
        vBox.getChildren().add(btnSave);
        vBox.getChildren().add(btnLoad);
        vBox.getChildren().add(figureChoiceBox);
        vBox.getChildren().add(btnChange);
        vBox.getChildren().add(new VBox(20));
        HBox figureBox = new HBox(10);
        figureBox.getChildren().add(new HBox(20));
        figureBox.getChildren().add(vBox);
        figureBox.getChildren().add(new HBox(20));
        figureBox.setStyle("-fx-border-color: Gray; -fx-border-width: 3");
        figureBox.setMaxWidth(screenWidth / 20);
        figureBox.setMinWidth(screenWidth / 20);

        mainPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    if (getSelectedButtonNumber(toggleGroup) != -1) {
                        if (drawSelected == null) {
                            drawSelected = new FigureTypes().getFigureTypes().get(getSelectedButtonNumber(toggleGroup));
                            figureCount++;
                            figureList.add(drawSelected);
                            drawSelected.setName(drawSelected.getClass().getSimpleName() + "_" + figureCount);
                            figureChoiceBox.getItems().add(drawSelected.getClass().getSimpleName() + "_" + figureCount);
                            drawSelected.setGc(gc);
                            drawSelected.setLineColor(lineColorPicker.getValue());
                        }
                        if ((drawSelected != null) && !drawSelected.isComplete()) {
                            drawSelected.onClick(MouseInfo.getPointerInfo().getLocation().getX() - canvas.getLayoutX() - primaryStage.getX() - 10,
                                    MouseInfo.getPointerInfo().getLocation().getY() - canvas.getLayoutY() - primaryStage.getY() - 32);
                        }
                        if ((drawSelected != null) && drawSelected.isComplete()) {
                            drawSelected = null;
                        }
                    }
                }
            }
        });

        mainPane.getChildren().add(figureBox);
        mainPane.getChildren().add(canvas);
        mainScene = new Scene(mainPane, screenWidth, screenHeight);
        primaryStage.setScene(mainScene);
        primaryStage.show();
        canvas.setWidth(primaryStage.getWidth() / 20 * 19 - 13);
        canvas.setHeight(primaryStage.getHeight() - 40);
    }

    private void createChangeWindow() {
        if (changeStage == null) {
            changeStage = new Stage();
        }
        if (!changeStage.isShowing()) {
            changeStage = new Stage();
            VBox vBox = new VBox();

            propertyChoiceBox.setPrefSize(100, 30);

            TextField propertyField = new TextField();
            propertyField.setPrefSize(200, 30);

            ColorPicker picker = new ColorPicker();
            picker.setPrefSize(50, 30);

            Button btnApply = new Button("Apply");
            btnApply.setPrefSize(100, 30);
            btnApply.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (propertyChoiceBox.getSelectionModel().getSelectedItem() != null) {
                        if(propertyChoiceBox.getSelectionModel().getSelectedItem().equals("lineColor")){
                            changeSelected.changeProperty(propertyChoiceBox.getSelectionModel().getSelectedItem(),
                                    (int)(picker.getValue().getRed() * 255) + " " + (int)(picker.getValue().getGreen() * 255) + " " + (int)(picker.getValue().getBlue() * 255));
                        } else {
                            changeSelected.changeProperty(propertyChoiceBox.getSelectionModel().getSelectedItem(), propertyField.getText());
                        }
                        changeStage.close();
                        update();
                    }
                }
            });

            Button btnDelete = new Button("Delete");
            btnDelete.setPrefSize(100, 30);
            btnDelete.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    removeFigure(changeSelected);
                    changeStage.close();
                }
            });

            HBox changePropertyBox = new HBox(10);
            changePropertyBox.getChildren().add(propertyChoiceBox);
            changePropertyBox.getChildren().add(propertyField);
            changePropertyBox.getChildren().add(picker);
            changePropertyBox.getChildren().add(btnApply);
            changePropertyBox.getChildren().add(btnDelete);

            vBox.getChildren().add(changePropertyBox);

            Scene scene = new Scene(vBox, 600, 600);
            changeStage.setScene(scene);
            changeStage.show();
        } else {
            changeStage.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
