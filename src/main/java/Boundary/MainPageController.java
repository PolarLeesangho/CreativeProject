package Boundary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import Enum.Town;
import Enum.Village;
import Enum.Sectors;

import java.io.IOException;


public class MainPageController {
    @FXML
    private ChoiceBox<String> townChoiceBox;
    @FXML
    private ChoiceBox<String> villageChoiceBox;
    @FXML
    private ChoiceBox<String> sectorsChoiceBox;

    private String choosedTown;
    private String choosedVillage;
    private String choosedSectors;

    @FXML
    private Button startBtn;
    @FXML
    private Button loginBtn;
    public void initialize() {
        initChoiceBox();
        choiceBoxAction();
    }
    private void initChoiceBox()
    {
        ObservableList<String> townItems = FXCollections.observableArrayList(
                Town.대전광역시.toString()
        );
        townChoiceBox.setItems(townItems);

        ObservableList<String> villageItems = FXCollections.observableArrayList();
        for(Village village : Village.getList()){
            villageItems.add(village.getName());
        }
        villageChoiceBox.setItems(villageItems);

        ObservableList<String> sectorItems = FXCollections.observableArrayList();
        for(Sectors sectors : Sectors.getList()){
            sectorItems.add(sectors.getIndustry());
        }
        sectorsChoiceBox.setItems(sectorItems);
    }
    private void choiceBoxAction()
    {
        // 선택 이벤트 처리
        townChoiceBox.setOnAction(event -> {
            choosedTown = townChoiceBox.getValue();
        });
        villageChoiceBox.setOnAction(event -> {
            choosedVillage = villageChoiceBox.getValue();
        });
        sectorsChoiceBox.setOnAction(event -> {
            choosedSectors = sectorsChoiceBox.getValue();
        });
    }

    @FXML
    private void handleStartBtn() throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view/StatisticsPage.fxml"));
        Parent otherPage = loader.load();

        Scene currentScene = startBtn.getScene();
        currentScene.setRoot(otherPage);
        Stage primaryStage = (Stage) currentScene.getWindow();
        primaryStage.setTitle("Statistics Page");
    }

    @FXML
    private void handleLoginBtn() throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("view/ManagerLoginPage.fxml"));
        Parent otherPage = loader.load();

        Scene currentScene = loginBtn.getScene();
        currentScene.setRoot(otherPage);
        Stage primaryStage = (Stage) currentScene.getWindow();
        primaryStage.setTitle("Login Page");
        primaryStage.setWidth(600);
        primaryStage.setHeight(400);
    }


}
