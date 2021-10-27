/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia;

import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

//
/**
 *
 * @author 44280
 */
public class TaxKeeperFx extends Application {

    GridPane RegPane, LoginPane, HomePane, SearchPane, ClientsPane;
    GridPane OverviewPane, DocumentsPane, AddClientFormPane;
    GridPane UpdatePane, AddUpdateDocumentPane;
    VBox logVBox;

    // log page
    Button btnBackHome;
    TableView<Record> tblLog;
    Label lblLog;
    Scene logPageScene;

    // login and register
    Button btnLogin, btnRegPage, btnBack, btnRegisterAcc;
    Label lblLogUsername, lblLogPassword, lblLogError, lblLogTopic;
    Label lblRegTopic, lblRegError, lblRegPassword, lblRegUsername, lblRegAdminPassword, lblRegReEnterPass;
    TextField txtLogUsername, txtRegUsername;
    PasswordField txtLogPassword, txtRegPassword, txtRegCode, txtRegReEnterPass;
    User user;
    Stage stage;
    Scene logScene, regScene;

    // home and search
    TextField txtSearch;
    Label lblTitleSearch, lblTitleHome, lblSearch, lblNotFound, lblGoToClients, lblGoToSearch, lblGenRegCode, lblLogout, lblLogPage;
    ArrayList<Button> btnSearchRes;
    Button btnClients, btnSearch, btnHomeFromSearch, btnEnter, btnGenRegCode, btnLogout, btnLogPage;
    Scene sceneSearch, sceneHome;
    ArrayList<Client> searchResClients;
    ArrayList<Document> searchResDocs;

    // clients and overview
    ArrayList<Button> btnClientList;
    Button btnAddClient, btnUploadDoc, btnAddTaxCalc, btnEditClientProfile, btnHomeFromClients;
    Button btnOverviewToClients, btnOverviewToSearch, btnDeleteClient, btnUpdateClient, btnAddDocument, btnUploadImage, btnUploadDocument;
    Label lblTitleClients, lblTitleClientView, lblName, lblAddress, lblPostCode, lblTaxIdNumber, lblClientIcon, lblDocuments;
    ArrayList<Label> lblClientDocs;
    ArrayList<Button> btnDeleteDoc, btnViewDoc, btnUpdateDoc;
    Scene sceneClients, sceneView;
    ArrayList<Client> clients;

    // add client form
    Label lblTitleAddClient, lblAddClientName, lblAddClientAddress, lblAddClientPostCode, lblAddClientError, lblAddTaxIdNum;
    TextField txtAddClientName, txtAddClientAddress, txtAddClientPostCode, txtAddTaxIdNum;
    Button btnEnterAddClient, btnBackToClients;
    Scene sceneAddClient;

    // update client form
    Label lblTitleUpdate, lblUpdateName, lblUpdateAddress, lblUpdatePostCode, lblUpdateError, lblUpdateTaxIdNumber;
    TextField txtUpdateName, txtUpdateAddress, txtUpdatePostCode, txtUpdateTaxIdNumber;
    Button btnReturnToView, btnUpdateClientInfo;
    Scene sceneUpdateClient;

    // add and update document form
    Label lblTitleDocumentForm, lblDocName, lblTaxObjectNumber, lblPropertyAddress, lblCertNumber;
    Label lblLandArea, lblBuildingArea, lblMarketPrice, lblLandTOSV, lblBuildingTOSV, lblMoneyDeposited, lblAddDocError;
    TextField txtDocName, txtTaxObjectNumber, txtPropertyAddress, txtCertNumber, txtLandArea, txtBuildingArea;
    TextField txtMarketPrice, txtLandTOSV, txtBuildingTOSV, txtMoneyDeposited;
    Button btnCancelAddDoc, btnConfirmAddUpdateDoc;
    Scene sceneAddUpdateDocumentForm;
    
    /*
    * Precondition: Program has just been opened
    * Postcondition: User class is instantiated, All GUI components are instantiated
    */
    public TaxKeeperFx() throws ClassNotFoundException {
        user = new User();

        // log page
        btnBackHome = new Button("Home");
        tblLog = new TableView<Record>();
        lblLog = new Label("Log");

        // update client 
        lblTitleUpdate = new Label("Update Client");
        lblUpdateName = new Label("Name");
        lblUpdateAddress = new Label("Address");
        lblUpdatePostCode = new Label("Post Code");
        txtUpdateName = new TextField();
        txtUpdateAddress = new TextField();
        txtUpdatePostCode = new TextField();
        btnReturnToView = new Button("Cancel");
        btnReturnToView.setPrefSize(80, 10);
        btnUpdateClientInfo = new Button("Update");
        btnUpdateClientInfo.setPrefSize(80, 10);
        lblUpdateError = new Label();
        lblUpdateTaxIdNumber = new Label("Tax ID Number");
        txtUpdateTaxIdNumber = new TextField();

        // login
        lblLogTopic = new Label("Login");
        lblRegTopic = new Label("Register");
        btnLogin = new Button("Login");
        btnLogin.setPrefSize(80, 10);
        btnRegPage = new Button("Register");
        btnRegPage.setPrefSize(80, 10);
        btnBack = new Button("Back");
        btnBack.setPrefSize(80, 10);
        btnRegisterAcc = new Button("Register");
        btnRegisterAcc.setPrefSize(80, 10);
        lblLogUsername = new Label("Username");
        lblRegUsername = new Label("Username");
        lblLogPassword = new Label("Password");
        lblRegPassword = new Label("Password");

        // register
        lblLogError = new Label();
        lblRegError = new Label();
        txtLogUsername = new TextField();
        txtRegUsername = new TextField();
        txtLogPassword = new PasswordField();
        txtRegPassword = new PasswordField();
        lblRegAdminPassword = new Label("Registration Code");
        lblRegReEnterPass = new Label("Reenter Password");
        txtRegCode = new PasswordField();
        txtRegReEnterPass = new PasswordField();

        // home and search
        txtSearch = new TextField();
        lblTitleSearch = new Label("Tax Keeper");
        lblSearch = new Label("Search");
        lblTitleHome = new Label("Tax Keeper");
        btnSearchRes = new ArrayList<Button>();
        btnHomeFromSearch = new Button("Back to Home");
        btnEnter = new Button("Enter");
        btnClients = new Button();
        btnSearch = new Button();
        lblNotFound = new Label("Search results not found");
        lblNotFound.setFont(Font.font("Roboto", FontWeight.BOLD, 15));
        lblNotFound.setTextFill(Paint.valueOf("#ffffff"));
        lblGoToClients = new Label("Clients");
        lblGoToSearch = new Label("Search");
        btnGenRegCode = new Button();
        lblGenRegCode = new Label("Generate Code");
        lblGenRegCode.setFont(Font.font("Roboto", FontWeight.BOLD, 15));
        lblGenRegCode.setTextFill(Paint.valueOf("#ffffff"));
        btnLogout = new Button();
        lblLogout = new Label("Log out");
        lblLogout.setFont(Font.font("Roboto", FontWeight.BOLD, 15));
        lblLogout.setTextFill(Paint.valueOf("#ffffff"));
        lblLogPage = new Label("Logs");
        btnLogPage = new Button();

        // add client form
        lblTitleAddClient = new Label("Add New Client");
        lblAddClientName = new Label("Name");
        lblAddClientAddress = new Label("Address");
        lblAddClientPostCode = new Label("Post Code");
        lblAddClientError = new Label();
        txtAddClientName = new TextField();
        txtAddClientAddress = new TextField();
        txtAddClientPostCode = new TextField();
        btnEnterAddClient = new Button("Enter");
        btnEnterAddClient.setPrefSize(80, 10);
        btnBackToClients = new Button("Back");
        btnBackToClients.setPrefSize(80, 10);
        lblAddTaxIdNum = new Label("Tax ID Number");
        txtAddTaxIdNum = new TextField();

        // gridpanes & vbox
        LoginPane = new GridPane();
        RegPane = new GridPane();
        SearchPane = new GridPane();
        HomePane = new GridPane();
        ClientsPane = new GridPane();
        OverviewPane = new GridPane();
        AddClientFormPane = new GridPane();
        UpdatePane = new GridPane();
        AddUpdateDocumentPane = new GridPane();
        logVBox = new VBox();

        // client and overview
        btnClientList = new ArrayList<Button>();
        lblClientDocs = new ArrayList<Label>();
        btnDeleteDoc = new ArrayList<Button>();
        btnUploadDoc = new Button("Add Document");
        btnUploadDoc.setPrefSize(175, 10);
        btnAddTaxCalc = new Button("Add Tax Calculation");
        btnAddTaxCalc.setPrefSize(175, 10);
        user = new User();
        user.connectDB();
        btnAddClient = new Button("Add Client");
        lblTitleClients = new Label("Clients");
        lblTitleClientView = new Label("Client Overview");
        lblName = new Label();
        lblAddress = new Label();
        lblPostCode = new Label();
        btnHomeFromClients = new Button("Back to Home");
        btnHomeFromClients.setPrefSize(175, 10);
        lblClientIcon = new Label();
        btnOverviewToClients = new Button("Go to Clients");
        btnOverviewToClients.setPrefSize(175, 10);
        btnOverviewToSearch = new Button("Go to Search");
        btnOverviewToSearch.setPrefSize(175, 10);
        btnDeleteClient = new Button("Delete Client");
        btnDeleteClient.setPrefSize(175, 10);
        btnUpdateClient = new Button("Update Client");
        btnUpdateClient.setPrefSize(175, 10);
        lblDocuments = new Label("Documents");
        btnAddDocument = new Button("+ Add Document");
        btnAddDocument.setPrefSize(175, 10);
        lblTaxIdNumber = new Label();
        btnUploadImage = new Button("Upload Image");
        btnUploadImage.setPrefSize(175, 10);
        btnUploadDocument = new Button("Upload Document");
        btnUploadDocument.setPrefSize(175, 10);
        btnViewDoc = new ArrayList<Button>();
        btnUpdateDoc = new ArrayList<Button>();

        // add document form
        lblTitleDocumentForm = new Label();
        lblDocName = new Label("Document Name");
        lblTaxObjectNumber = new Label("Tax Object Number");
        lblPropertyAddress = new Label("Property Address");
        lblCertNumber = new Label("Certificate Number");
        lblLandArea = new Label("Land Area");
        lblBuildingArea = new Label("Building Area");
        lblMarketPrice = new Label("Market Price");
        lblLandTOSV = new Label("Land Tax Object Sales Value");
        lblBuildingTOSV = new Label("Building Tax Object Sales Value");
        lblMoneyDeposited = new Label("Money Deposited");
        txtDocName = new TextField();
        txtTaxObjectNumber = new TextField();
        txtPropertyAddress = new TextField();
        txtCertNumber = new TextField();
        txtLandArea = new TextField();
        txtBuildingArea = new TextField();
        txtMarketPrice = new TextField();
        txtLandTOSV = new TextField();
        txtBuildingTOSV = new TextField();
        txtMoneyDeposited = new TextField();
        btnCancelAddDoc = new Button("Cancel");
        btnConfirmAddUpdateDoc = new Button();
        lblAddDocError = new Label();
    }
    
