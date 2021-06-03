package gui;

import javafx.application.Application;
import javafx.collections.FXCollections;
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

import javax.swing.text.LabelView;
import javax.xml.crypto.NodeSetData;
import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.Map;

public class MainScene extends Application {

    //Vbox
    private VBox layout2;
    private VBox layout3;
    private VBox infoLayout;
    private VBox crudVBox;
    private VBox summaryLayout = new VBox();

    //Hbox
    private HBox hBox1;
    private HBox hBox2;
    private HBox hBox3;


    //Buttons
    private Button selectPersona;
    private Button addPersona;
    private Button deletePersona;
    private Button editPersona;

    //TextFields
    private TextField nameInput;
    private TextField lastNameInput;
    private TextField ageInput;

    //ChoiceBoxes
    private ChoiceBox isVictim;
    private ChoiceBox side;
    private ChoiceBox aggression;

    //Info Labels
    private Label nameTitle;
    private Label ageInfo;
    private Label victimInfo;
    private Label aggressionInfo;
    private Label sideInfo;

    //Summary Labels
    private Label totalVictimsNumber;
    private Label totalVictim;

    //VisualProperties
    private Scene scene;
    private Scene scene2;
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

        addPersona.setOnAction(e ->
        {
            boolean victim = false;
            Enum aggressionType = AggressionType.NO_APLICA;
            Enum sideType = null;

            if (isVictim.getSelectionModel().getSelectedItem().equals("SI"))
                victim=true;
            if (aggression.getSelectionModel().getSelectedItem().equals("VIOLENCIA SEXUAL"))
                aggressionType = AggressionType.VIOLENCIA_SEXUAL;
            if (aggression.getSelectionModel().getSelectedItem().equals("VIOLENCIA HOMICIDA CON ARMAS"))
                aggressionType = AggressionType.VIOLENCIA_HOMICIDA_CON_ARMAS;
            if (aggression.getSelectionModel().getSelectedItem().equals("VIOLENCIA CON ARMAS"))
                aggressionType = AggressionType.VIOLENCIA_CON_ARMAS;
            if (side.getSelectionModel().getSelectedItem().equals("POLICIA"))
                sideType = Side.POLICE;
            if (side.getSelectionModel().getSelectedItem().equals("MANIFESTANTE"))
                sideType = Side.CIVILIAN;



            try {
                int age = Integer.parseInt(ageInput.getText());
                if (age < 0) throw new PersonaException(PersonaException.BAD_AGE_LOWER);
                if (age > 120) throw new PersonaException(PersonaException.BAD_AGE_UPPER);
                Persona p = new Persona(nameInput.getText(),lastNameInput.getText(),age,victim,aggressionType,sideType);
                this.personaServices.insert(p);
                nameInput.clear();
                lastNameInput.clear();
                ageInput.clear();
                isVictim.getSelectionModel().clearSelection();
                side.getSelectionModel().clearSelection();
                aggression.getSelectionModel().clearSelection();

            } catch (PersonaException personaException) {
                personaException.printStackTrace();

            }
            catch (NumberFormatException er)
            {
                try {
                    throw new PersonaException(PersonaException.BAD_AGE + " : " + er.getMessage());
                } catch (PersonaException personaException) {
                    personaException.printStackTrace();
                }
            }
            totalVictimsNumber.setText(String.valueOf(personaServices.getAll().size()));

        });

        selectPersona.setOnAction(e ->
        {
            String isVictim1 = "NO";

            nameTitle.setText( personasTable.getSelectionModel().getSelectedItem().getFull());
            ageInfo.setText("Edad: "+personasTable.getSelectionModel().getSelectedItem().getAge());
            sideInfo.setText("Bando: "+personasTable.getSelectionModel().getSelectedItem().getSide());
            if (personasTable.getSelectionModel().getSelectedItem().isVictim())
                isVictim1="SI";
            victimInfo.setText("Es victima?: "+isVictim1);
            aggressionInfo.setText("Tipo de agresion: "+ personasTable.getSelectionModel().getSelectedItem().getAggressionType());

        });

