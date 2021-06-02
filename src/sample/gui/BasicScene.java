package sample.gui;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Text;
import sample.logic.PersonaException;
import sample.logic.entities.Persona;
import sample.logic.services.IPersonaServices;
import sample.logic.services.implementación.PersonaServices;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BasicScene extends Application  {

    //Visual Properties
    private Scene scene;
    private TableView<Persona> personasTable;
    private TextField nameInput;
    private TextField lastNameInput;
    private TextField ageInput;
    private Button addPersona;
    private Button deletePersona;

    //Logic Properties
    private IPersonaServices personaServices;

    //Menu
    private MenuBar menuBar;
    private Map<String, MenuItem> fileManuItems;

    @Override
    public void start(Stage primaryStage) throws Exception{

        setup();
        behaviour(primaryStage);

        primaryStage.setTitle("Sabana Example");
        primaryStage.setScene(this.scene);
        primaryStage.show();
    }
    public void behaviour(Stage stage)
    {
        this.personaServices = new PersonaServices();


        personasTable.setItems((ObservableList<Persona>) personaServices.getAll());
        addPersona.setOnAction(e ->
        {
            try {
                Persona p = new Persona(nameInput.getText(),lastNameInput.getText(), ageInput.getText());
                this.personaServices.insert(p);
                nameInput.clear();
                lastNameInput.clear();
                ageInput.clear();
            } catch (PersonaException personaException) {
                personaException.printStackTrace();
            }
        });
        deletePersona.setOnAction(e ->
        {
            this.personaServices.delete(personasTable.getSelectionModel().getSelectedItems());

        });

        fileManuItems.get("Export").setOnAction(e ->
        {
            try {
                this.personaServices.export();
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        });
        fileManuItems.get("Import").setOnAction(e->
        {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select personas file");
            File fiel = fileChooser.showOpenDialog(stage);
            if (fiel == null)
            {
                System.out.println("No file");
            }
            else
            {
                try
                {
                    this.personaServices.importPersonas(fiel);
                    this.personaServices.getAll().stream().forEach(p-> System.out.println(p.toString()));
                }
                catch (IOException | PersonaException ioException)
                {
                    ioException.printStackTrace();
                }
            }
        });
    }
    public void setup() throws PersonaException {

        setupInputs();
        setupMenu();
        setupTable();
        setUpCrud();

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(nameInput,lastNameInput,ageInput,addPersona,deletePersona);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20,20,20,20));
        layout.getChildren().addAll(personasTable, hBox);

        BorderPane layout2 = new BorderPane();
        layout2.setTop(menuBar);

        scene = new Scene(layout2, 1000, 1000);

    }
    private void setupTable()
    {
        TableColumn<Persona, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setMaxWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Persona, String> lastNameColumn = new TableColumn<>("LastName");
        lastNameColumn.setMaxWidth(200);
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("last"));

        TableColumn<Persona, Integer> ageColumn = new TableColumn<>("Age");
        ageColumn.setMaxWidth(200);
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<Persona, UUID> idColumn = new TableColumn<>("Id");
        idColumn.setMaxWidth(200);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        personasTable = new TableView<>();
        personasTable.getColumns().addAll(nameColumn,lastNameColumn,ageColumn,idColumn);

    }
    private void setUpCrud()
    {
        this.addPersona = new Button();
        this.addPersona.setText("Add");
        this.addPersona.setMinWidth(30);

        this.deletePersona = new Button();
        this.deletePersona.setText("Delete");
        this.deletePersona.setMinWidth(30);
    }
    private void setupInputs()
    {
        nameInput = new TextField();
        nameInput.setPromptText("name");
        nameInput.setMinWidth(50);


        lastNameInput = new TextField();
        lastNameInput.setPromptText("lastName");
        lastNameInput.setMinWidth(50);

        ageInput = new TextField();
        ageInput.setPromptText("age");
        ageInput.setMinWidth(50);
    }
    private void setupMenu()
    {
        Menu fileMenu = new Menu("File");

        fileManuItems = new HashMap<>();
        fileManuItems.put("Import",new MenuItem("Import"));
        fileManuItems.put("Export",new MenuItem("Export"));

        fileMenu.getItems().add(fileManuItems.get("Import"));
        fileMenu.getItems().add(fileManuItems.get("Export"));

        menuBar = new MenuBar();
        menuBar.getMenus().add(fileMenu);
    }


    public static void main(String[] args) {
        launch(args);
    }

}