    /*
    * Precondition: Program has just been opened
    * Postcondition: GUI components are organized into GridPanes, Scenes are
    *                instantiated, LoginPane is shown on stage, assign methods for buttons
    */
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;

        // log page
        logVBox.setSpacing(10);
        logVBox.setPadding(new Insets(10, 0, 0, 10));
        lblLog.setFont(Font.font("Roboto", FontWeight.BOLD, 30));

        TableColumn<Record, String> changeCol = new TableColumn("Change");
        changeCol.setMinWidth(200);
        changeCol.setCellValueFactory(new PropertyValueFactory<>("change"));

        TableColumn<Record, String> accCol = new TableColumn("Account");
        accCol.setMinWidth(100);
        accCol.setCellValueFactory(new PropertyValueFactory<>("account"));

        TableColumn<Record, String> timeCol = new TableColumn("Time");
        timeCol.setMinWidth(100);
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

       // populateLogTable();
        tblLog.setPrefWidth(300);
        tblLog.setPrefHeight(400);
        tblLog.getColumns().setAll(changeCol, accCol, timeCol);
        tblLog.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        logVBox.getChildren().addAll(btnBackHome, lblLog, tblLog);
        btnBackHome.setOnAction(e -> BackToHome());

        // add and update document form
        AddUpdateDocumentPane.setAlignment(Pos.CENTER);
        AddUpdateDocumentPane.setVgap(10);
        AddUpdateDocumentPane.setHgap(10);

        AddUpdateDocumentPane.add(lblTitleDocumentForm, 0, 0, 2, 1);
        AddUpdateDocumentPane.setHalignment(lblTitleDocumentForm, HPos.CENTER);
        lblTitleDocumentForm.setFont(Font.font("Roboto", FontWeight.BOLD, 30));
        lblTitleDocumentForm.setTextFill(Paint.valueOf("#ffffff"));
        AddUpdateDocumentPane.addRow(1, lblDocName, txtDocName);
        lblDocName.setFont(Font.font("Roboto", FontWeight.NORMAL, 15));
        lblDocName.setTextFill(Paint.valueOf("#ffffff"));
        AddUpdateDocumentPane.addRow(2, lblTaxObjectNumber, txtTaxObjectNumber);
        lblTaxObjectNumber.setFont(Font.font("Roboto", FontWeight.NORMAL, 15));
        lblTaxObjectNumber.setTextFill(Paint.valueOf("#ffffff"));
        AddUpdateDocumentPane.addRow(3, lblPropertyAddress, txtPropertyAddress);
        lblPropertyAddress.setFont(Font.font("Roboto", FontWeight.NORMAL, 15));
        lblPropertyAddress.setTextFill(Paint.valueOf("#ffffff"));
        AddUpdateDocumentPane.addRow(4, lblCertNumber, txtCertNumber);
        lblCertNumber.setFont(Font.font("Roboto", FontWeight.NORMAL, 15));
        lblCertNumber.setTextFill(Paint.valueOf("#ffffff"));
        AddUpdateDocumentPane.addRow(5, lblLandArea, txtLandArea);
        lblLandArea.setFont(Font.font("Roboto", FontWeight.NORMAL, 15));
        lblLandArea.setTextFill(Paint.valueOf("#ffffff"));
        AddUpdateDocumentPane.addRow(6, lblBuildingArea, txtBuildingArea);
        lblBuildingArea.setFont(Font.font("Roboto", FontWeight.NORMAL, 15));
        lblBuildingArea.setTextFill(Paint.valueOf("#ffffff"));
        AddUpdateDocumentPane.addRow(7, lblMarketPrice, txtMarketPrice);
        lblMarketPrice.setFont(Font.font("Roboto", FontWeight.NORMAL, 15));
        lblMarketPrice.setTextFill(Paint.valueOf("#ffffff"));
        AddUpdateDocumentPane.addRow(8, lblLandTOSV, txtLandTOSV);
        lblLandTOSV.setFont(Font.font("Roboto", FontWeight.NORMAL, 15));
        lblLandTOSV.setTextFill(Paint.valueOf("#ffffff"));
        AddUpdateDocumentPane.addRow(9, lblBuildingTOSV, txtBuildingTOSV);
        lblBuildingTOSV.setFont(Font.font("Roboto", FontWeight.NORMAL, 15));
        lblBuildingTOSV.setTextFill(Paint.valueOf("#ffffff"));
        AddUpdateDocumentPane.addRow(10, lblMoneyDeposited, txtMoneyDeposited);
        lblMoneyDeposited.setFont(Font.font("Roboto", FontWeight.NORMAL, 15));
        lblMoneyDeposited.setTextFill(Paint.valueOf("#ffffff"));
        AddUpdateDocumentPane.addRow(11, btnCancelAddDoc, btnConfirmAddUpdateDoc);
        AddUpdateDocumentPane.setHalignment(btnCancelAddDoc, HPos.LEFT);
        AddUpdateDocumentPane.setHalignment(btnConfirmAddUpdateDoc, HPos.RIGHT);
        AddUpdateDocumentPane.add(lblAddDocError, 0, 12, 2, 1);
        AddUpdateDocumentPane.setHalignment(lblAddDocError, HPos.RIGHT);
        lblAddDocError.setFont(Font.font("Roboto", FontWeight.NORMAL, 15));
        lblAddDocError.setTextFill(Paint.valueOf("#C5CAE9"));

