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
    private VBox infoLayout;
    private VBox crudVBox;

    //Hbox
    private HBox hBox1;
    private HBox hBox2;
    private HBox hBox3;

    //Buttons
    private Button selectPersona;
    private Button addPersona;
    private Button deletePersona;

    //TextFields
    private TextField nameInput;
    private TextField lastNameInput;
    private TextField ageInput;

    //Labels
    private Label nameTitle;
    private Label ageInfo;
    private Label victimInfo;
    private Label aggressionInfo;
    private Label sideInfo;

    //VisualProperties
    private Scene scene;
    private TableView<Persona> personasTable;

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
            String isVictim = "NO";

            nameTitle.setText( personasTable.getSelectionModel().getSelectedItem().getFull());
            ageInfo.setText("Edad: "+personasTable.getSelectionModel().getSelectedItem().getAge());
            sideInfo.setText("Bando: "+personasTable.getSelectionModel().getSelectedItem().getSide());
            if (personasTable.getSelectionModel().getSelectedItem().isVictim())
                isVictim="SI";
            victimInfo.setText("Es victima?: "+isVictim);
            aggressionInfo.setText("Tipo de agresion: "+ personasTable.getSelectionModel().getSelectedItem().getAggressionType());
        });



    }
    private void setUp()
    {
        setUpCrud();
        setUpInputs();
        setupTable();
        setUpMenu();
        setUplayout();

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

        addPersona = new Button("Add");
        addPersona.setMinWidth(250);
        addPersona.setPadding(new Insets(20,20,20,20));



        deletePersona = new Button("Delete");
        deletePersona.setMinWidth(250);
        deletePersona.setPadding(new Insets(20));


    }
    private void setupTable()
    {


        TableColumn<Persona, String> actoresColumn= new TableColumn<>("Actores");

        actoresColumn.setCellValueFactory(new PropertyValueFactory<>("full"));
        actoresColumn.setMinWidth(400);

        personasTable = new TableView<>();
        personasTable.setMinWidth(400);
        personasTable.setMinHeight(780);
        personasTable.getColumns().addAll(actoresColumn);
        personasTable.setBorder(new Border(new BorderStroke(Color.valueOf("#4498C4"), BorderStrokeStyle.SOLID,CornerRadii.EMPTY,new BorderWidths(5),new Insets(20,20,20,20))));

    }
    private void setUpInputs()
    {
        nameInput = new TextField();
        nameInput.setPromptText("Nombre");
        nameInput.setMinWidth(177);

        lastNameInput = new TextField();
        lastNameInput.setPromptText("Apellido");
        lastNameInput.setMinWidth(177);

        ageInput = new TextField();
        ageInput.setPromptText("Edad");
        ageInput.setMinWidth(177);


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
    private void setUplayout()
    {
        layout2 = new VBox();


        layout3 = new VBox();
        layout3.getChildren().addAll(personasTable);



        //NameTitle
        this.nameTitle = new Label("Principales Actores del Paro");
        this.nameTitle.setMinWidth(600);
        this.nameTitle.setAlignment(Pos.CENTER);
        this.nameTitle.setPadding(new Insets(20,20,20,20));
        this.nameTitle.setBorder(new Border(new BorderStroke(Color.valueOf("#4498C4"), BorderStrokeStyle.SOLID,CornerRadii.EMPTY,new BorderWidths(5),new Insets(20,50,20,10))));
        this.nameTitle.setFont(Font.font("Arial", FontWeight.BOLD,35));

        //
        infoLayout = new VBox();

        this.ageInfo = new Label("Edad: ");
        this.ageInfo.setMinWidth(600);
        this.ageInfo.setAlignment(Pos.TOP_LEFT);
        this.ageInfo.setPadding(new Insets(5,20,5,20));
        this.ageInfo.setFont(new Font(20));

        this.sideInfo = new Label("Bando: ");
        this.sideInfo.setMinWidth(600);
        this.sideInfo.setAlignment(Pos.TOP_LEFT);
        this.sideInfo.setPadding(new Insets(5,20,5,20));
        this.sideInfo.setFont(new Font(20));

        this.victimInfo = new Label("Es victima?: ");
        this.victimInfo.setMinWidth(600);
        this.victimInfo.setAlignment(Pos.TOP_LEFT);
        this.victimInfo.setPadding(new Insets(5,20,5,20));
        this.victimInfo.setFont(new Font(20));

        this.aggressionInfo = new Label("Tipo de agresion: ");
        this.aggressionInfo.setMinWidth(600);
        this.aggressionInfo.setAlignment(Pos.TOP_LEFT);
        this.aggressionInfo.setPadding(new Insets(5,20,30,20));
        this.aggressionInfo.setFont(new Font(20));

        selectPersona.setMinWidth(550);
        selectPersona.setAlignment(Pos.BOTTOM_CENTER);



        hBox1 = new HBox();
        hBox1.getChildren().addAll(addPersona,deletePersona);
        hBox1.setPadding(new Insets(20,0,20,0));
        hBox1.setSpacing(50);
        hBox2 = new HBox();
        hBox2.getChildren().addAll(nameInput,lastNameInput,ageInput);
        hBox2.setPadding(new Insets(20,0,20,0));
        hBox2.setSpacing(10);
        hBox3 = new HBox();


        crudVBox= new VBox();
        crudVBox.getChildren().addAll(hBox2,hBox3,hBox1);

        infoLayout.getChildren().addAll(ageInfo,sideInfo,victimInfo,aggressionInfo,selectPersona);
        layout2.getChildren().addAll(nameTitle,infoLayout,crudVBox);
    }
}
