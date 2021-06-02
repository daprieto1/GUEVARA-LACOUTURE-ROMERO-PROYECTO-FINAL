package gui;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import logic.PersonaException;
import logic.entities.AggressionType;
import logic.entities.Persona;
import logic.entities.Side;
import logic.services.impl.IPersonaServices;
import logic.services.impl.PersonaServices;

import javax.xml.crypto.NodeSetData;
import java.util.HashMap;
import java.util.Map;

public class MainScene extends Application {

    //Vbox
    private VBox layout2;
    private VBox layout3;

    //VisualProperties
    private Scene scene;
    private TableView<Persona> personasTable;
    private Label nameTitle;
    private Label ageInfo;
    private Label victimInfo;
    private Label aggressionInfo;
    private Label sideInfo;
    private Button selectPersona;

    //Menu
    private MenuBar menuBar;
    private Map<String, MenuItem> fileMenuItems;

    //Logic Properties
    private IPersonaServices personaServices;
    //Persona
    private Persona mia;

    {
        try {
            mia = new Persona("Mia","Lacouture",18,true, AggressionType.VIOLENCIA_HOMICIDA_CON_ARMAS, Side.CIVILIAN);
        } catch (PersonaException e) {
            e.printStackTrace();
        }
    }
    private Persona sebas;

    {
        try {
            sebas = new Persona("Sebastian","Guevara",19,false, AggressionType.VIOLENCIA_SEXUAL, Side.POLICE);
        } catch (PersonaException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        setUp();
        behaviour(primaryStage);
        primaryStage.setTitle("CRUD");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    private void behaviour(Stage stage)
    {


        this.personaServices = new PersonaServices();
        this.personaServices.insert(mia);
        this.personaServices.insert(sebas);

        personasTable.setItems((ObservableList<Persona>) this.personaServices.getAll());

        selectPersona.setOnAction(e ->
        {
            nameTitle.setText( personasTable.getSelectionModel().getSelectedItem().getFull());
        });



    }
    private void setUp()
    {
        setUpCrud();
        setUpInputs();
        setupTable();
        setUpMenu();
        setUpvBox();

        BorderPane layout = new BorderPane();
        layout.setLeft(layout3);
        layout.setRight(layout2);
        layout.setTop(menuBar);




        scene = new Scene(layout,1100,800);
    }
    private void setUpCrud()
    {
        selectPersona = new Button("Select");
        selectPersona.setMinWidth(30);
        selectPersona.setPadding(new Insets(20));
    }
    private void setupTable()
    {


        TableColumn<Persona, String> actoresColumn= new TableColumn<>("Actores");

        actoresColumn.setCellValueFactory(new PropertyValueFactory<>("full"));
        actoresColumn.setMinWidth(400);

        personasTable = new TableView<>();
        personasTable.setMinWidth(400);
        personasTable.setMinHeight(700);
        personasTable.getColumns().addAll(actoresColumn);
        personasTable.setBorder(new Border(new BorderStroke(Color.valueOf("#4498C4"), BorderStrokeStyle.SOLID,CornerRadii.EMPTY,new BorderWidths(5),new Insets(20,20,20,20))));

    }
    private void setUpInputs()
    {

    }
    private void setUpMenu()
    {
        //Directory Menu
        Menu directoryMenu = new Menu("Directory");

        fileMenuItems = new HashMap<>();
        fileMenuItems.put("Import", new MenuItem("Import"));
        fileMenuItems.put("Export", new MenuItem("Export"));

        directoryMenu.getItems().add(fileMenuItems.get("Import"));
        directoryMenu.getItems().add(fileMenuItems.get("Export"));

        Menu summaryMenu = new Menu("Summary");

        menuBar = new MenuBar();
        menuBar.getMenus().addAll(directoryMenu,summaryMenu);

    }
    private void setUpvBox()
    {
        layout2 = new VBox();


        layout3 = new VBox();
        layout3.getChildren().addAll(personasTable,selectPersona);



        //NameTitle
        this.nameTitle = new Label("Principales Actores del Paro");
        this.nameTitle.setMinWidth(600);
        this.nameTitle.setAlignment(Pos.CENTER);
        this.nameTitle.setPadding(new Insets(20,20,20,20));
        this.nameTitle.setBorder(new Border(new BorderStroke(Color.valueOf("#4498C4"), BorderStrokeStyle.SOLID,CornerRadii.EMPTY,new BorderWidths(5),new Insets(20,50,20,10))));
        this.nameTitle.setFont(Font.font("Arial", FontWeight.BOLD,35));

        //
        VBox infoLayout = new VBox();


        layout2.getChildren().addAll(nameTitle,infoLayout);
    }
}