        btnCancelAddDoc.setOnAction(e -> goBackClientOverview());

        // update client information
        UpdatePane.setAlignment(Pos.CENTER);
        UpdatePane.setVgap(10);
        UpdatePane.setHgap(10);

        UpdatePane.add(lblTitleUpdate, 0, 0, 2, 1);
        UpdatePane.setHalignment(lblTitleUpdate, HPos.CENTER);
        lblTitleUpdate.setFont(Font.font("Roboto", FontWeight.BOLD, 30));
        lblTitleUpdate.setTextFill(Paint.valueOf("#ffffff"));
        UpdatePane.addRow(1, lblUpdateName, txtUpdateName);
        lblUpdateName.setFont(Font.font("Roboto", FontWeight.NORMAL, 15));
        lblUpdateName.setTextFill(Paint.valueOf("#ffffff"));
        UpdatePane.addRow(2, lblUpdateAddress, txtUpdateAddress);
        lblUpdateAddress.setFont(Font.font("Roboto", FontWeight.NORMAL, 15));
        lblUpdateAddress.setTextFill(Paint.valueOf("#ffffff"));
        UpdatePane.addRow(3, lblUpdatePostCode, txtUpdatePostCode);
        lblUpdatePostCode.setFont(Font.font("Roboto", FontWeight.NORMAL, 15));
        lblUpdatePostCode.setTextFill(Paint.valueOf("#ffffff"));
        UpdatePane.addRow(4, lblUpdateTaxIdNumber, txtUpdateTaxIdNumber);
        lblTaxIdNumber.setFont(Font.font("Roboto", FontWeight.NORMAL, 15));
        lblUpdateTaxIdNumber.setTextFill(Paint.valueOf("#ffffff"));
        UpdatePane.addRow(5, btnReturnToView, btnUpdateClientInfo);
        UpdatePane.setHalignment(btnReturnToView, HPos.LEFT);
        UpdatePane.setHalignment(btnUpdateClientInfo, HPos.RIGHT);
        UpdatePane.add(lblUpdateError, 0, 6, 2, 1);
        UpdatePane.setHalignment(lblUpdateError, HPos.RIGHT);
        lblUpdateError.setFont(Font.font("Roboto", FontWeight.SEMI_BOLD, 15));
        lblUpdateError.setTextFill(Paint.valueOf("#C5CAE9"));

        btnReturnToView.setOnAction(e -> goBackClientOverview());
        btnUpdateClientInfo.setOnAction(e -> updateClientInfo());

        // client overview
        OverviewPane.setAlignment(Pos.TOP_LEFT);
        OverviewPane.setPadding(new Insets(20, 20, 20, 20));
        OverviewPane.setHgap(10);
        OverviewPane.setVgap(10);

        OverviewPane.add(lblTitleClientView, 0, 0, 3, 1);
        OverviewPane.setHalignment(lblTitleClientView, HPos.CENTER);
        lblTitleClientView.setFont(Font.font("Roboto", FontWeight.BOLD, 30));
        lblTitleClientView.setTextFill(Paint.valueOf("#ffffff"));
        OverviewPane.addRow(1, btnOverviewToClients);
        OverviewPane.add(lblClientIcon, 0, 2, 1, 4);
        OverviewPane.setHalignment(lblClientIcon, HPos.CENTER);
        OverviewPane.addRow(2, lblName, btnUploadImage);
        lblName.setFont(Font.font("Roboto", FontWeight.NORMAL, 18));
        lblName.setTextFill(Paint.valueOf("#ffffff"));
        OverviewPane.addRow(3, lblAddress);
        lblAddress.setFont(Font.font("Roboto", FontWeight.NORMAL, 18));
        lblAddress.setTextFill(Paint.valueOf("#ffffff"));
        OverviewPane.addRow(4, lblPostCode);
        lblPostCode.setFont(Font.font("Roboto", FontWeight.NORMAL, 18));
        lblPostCode.setTextFill(Paint.valueOf("#ffffff"));
        OverviewPane.addRow(6, btnDeleteClient, btnUpdateClient);
        OverviewPane.addRow(8, lblDocuments, btnAddDocument, btnUploadDocument);
        lblDocuments.setFont(Font.font("Roboto", FontWeight.SEMI_BOLD, 24));
        lblDocuments.setTextFill(Paint.valueOf("#ffffff"));
        btnUploadImage.setOnAction(e -> uploadImage());
        btnOverviewToClients.setOnAction(e -> goToClients());
        //  btnDeleteClient.setOnAction(e -> deleteClient());
        btnUpdateClient.setOnAction(e -> goToUpdatePage());
        btnAddDocument.setOnAction(e -> goToAddDocPage());
        btnUploadDocument.setOnAction(e -> uploadDocument());

        // login
        LoginPane.setHgap(10);
        LoginPane.setVgap(20);
        LoginPane.setAlignment(Pos.CENTER);

        LoginPane.add(lblLogTopic, 0, 0, 2, 1);
        LoginPane.setHalignment(lblLogTopic, HPos.CENTER);
        lblLogTopic.setFont(Font.font("Roboto", FontWeight.BOLD, 40));
        lblLogTopic.setTextFill(Paint.valueOf("#ffffff"));
        LoginPane.addRow(1, lblLogUsername, txtLogUsername);
        lblLogUsername.setFont(Font.font("Roboto", FontWeight.NORMAL, 15));
        lblLogUsername.setTextFill(Paint.valueOf("#ffffff"));
        LoginPane.addRow(2, lblLogPassword, txtLogPassword);
        lblLogPassword.setFont(Font.font("Roboto", FontWeight.NORMAL, 15));
        lblLogPassword.setTextFill(Paint.valueOf("#ffffff"));
        LoginPane.addRow(3, btnRegPage, btnLogin);
        LoginPane.setHalignment(btnLogin, HPos.RIGHT);
        LoginPane.setHalignment(btnRegPage, HPos.LEFT);
        lblLogError.setFont(Font.font("Roboto", FontWeight.SEMI_BOLD, 15));
        lblLogError.setTextFill(Paint.valueOf("#C5CAE9"));
        LoginPane.add(lblLogError, 0, 4, 2, 1);
        LoginPane.setHalignment(lblLogError, HPos.RIGHT);
        btnLogin.setOnAction(e -> loginClicked());
        btnRegPage.setOnAction(e -> regPage());

        // register
        RegPane.setHgap(10);
        RegPane.setVgap(20);
        RegPane.setAlignment(Pos.CENTER);