        deletePersona.setOnAction(e ->
        {
            this.personaServices.delete(personasTable.getSelectionModel().getSelectedItems());
            totalVictimsNumber.setText(String.valueOf(personaServices.getAll().size()));
        });
        fileMenuItems.get("Export").setOnAction(e ->
        {
            try {
                this.personaServices.export();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        editPersona.setOnAction(e ->
        {
            boolean victim = false;
            Enum aggressionType = AggressionType.NO_APLICA;
            Enum sideType = null;

            if(isVictim.equals("SI"))
                victim=true;
            if (aggression.getSelectionModel().getSelectedItem().equals("VIOLENCIA SEXUAL"))
                aggressionType = AggressionType.VIOLENCIA_SEXUAL;
            if (aggression.getSelectionModel().getSelectedItem().equals("VIOLENCIA HOMICIDA CON ARMAS"))
                aggressionType = AggressionType.VIOLENCIA_HOMICIDA_CON_ARMAS;
            if (aggression.getSelectionModel().getSelectedItem().equals("VIOLENCIA CON ARMAS"))
                aggressionType = AggressionType.VIOLENCIA_CON_ARMAS;
            if (side.getSelectionModel().getSelectedItem().equals("POLICIA"))
                sideType = Side.POLICE;
            if (side.getSelectionModel().getSelectedItem().equals("MANIFESTANTE"))
                sideType = Side.CIVILIAN;

            this.personaServices.edit(nameInput.getText(),lastNameInput.getText(),Integer.parseInt(ageInput.getText()),victim,aggressionType,sideType,personasTable.getSelectionModel().getSelectedItem());
            nameInput.clear();
            lastNameInput.clear();
            ageInput.clear();
            isVictim.getSelectionModel().clearSelection();
            side.getSelectionModel().clearSelection();
            aggression.getSelectionModel().clearSelection();
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
        addPersona.setMinWidth(150);
        addPersona.setPadding(new Insets(20,20,20,20));

        deletePersona = new Button("Delete");
        deletePersona.setMinWidth(150);
        deletePersona.setPadding(new Insets(20));

        editPersona = new Button("Editar");
        editPersona.setMinWidth(150);
        editPersona.setPadding(new Insets(20));


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

        isVictim = new ChoiceBox(FXCollections.observableArrayList("SI","NO"));
        isVictim.setMinWidth(150);

        side = new ChoiceBox(FXCollections.observableArrayList("POLICIA","MANIFESTANTE"));
        side.setMinWidth(150);

        aggression = new ChoiceBox(FXCollections.observableArrayList("VIOLENCIA HOMICIDA CON ARMAS","VIOLENCIA CON ARMAS","VIOLENCIA SEXUAL", "NO APLICA"));



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
        Stage summary = new Stage();

        this.scene2 = new Scene(summaryLayout,900,500);

        fileMenuItems.put("Show summary",new MenuItem("Show summary"));

        summaryMenu.getItems().add(fileMenuItems.get("Show summary"));
        summaryMenu.setOnAction(e->
        {
            summary.show();
            summary.setScene(scene2);
            totalVictimsNumber.setText(String.valueOf(personaServices.getAll().size()));
        });

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
        hBox1.getChildren().addAll(addPersona,deletePersona,editPersona);
        hBox1.setPadding(new Insets(20,0,20,0));
        hBox1.setSpacing(50);
        hBox2 = new HBox();
        hBox2.getChildren().addAll(nameInput,lastNameInput,ageInput);
        hBox2.setPadding(new Insets(20,0,20,0));
        hBox2.setSpacing(10);

        Label isVictimChoice = new Label("Es victima?");
        Label sideChoice = new Label("Bando");
        Label aggressionChoice = new Label("Tipo de agresion");

        VBox h1 = new VBox();
        h1.getChildren().addAll(isVictimChoice,isVictim);
        VBox h2 = new VBox();
        h2.getChildren().addAll(sideChoice,side);
        VBox h3 = new VBox();
        h3.getChildren().addAll(aggressionChoice,aggression);

        hBox3 = new HBox();
        hBox3.getChildren().addAll(h1,h2,h3);
        hBox3.setSpacing(12);

        crudVBox= new VBox();
        crudVBox.getChildren().addAll(hBox2,hBox3,hBox1);

        infoLayout.getChildren().addAll(ageInfo,sideInfo,victimInfo,aggressionInfo,selectPersona);
        layout2.getChildren().addAll(nameTitle,infoLayout,crudVBox);

        //Summary menu layout


        HBox f = new HBox();

        f.setPadding(new Insets(20,0,0,80));

        totalVictim = new Label("Victimas de agresion fisica grave o letal");
        totalVictim.setMinWidth(650);
        totalVictim.setPadding(new Insets(11,0,0,0));
        totalVictim.setAlignment(Pos.CENTER_RIGHT);
        totalVictim.setFont(new Font(35));

        totalVictimsNumber = new Label();
        //totalVictimsNumber.setMinWidth(20);
        totalVictimsNumber.setAlignment(Pos.CENTER_LEFT);
        totalVictimsNumber.setFont(new Font(50));
        f.getChildren().addAll(totalVictimsNumber,totalVictim);


        GridPane g = new GridPane();


        summaryLayout.getChildren().addAll(f,g);
    }
}