        RegPane.add(lblRegTopic, 0, 0, 2, 1);
        RegPane.setHalignment(lblRegTopic, HPos.CENTER);
        lblRegTopic.setFont(Font.font("Roboto", FontWeight.BOLD, 40));
        lblRegTopic.setTextFill(Paint.valueOf("#ffffff"));
        RegPane.addRow(1, lblRegUsername, txtRegUsername);
        lblRegUsername.setFont(Font.font("Roboto", FontWeight.NORMAL, 15));
        lblRegUsername.setTextFill(Paint.valueOf("#ffffff"));
        RegPane.addRow(2, lblRegPassword, txtRegPassword);
        lblRegPassword.setFont(Font.font("Roboto", FontWeight.NORMAL, 15));
        lblRegPassword.setTextFill(Paint.valueOf("#ffffff"));
        RegPane.addRow(3, lblRegReEnterPass, txtRegReEnterPass);
        lblRegReEnterPass.setFont(Font.font("Roboto", FontWeight.NORMAL, 15));
        lblRegReEnterPass.setTextFill(Paint.valueOf("#ffffff"));
        RegPane.addRow(4, lblRegAdminPassword, txtRegCode);
        lblRegAdminPassword.setFont(Font.font("Roboto", FontWeight.NORMAL, 15));
        lblRegAdminPassword.setTextFill(Paint.valueOf("#ffffff"));
        RegPane.addRow(5, btnBack, btnRegisterAcc);
        LoginPane.setHalignment(btnRegisterAcc, HPos.RIGHT);
        LoginPane.setHalignment(btnBack, HPos.LEFT);
        RegPane.add(lblRegError, 0, 6, 2, 1);
        lblRegError.setFont(Font.font("Roboto", FontWeight.SEMI_BOLD, 15));
        lblRegError.setTextFill(Paint.valueOf("#C5CAE9"));
        RegPane.setHalignment(lblRegError, HPos.RIGHT);

        btnBack.setOnAction(e -> backLogin());
        btnRegisterAcc.setOnAction(e -> registerAcc());

        // home
        HomePane.setAlignment(Pos.CENTER);
        HomePane.setVgap(10);
        HomePane.setHgap(30);

        HomePane.add(lblTitleHome, 0, 0, 5, 1);
        lblTitleHome.setFont(Font.font("Roboto", FontWeight.BOLD, 40));
        lblTitleHome.setTextFill(Paint.valueOf("#ffffff"));
        HomePane.setHalignment(lblTitleHome, HPos.CENTER);
        HomePane.addRow(1, btnLogout, btnClients, btnSearch, btnLogPage);
        HomePane.addRow(2, lblLogout, lblGoToClients, lblGoToSearch, lblLogPage);
        lblLogPage.setFont(Font.font("Roboto", FontWeight.NORMAL, 15));
        lblLogPage.setTextFill(Paint.valueOf("#ffffff"));
        HomePane.setHalignment(lblLogPage, HPos.CENTER);
        lblLogout.setFont(Font.font("Roboto", FontWeight.NORMAL, 15));
        lblLogout.setTextFill(Paint.valueOf("#ffffff"));
        lblGoToClients.setFont(Font.font("Roboto", FontWeight.NORMAL, 15));
        lblGoToClients.setTextFill(Paint.valueOf("#ffffff"));
        HomePane.setHalignment(lblGoToClients, HPos.CENTER);
        lblGoToSearch.setFont(Font.font("Roboto", FontWeight.NORMAL, 15));
        lblGoToSearch.setTextFill(Paint.valueOf("#ffffff"));
        HomePane.setHalignment(lblGoToSearch, HPos.CENTER);
        HomePane.setHalignment(lblLogout, HPos.CENTER);

        btnSearch.setOnAction(e -> goToSearch());
        btnSearch.setPrefSize(100, 100);
        btnClients.setOnAction(e -> goToClients());
        btnClients.setPrefSize(100, 100);
        btnLogout.setOnAction(e -> logout());
        btnLogout.setPrefSize(100, 100);
        btnGenRegCode.setPrefSize(100, 100);
        btnLogPage.setOnAction(e -> goToLogs());
        btnLogPage.setPrefSize(100, 100);

        try {
            Image image = new Image(getClass().getResourceAsStream("/ia/images/client-group.png"));
            btnClients.setGraphic(new ImageView(image));
            System.out.println("success");
            image = new Image(getClass().getResourceAsStream("/ia/images/search.png"));
            btnSearch.setGraphic(new ImageView(image));
            System.out.println("success");
            image = new Image(getClass().getResourceAsStream("/ia/images/logout.png"));
            btnLogout.setGraphic(new ImageView(image));
            image = new Image(getClass().getResourceAsStream("/ia/images/log.png"));
            btnLogPage.setGraphic(new ImageView(image));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // search
        SearchPane.setAlignment(Pos.TOP_LEFT);
        SearchPane.setVgap(10);
        SearchPane.setHgap(10);
        SearchPane.setPadding(new Insets(20, 20, 20, 20));

        SearchPane.addRow(0, lblTitleSearch);
        lblTitleSearch.setFont(Font.font("Roboto", FontWeight.BOLD, 30));
        lblTitleSearch.setTextFill(Paint.valueOf("#ffffff"));
        SearchPane.setHalignment(lblTitleSearch, HPos.LEFT);
        SearchPane.addRow(1, lblSearch);
        lblSearch.setFont(Font.font("Roboto", FontWeight.SEMI_BOLD, 20));
        lblSearch.setTextFill(Paint.valueOf("#ffffff"));
        SearchPane.setHalignment(lblSearch, HPos.LEFT);
        SearchPane.addRow(2, txtSearch, btnEnter);
        SearchPane.addRow(3, btnHomeFromSearch);

        btnHomeFromSearch.setOnAction(e -> BackToHome());

        btnEnter.setOnAction(e -> getSearchResults());

        // clients
        ClientsPane.setAlignment(Pos.CENTER);
        ClientsPane.setVgap(10);
        ClientsPane.setHgap(10);

        ClientsPane.add(lblTitleClients, 0, 0, 3, 1);
        lblTitleClients.setFont(Font.font("Roboto", FontWeight.BOLD, 40));
        lblTitleClients.setTextFill(Paint.valueOf("#ffffff"));
        ClientsPane.setHalignment(lblTitleClients, HPos.CENTER);
        ClientsPane.addRow(1, btnHomeFromClients);

        showClients();

        btnAddClient.setOnAction(e -> goToAddClientForm());
        btnHomeFromClients.setOnAction(e -> BackToHome());

        // add client form
        AddClientFormPane.setAlignment(Pos.CENTER);
        AddClientFormPane.setVgap(10);
        AddClientFormPane.setHgap(10);

        AddClientFormPane.add(lblTitleAddClient, 0, 0, 2, 1);
        AddClientFormPane.setHalignment(lblTitleAddClient, HPos.CENTER);
        lblTitleAddClient.setFont(Font.font("Roboto", FontWeight.BOLD, 30));
        lblTitleAddClient.setTextFill(Paint.valueOf("#ffffff"));
        AddClientFormPane.addRow(1, lblAddClientName, txtAddClientName);
        lblAddClientName.setFont(Font.font("Roboto", FontWeight.BOLD, 15));
        lblAddClientName.setTextFill(Paint.valueOf("#ffffff"));
        AddClientFormPane.addRow(2, lblAddClientAddress, txtAddClientAddress);
        lblAddClientAddress.setFont(Font.font("Roboto", FontWeight.BOLD, 15));
        lblAddClientAddress.setTextFill(Paint.valueOf("#ffffff"));
        AddClientFormPane.addRow(3, lblAddClientPostCode, txtAddClientPostCode);
        lblAddClientPostCode.setFont(Font.font("Roboto", FontWeight.BOLD, 15));
        lblAddClientPostCode.setTextFill(Paint.valueOf("#ffffff"));
        AddClientFormPane.addRow(4, lblAddTaxIdNum, txtAddTaxIdNum);
        lblAddTaxIdNum.setFont(Font.font("Roboto", FontWeight.BOLD, 15));
        lblAddTaxIdNum.setTextFill(Paint.valueOf("#ffffff"));
        AddClientFormPane.addRow(5, btnBackToClients, btnEnterAddClient);
        AddClientFormPane.setHalignment(btnBackToClients, HPos.LEFT);
        AddClientFormPane.setHalignment(btnEnterAddClient, HPos.RIGHT);
        AddClientFormPane.add(lblAddClientError, 0, 6, 2, 1);
        AddClientFormPane.setHalignment(lblAddClientError, HPos.RIGHT);
        lblAddClientError.setFont(Font.font("Roboto", FontWeight.SEMI_BOLD, 15));
        lblAddClientError.setTextFill(Paint.valueOf("#C5CAE9"));

        btnEnterAddClient.setOnAction(e -> AddClient());
        btnBackToClients.setOnAction(e -> goToClients());

        // scenes
        logPageScene = new Scene(logVBox, 600, 700);
        logScene = new Scene(LoginPane, 400, 400);
        logScene.getStylesheets().add("/ia/DefaultStyle.css");
        regScene = new Scene(RegPane, 500, 500);
        regScene.getStylesheets().add("/ia/DefaultStyle.css");
        sceneHome = new Scene(HomePane, 800, 500);
        sceneHome.getStylesheets().add("/ia/DefaultStyle.css");
        sceneSearch = new Scene(SearchPane, 500, 700);
        sceneSearch.getStylesheets().add("/ia/DefaultStyle.css");
        sceneClients = new Scene(ClientsPane, 700, 700);
        sceneClients.getStylesheets().add("/ia/DefaultStyle.css");
        sceneAddClient = new Scene(AddClientFormPane, 400, 400);
        sceneAddClient.getStylesheets().add("/ia/DefaultStyle.css");
        sceneView = new Scene(OverviewPane, 700, 850);
        sceneView.getStylesheets().add("/ia/DefaultStyle.css");
        sceneUpdateClient = new Scene(UpdatePane, 500, 500);
        sceneUpdateClient.getStylesheets().add("/ia/DefaultStyle.css");
        sceneAddUpdateDocumentForm = new Scene(AddUpdateDocumentPane, 700, 700);
        sceneAddUpdateDocumentForm.getStylesheets().add("/ia/DefaultStyle.css");

        stage.setTitle("Digital Tax Keeper");
        stage.setScene(logScene);
        stage.show();
    }
    
    /*
    * Precondition: user clicks on the Logs button in the homepage
    * Postcondition: most updated logs are displayed on the table in the logs page
    */
    private void goToLogs() {
        ObservableList<Record> logs = FXCollections.observableArrayList(user.getLogs());
        for (int i = 0; i < logs.size(); i++) {
            System.out.println(logs.get(i).getChange() + " " + logs.get(i).getAccount() + " " + logs.get(i).getTime());

        }
        tblLog.setItems(logs);
        stage.setScene(logPageScene);
        stage.show();
    }
    
    /*
    * Precondition: user clicks on the login button
    * Postcondition: home page will be displayed on the stage if login credentials
    *                are verified, the error label will be displayed in the login
    *                page if the login credentials are wrong
    */
    private void loginClicked() {
        String username = txtLogUsername.getText();
        String pass = txtLogPassword.getText();
        boolean verify = user.verifyAcc(username, pass);
        if (verify && !username.equals("") && !username.equals("")) {
            System.out.println("admin? " + user.isAdmin(username, pass));
            if (user.isAdmin(username, pass)) {
                HomePane.add(btnGenRegCode, 4, 1);
                HomePane.add(lblGenRegCode, 4, 2);
                btnGenRegCode.setOnAction(e -> genCode());
                btnGenRegCode.setPrefSize(100, 100);
                try {
                    Image image = new Image(getClass().getResourceAsStream("/ia/images/gen-code.png"));
                    btnGenRegCode.setGraphic(new ImageView(image));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            stage.setScene(sceneHome);
            stage.show();
        } else {
            lblLogError.setText("Invalid");
        }
        System.out.println("current acc: " + user.currentAcc);
    }
    
    /*
    * Precondition: user clicks on the logout button in the home page
    * Postcondition: redirects user back to the login page
    */
    private void logout() {
        txtLogUsername.setText("");
        txtLogPassword.setText("");
        HomePane.getChildren().remove(btnGenRegCode);
        HomePane.getChildren().remove(lblGenRegCode);
        lblLogError.setText("");
        stage.setScene(logScene);
        stage.show();
    }
    
    /*
    * Precondition: a user (who is an admin) clicks on the "generate code"
    *               button in the home page
    * Postcondition: an information dialog will be displayed indicating the
    *                randomly generated code to be used for registering new
    *                accounts
    */
    private void genCode() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Registration Code");
        alert.setHeaderText(null);
        alert.setContentText("Registration Code: " + user.getRegCode());

        alert.showAndWait();
    }
    
    /*
    * Precondition: user clicks on the "register" button in the login page
    * Postcondition: all of the textfields are set to blank and the register
    *                page is displayed on stage
    */
    private void regPage() {
        txtLogUsername.setText("");
        txtLogPassword.setText("");
        lblLogError.setText("");
        stage.setScene(regScene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /*
    * Precondition: user attempts to register an account by clicking on the
    *               "register" button in the register page
    * Postcondition: if the register code is correct and the input in the textfields
    *                are all valid, the new account will be registered to the database
    *                and will redirect user to login page. Otherwise, an error label
    *                will appear
    */
    private void registerAcc() {
        String username = txtRegUsername.getText();
        String pass = txtRegPassword.getText();
        String checkpass = txtRegReEnterPass.getText();
        String regCode = txtRegCode.getText();
        try {
            int enteredCode = Integer.parseInt(regCode);
            System.out.println("entered code: " + enteredCode);
            System.out.println("correct code? " + user.checkRegCode(enteredCode));
            if (user.checkRegCode(enteredCode) && pass.equals(checkpass) && !user.checkIfAccExist(username)) {
                System.out.println("accepted");
                user.addUser(username, pass, "user");
                txtRegUsername.setText("");
                txtRegPassword.setText("");
                txtRegReEnterPass.setText("");
                txtRegCode.setText("");
                lblRegError.setText("");
                stage.setScene(logScene);
                stage.show();
            } else {
                lblRegError.setText("Invalid");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lblRegError.setText("Invalid");
        }

    }
    
    /*
    * Precondition: user clicks on the "back" button in the register page
    * Postcondition: user is redirected to the login page
    */
    private void backLogin() {
        txtRegUsername.setText("");
        txtRegPassword.setText("");
        txtRegReEnterPass.setText("");
        txtRegCode.setText("");
        lblRegError.setText("");
        stage.setScene(logScene);
        stage.show();
    }

    /*
    * Precondition: user clicks on the "search" button in the homepage
    * Postcondition: user is redirected to the search page
    */
    private void goToSearch() {
        stage.setScene(sceneSearch);
        stage.show();
    }
    
    /*
    * Precondition: user clicks on the "back to home" button
    * Postcondition: search results are cleared and user is redirected to the
    *                home page
    */
    private void BackToHome() {
        clearSearchResults();
        txtSearch.setText("");
        stage.setScene(sceneHome);
        stage.show();
    }
    
    /*
    * Precondition: there are search results displayed on the search page
    * Postcondition: displayed search results will be cleared from the search pane
    */
    private void clearSearchResults() {
        ArrayList<Node> deleteNodes = new ArrayList();
        int count = 0;
        for (Node child : SearchPane.getChildren()) {
            if (count >= 5) {
                deleteNodes.add(child);
            }
            System.out.println(count);
            count++;
        }
        SearchPane.getChildren().removeAll(deleteNodes);
    }
    
    /*
    * Precondition: user goes to the clients page
    * Postcondition: clients scene will be displayed on the stage
    */
    private void goToClients() {
        showClients();
        stage.setScene(sceneClients);
        stage.show();
    }
    
    /*
    * Precondition: user clicks on the "add client" button in the clients page
    * Postcondition: add client form scene is displayed on the stage
    */
    private void goToAddClientForm() {
        stage.setScene(sceneAddClient);
        stage.show();
    }
    
    /*
    * Precondition: user clicks on the "add client" button in the add clients page
    * Postcondition: if the input values are accepted, the client will be registered
    *                in the database and user is redirected to the clients page, 
    *                otherwise an error will be displayed
    */
    private void AddClient() {
        String name = txtAddClientName.getText();
        String address = txtAddClientAddress.getText();
        String post_code = txtAddClientPostCode.getText();
        String tax_id_num = txtAddTaxIdNum.getText();
        Client client = new Client(name, address, post_code, tax_id_num);
        if (!name.equals("") || !address.equals("") || !post_code.equals("") || !tax_id_num.equals("")) {
            try {
                user.addClient(client);
                Image image = new Image(getClass().getResourceAsStream("/ia/images/add-client.png"));
                Button button = new Button();
                button.setGraphic(new ImageView(image));
                button.setText(client.getName());
                btnSearchRes.add(button);
                showClients();
                txtAddClientName.setText("");
                txtAddClientAddress.setText("");
                txtAddClientPostCode.setText("");
                txtAddTaxIdNum.setText("");
                stage.setScene(sceneClients);
                stage.show();
            } catch (Exception e) {
                //        e.printStackTrace();
            }
        } else if (name.equals("") || address.equals("") || post_code.equals("")) {
            lblAddClientError.setText("Please fill out all fields");
        } else if (user.checkIfClientExist(client)) {
            lblAddClientError.setText("Client already exists");
        } else {
            lblAddClientError.setText("Invalid input");
        }
    }
    
    /*
    * Precondition: user selects a specific client from the clients or search page
    * Postcondition: user is redirected to the specific client's overview page   
    */
    private void goToClientOverview(int index, String source) {
//        System.out.println(clients.get(index).toString() + " index: " + index);
        btnDeleteClient.setOnAction(e -> deleteClient(clients.get(index).getId()));
        if (source.equals("clients")) {
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(clients.get(index).getPic());
                BufferedImage bImage = ImageIO.read(bais);
                Image image = SwingFXUtils.toFXImage(bImage, null);
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);
                lblClientIcon.setGraphic(imageView);
            } catch (Exception e) {
                Image image = new Image(getClass().getResourceAsStream("/ia/images/client.png"));
                lblClientIcon.setGraphic(new ImageView(image));
            }
            lblName.setText(clients.get(index).getName());
            lblAddress.setText(clients.get(index).getAddress());
            lblPostCode.setText(clients.get(index).getPost_code());
            lblTaxIdNumber.setText(clients.get(index).getTax_id_number());
            int id = user.getClientId(clients.get(index).getName());
            showClientDocs(id);
        } else {
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(searchResClients.get(index).getPic());
                BufferedImage bImage = ImageIO.read(bais);
                Image image = SwingFXUtils.toFXImage(bImage, null);
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);
                lblClientIcon.setGraphic(imageView);
            } catch (Exception e) {
                Image image = new Image(getClass().getResourceAsStream("/ia/images/client.png"));
                lblClientIcon.setGraphic(new ImageView(image));
            }
            lblName.setText(searchResClients.get(index).getName());
            lblAddress.setText(searchResClients.get(index).getAddress());
            lblPostCode.setText(searchResClients.get(index).getPost_code());
            lblTaxIdNumber.setText(searchResClients.get(index).getTax_id_number());
            int id = user.getClientId(searchResClients.get(index).getName());
            showClientDocs(id);
        }
        stage.setScene(sceneView);
        stage.show();
    }
    
    /*
    * Precondition: user clicks on the "back" button from the add/update document or
    *               client form
    * Postcondition: redirects user to the overview page
    */
    private void goBackClientOverview() {
        txtUpdateName.setText("");
        txtUpdateAddress.setText("");
        txtUpdatePostCode.setText("");
        stage.setScene(sceneView);
        stage.show();
    }
    
    /*
    * Precondition: user clicks on the "delete" button for a client in the overview page
    * Postcondition: confirmation dialog is displayed, if yes the client is deleted 
    *                from the database and user redirected to the clients page, if not
    *                the dialog is closed
    */
    private void deleteClient(int client_id) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Are you sure you want to delete this client?");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");

        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yesButton) {
            Client client = new Client(lblName.getText(), lblAddress.getText(), lblPostCode.getText(), lblTaxIdNumber.getText());
            client.setId(client_id);
            user.deleteClient(client);
            goToClients();
        }
    }

    /*
    * Precondition: user clicks on the "update" button in the update form page
    * Postcondition: client's information is updated in the database and user
    *                is redirected back to the overview page
    */
    private void updateClientInfo() {
        String name = txtUpdateName.getText();
        String address = txtUpdateAddress.getText();
        String post_code = txtUpdatePostCode.getText();
        String tax_id_number = txtUpdateTaxIdNumber.getText();

        Client newClient = new Client(name, address, post_code, tax_id_number);
        Client oldClient = new Client(lblName.getText(), lblAddress.getText(), lblPostCode.getText(), lblTaxIdNumber.getText());

        if (!name.equals("") || !address.equals("") || !post_code.equals("")) {
            user.updateClientInfo(newClient, oldClient);
            lblName.setText(name);
            lblAddress.setText(address);
            lblPostCode.setText(post_code);
            goBackClientOverview();
        } else {
            lblUpdateError.setText("Invalid");
        }
    }

    /*
    * Precondition: user clicks on the "update" button in the overview page
    * Postcondition: update scene is displayed on the stage
    */
    private void goToUpdatePage() {
        txtUpdateName.setText(lblName.getText());
        txtUpdateAddress.setText(lblAddress.getText());
        txtUpdatePostCode.setText(lblPostCode.getText());
        txtUpdateTaxIdNumber.setText(lblTaxIdNumber.getText());
        stage.setScene(sceneUpdateClient);
        stage.show();
    }

    /*
    * Precondition: user clicks on the "+ add document" button in the overview page
    * Postcondition: add document form scene is displayed on the stage
    */
    private void goToAddDocPage() {
        lblTitleDocumentForm.setText("New Document");
        btnConfirmAddUpdateDoc.setText("Add Document");
        txtDocName.setText("");
        txtTaxObjectNumber.setText("");
        txtPropertyAddress.setText("");
        txtCertNumber.setText("");
        txtLandArea.setText("");
        txtBuildingArea.setText("");
        txtMarketPrice.setText("");
        txtLandTOSV.setText("");
        txtBuildingTOSV.setText("");
        txtMoneyDeposited.setText("");
        btnConfirmAddUpdateDoc.setOnAction(e -> addDoc());
        stage.setScene(sceneAddUpdateDocumentForm);
        stage.show();
    }

    /*
    * Precondition: user clicks on the "add document" button in the add document page
    * Postcondition: if the input is valid, the document is added to the database and
    *                user is redirected to the overview page, otherwise, error
    *                label is displayed
    */
    private void addDoc() {
        String DocName = txtDocName.getText();
        String TaxObjectNumber = txtTaxObjectNumber.getText();
        String PropertyAddress = txtPropertyAddress.getText();
        String CertNumber = txtCertNumber.getText();
        double LandArea = Double.parseDouble(txtLandArea.getText());
        double BuildingArea = Double.parseDouble(txtBuildingArea.getText());
        double MarketPrice = Double.parseDouble(txtMarketPrice.getText());
        double LandTOSV = Double.parseDouble(txtLandTOSV.getText());
        double BuildingTOSV = Double.parseDouble(txtBuildingTOSV.getText());
        int MoneyDeposited = Integer.parseInt(txtMoneyDeposited.getText());

        txtDocName.setText("");
        txtTaxObjectNumber.setText("");
        txtPropertyAddress.setText("");
        txtCertNumber.setText("");
        txtLandArea.setText("");
        txtBuildingArea.setText("");
        txtMarketPrice.setText("");
        txtLandTOSV.setText("");
        txtBuildingTOSV.setText("");
        txtMoneyDeposited.setText("");

        if (!DocName.equals("") || !TaxObjectNumber.equals("") || !PropertyAddress.equals("") || !CertNumber.equals("")) {
            Document document = new Document(DocName, TaxObjectNumber, PropertyAddress, CertNumber, LandArea, BuildingArea, MarketPrice, LandTOSV, BuildingTOSV, MoneyDeposited);
            Client client = new Client(lblName.getText(), lblAddress.getText(), lblPostCode.getText(), lblTaxIdNumber.getText());
            client.setId(user.getClientId(client.getName()));
            user.saveToPDF(document, client);
            showClientDocs(client.getId());
            stage.setScene(sceneView);
            stage.show();
        } else {
            lblAddDocError.setText("Invalid");
        }
    }

    /*
    * Precondition: the user clicks on the "clients" button in the home page
    *               or wants to return to the clients page
    * Postcondition: clients are displayed on the clients page in rows of 3
    */
    private void showClients() {
        // clears the client list
        ArrayList<Node> deleteNodes = new ArrayList();
        int count = 0;
        for (Node child : ClientsPane.getChildren()) {
            if (count >= 2) {
                deleteNodes.add(child);
            }
            System.out.println(count);
            count++;
        }
        ClientsPane.getChildren().removeAll(deleteNodes);

        // shows
        clients = user.getClients();
        btnClientList.clear();
        int countRow = 2;
        for (int i = 0; i < clients.size(); i++) {
            btnClientList.add(new Button(clients.get(i).getName()));
            btnClientList.get(i).setPrefSize(250, 100);
            try {
                System.out.println(clients.get(i).getName() + " " + Arrays.toString(clients.get(i).getPic()));
                ByteArrayInputStream bais = new ByteArrayInputStream(clients.get(i).getPic());
                BufferedImage bImage = ImageIO.read(bais);
                Image image = SwingFXUtils.toFXImage(bImage, null);
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(75);
                imageView.setFitWidth(75);
                btnClientList.get(i).setGraphic(imageView);
                lblClientIcon.setGraphic(imageView);
            } catch (Exception e) {
                Image image = new Image(getClass().getResourceAsStream("/ia/images/client.png"));
                btnClientList.get(i).setGraphic(new ImageView(image));
            }
            ClientsPane.addRow(countRow, btnClientList.get(i));
            if ((i + 1) % 3 == 0) {
                countRow++;
            }
        }
        Image image = new Image(getClass().getResourceAsStream("/ia/images/add-client.png"));
        btnAddClient.setGraphic(new ImageView(image));
        btnAddClient.setText("Add Client");
        btnAddClient.setPrefSize(200, 100);
        ClientsPane.addRow(countRow, btnAddClient);
        for (int i = 0;i < btnClientList.size();i++) {
            btnClientList.get(i).setPrefSize(200, 100);
            final int index = i;
            btnClientList.get(i).setOnAction(e -> goToClientOverview(index, "clients"));
        }
    }

    /*
    * Precondition: user enters a keyword to search for on the textfield
    * Postcondition: search results relating to the keyword is displayed
    *                or an error will be displayed
    */
    private void getSearchResults() {
        System.out.println("in search results");
        btnSearchRes.clear();
        clearSearchResults();
        String keyword = txtSearch.getText();
        searchResClients = user.getSearchResultsClients(keyword);
        searchResDocs = user.getSearchResultsDocs(keyword);
        if (keyword.equals("")) {
            System.out.println("Empty search bar");
        } else if (searchResClients.isEmpty() && searchResDocs.isEmpty()) {
            lblNotFound.setText("Search results not found");
            SearchPane.addRow(4, lblNotFound);
            System.out.println("not found");
        } else {
            // clients
            for (int i = 0; i < searchResClients.size(); i++) {
                btnSearchRes.add(new Button(searchResClients.get(i).getName()));
                btnSearchRes.get(i).setPrefSize(250, 100);
                try {
                    System.out.println("picture: " + Arrays.toString(searchResClients.get(i).getPic()));
                    ByteArrayInputStream bais = new ByteArrayInputStream(searchResClients.get(i).getPic());
                    BufferedImage bImage = ImageIO.read(bais);
                    Image image = SwingFXUtils.toFXImage(bImage, null);
                    ImageView imageView = new ImageView(image);
                    imageView.setFitHeight(75);
                    imageView.setFitWidth(75);
                    btnSearchRes.get(i).setGraphic(imageView);
                } catch (Exception e) {
                    e.printStackTrace();
                    Image image1 = new Image(getClass().getResourceAsStream("/ia/images/client.png"));
                    btnSearchRes.get(i).setGraphic(new ImageView(image1));
                }
                SearchPane.addRow(4 + i, btnSearchRes.get(i));
                System.out.println("printing results");
            }

            int startingRow = 4 + searchResClients.size() + 1;
            // docs
            for (int i = 0; i < searchResDocs.size(); i++) {
                btnSearchRes.add(new Button(searchResDocs.get(i).getDocName()));
                Image image = new Image(getClass().getResourceAsStream("/ia/images/document.png"));
                btnSearchRes.get(btnSearchRes.size() - 1).setGraphic(new ImageView(image));
                SearchPane.addRow(startingRow + i, btnSearchRes.get(btnSearchRes.size() - 1));
            }

            // set on action for buttons
            for (int i = 0; i < btnSearchRes.size(); i++) {
                btnSearchRes.get(i).setPrefSize(250, 100);
                btnSearchRes.get(i).setAlignment(Pos.CENTER_LEFT);
                final int index = i;
                if (i < searchResClients.size()) {
                    btnSearchRes.get(i).setOnAction(e -> goToClientOverview(index, "search"));
                } else {
                    btnSearchRes.get(i).setOnAction(e -> viewClientDoc(searchResDocs.get(index - searchResClients.size())));
                }
            }
        }

        System.out.println(btnSearchRes.size());
    }

    /*
    * Precondition: user selects a user to view overview page
    * Postcondition: specific client's documents are displayed at the bottom of the page
    */
    private void showClientDocs(int id) {
        ArrayList<Node> deleteNodes = new ArrayList();
        int count = 0;
        for (Node child : OverviewPane.getChildren()) {
            if (count >= 12) {
                System.out.println(count);
                deleteNodes.add(child);
            }
            count++;
        }
        OverviewPane.getChildren().removeAll(deleteNodes);

        ArrayList<Document> clientDocs = user.getClientDocuments(id);
        lblClientDocs.clear();
        btnDeleteDoc.clear();
        btnViewDoc.clear();
        btnUpdateDoc.clear();
        for (int i = 0; i < clientDocs.size(); i++) {
            btnUpdateDoc.add(new Button("Update"));
            btnDeleteDoc.add(new Button("Delete"));
            Image image = new Image(getClass().getResourceAsStream("/ia/images/document.png"));
            Button button = new Button(clientDocs.get(i).getDocName());
            button.setGraphic(new ImageView(image));
            button.setWrapText(true);
            btnViewDoc.add(button);
            OverviewPane.addRow(9 + i, btnViewDoc.get(i), btnDeleteDoc.get(i), btnUpdateDoc.get(i));
        }

        for (int i = 0; i < btnViewDoc.size(); i++) {
            final int index = i;
            btnViewDoc.get(index).setOnAction(e -> viewClientDoc(clientDocs.get(index)));
            btnViewDoc.get(index).setPrefSize(175, 10);
            btnUpdateDoc.get(index).setOnAction(e -> goToUpdateDocument(clientDocs.get(index)));
            btnUpdateDoc.get(index).setPrefSize(175, 10);
            btnDeleteDoc.get(index).setOnAction(e -> deleteClientDoc(clientDocs.get(index), id));
            btnDeleteDoc.get(index).setPrefSize(175, 10);
        }
    }

    /*
    * Precondition: user selects a specific document to delete
    * Postcondition: a confirmation dialog is displayed and if the user clicks yes,
    *                the specific document is deleted from the page and database, if
    *                not, the confirmation dialog is closed
    */
    private void deleteClientDoc(Document document, int client_id) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Confirmation");
        alert.setHeaderText("Are you sure you want to delete this document?");

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");

        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yesButton) {
            user.deleteDocument(document);
            showClientDocs(client_id);
        }
    }

    /*
    * Precondition: user clicks on the "update document" button for a specific
    *               existing document
    * Postcondition: update document form is displayed and textfields are filled
    *                out with existing information from the document
    */
    private void goToUpdateDocument(Document document) {
        lblTitleDocumentForm.setText("Update Document");
        txtDocName.setText(document.getDocName());
        txtTaxObjectNumber.setText(document.getTaxObjectNumber());
        txtPropertyAddress.setText(document.getPropertyAddress());
        txtCertNumber.setText(document.getCertNumber());
        txtLandArea.setText(Double.toString(document.getLandArea()));
        txtBuildingArea.setText(Double.toString(document.getBuildingArea()));
        txtMarketPrice.setText(Double.toString(document.getMarketPrice()));
        txtLandTOSV.setText(Double.toString(document.getLandTOSV()));
        txtBuildingTOSV.setText(Double.toString(document.getBuildingTOSV()));
        txtMoneyDeposited.setText(Double.toString(document.getMoneyDeposited()));
        btnConfirmAddUpdateDoc.setText("Update Document");
        btnConfirmAddUpdateDoc.setOnAction(e -> updateDoc(document.getDocId()));
        stage.setScene(sceneAddUpdateDocumentForm);
        stage.show();
    }

    /*
    * Precondition: user clicks on the "update" button in the update document page
    * Postcondition: document information is updated in the database and the user is
    *                redirected to the overview if all input values are valid, if
    *                not, an error will appear
    */
    private void updateDoc(int docid) {
        String DocName = txtDocName.getText();
        String TaxObjectNumber = txtTaxObjectNumber.getText();
        String PropertyAddress = txtPropertyAddress.getText();
        String CertNumber = txtCertNumber.getText();
        double LandArea = Double.parseDouble(txtLandArea.getText());
        double BuildingArea = Double.parseDouble(txtBuildingArea.getText());
        double MarketPrice = Double.parseDouble(txtMarketPrice.getText());
        double LandTOSV = Double.parseDouble(txtLandTOSV.getText());
        double BuildingTOSV = Double.parseDouble(txtBuildingTOSV.getText());
        int MoneyDeposited = new BigDecimal(txtMoneyDeposited.getText()).intValue();
        Document doc = new Document(DocName, TaxObjectNumber, PropertyAddress, CertNumber, LandArea, BuildingArea, MarketPrice, LandTOSV, BuildingTOSV, MoneyDeposited);
        doc.setDocId(docid);
        Client client = new Client(lblName.getText(), lblAddress.getText(), lblPostCode.getText(), lblTaxIdNumber.getText());
        client.setId(user.getClientId(client.getName()));
        user.updateDocument(doc, client);
        showClientDocs(client.getId());
        stage.setScene(sceneView);
        stage.show();
    }

    /*
    * Precondition: user clicks on the "upload image" button in the overview page
    * Postcondition: filechooser dialog will allow user to upload an image, if no
    *                errors occur, the image is added to the database and displayed
    *                on the client overview page, otherwise, an error dialog will appear
    */
    private void uploadImage() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.jpeg", "*.png");
        fileChooser.getExtensionFilters().add(filter);
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {

                FileInputStream fis = new FileInputStream(selectedFile);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                try {
                    for (int readNum; (readNum = fis.read(buf)) != -1;) {
                        bos.write(buf, 0, readNum);
                        System.out.println("read " + readNum + " bytes,");
                    }
                } catch (Exception ex) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Unable to Upload Image!");
                    alert.setContentText("The size of the image might be too big. Please try upload an image of a smaller size.");
                    alert.showAndWait();
                }
                byte[] bytes = bos.toByteArray();
                user.uploadImage(bytes, user.getClientId(lblName.getText()), lblName.getText());
                ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                BufferedImage bImage = ImageIO.read(bais);
                Image image = SwingFXUtils.toFXImage(bImage, null);
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(100);
                imageView.setFitWidth(100);
                lblClientIcon.setGraphic(imageView);
            } catch (Exception e) {
                //     e.printStackTrace();
            }
        }
    }
    
    /*
    * Precondition: user clicks on the a specific document button 
    *               in the overview page to view the document
    * Postcondition: opens the document in pdf format
    */
    private void viewClientDoc(Document document) {
        System.out.println(document.getDocName());
        System.out.println(Arrays.toString(document.getPdf()));
        byte[] docBytes = document.getPdf();
        try {
            FileOutputStream fos = new FileOutputStream(document.getDocName() + ".pdf");
            fos.write(docBytes);
            fos.close();
            Desktop.getDesktop().open(new File(document.getDocName() + ".pdf"));
        } catch (Exception e) {
            //  e.printStackTrace();
        }
    }
    
    /*
    * Precondition: user clicks on the "upload document" button in the overview page
    * Postcondition: filechooser dialog is displayed for user to select document. If
    *                file is too big, an error dialog will appear. If the file is
    *                appropriate, the document will be added to the database and on
    *                the overview page
    */
    private void uploadDocument() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("PDF", "*.pdf");
        fileChooser.getExtensionFilters().add(filter);
        File selectedFile = fileChooser.showOpenDialog(stage);

        try {
            System.out.println(selectedFile.length());
            FileInputStream fis = new FileInputStream(selectedFile);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            try {
                for (int readNum; (readNum = fis.read(buf)) != -1;) {
                    bos.write(buf, 0, readNum);
                    System.out.println("read " + readNum + " bytes,");
                }
            } catch (Exception ex) {
                System.out.println("file is too big");
            }
            byte[] bytes = bos.toByteArray();
            System.out.println(Arrays.toString(bytes));
            user.uploadDoc(bytes, user.getClientId(lblName.getText()), 
                    selectedFile.getName().substring(0, selectedFile.getName().indexOf(".")));
            showClientDocs(user.getClientId(lblName.getText()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<Document> clientDocs = user.getClientDocuments(user.getClientId(lblName.getText()));
        boolean exists = false;
        for (int i = 0; i < clientDocs.size(); i++) {
            String docName = clientDocs.get(i).getDocName() + ".pdf";
            if (docName.equals(selectedFile.getName())) {
                exists = true;
            }
        }
        if (!exists) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Unable to Upload Document!");
            alert.setContentText("The size of the file might be too big. Please try upload a file of a smaller size.");
            alert.showAndWait();
        }
    }

}
