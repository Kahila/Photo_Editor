package client.clientView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import client.clientController.Controller;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GuiView {
	private static String wall = "https://wallpaperaccess.com/full/1398314.jpg";
	private static Stage stage = null;
	private Controller ctr = new Controller();
	private boolean connected = false;
	
	private Scene sceneP1 = null; 
	private GridPane firstPane = null;
	private GridPane left = null;
	private GridPane right = null;
	private GridPane center = null;
	private BorderPane secondPane = null;
	private Scene sceneP2 = null;	
	private FileChooser.ExtensionFilter imageFltr = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
	private FileChooser fc = new FileChooser();
	private File selectedFile = null;
	private TextArea ta = null;
	private Image image = null;
	private Image imageReset = null;
	private ImageView imageView = null;
	
	//Parameterized constructor
	public GuiView(Stage stage1) {
		stage = stage1;
		setUpUI();
	}
	
	//method for setting up UI
	public void setUpUI() {
		stage.setTitle("ProjectX");
		
		PageOne();
		stage.show();
	}
	
	//first page of GUI (landing page)
	private void PageOne() {
		firstPane = new GridPane();
		sceneP1 = new Scene(firstPane, 1500, 800);
		
		Button select = new Button("UPLOAD IMAGE");
		Button create = new Button("UPLOAD VEDIO");
		Button change = new Button("EXIT PROGRAME");
		Alert alert = new Alert(AlertType.NONE);
		ta = new TextArea();
		ta.setDisable(true); //making the text Area unEditable
		ta.setStyle("-fx-text-fill: #4cd137;");
		
		//attempting to connect to server
		connected = false;
		if ((connected = ctr.connectServer())) {
			ta.appendText("Connected to the server\n");
		}
		
		select.setPrefWidth(300);
		select.setPrefHeight(50);
		ta.setPrefWidth(300);
		ta.setPrefHeight(50);
		create.setPrefWidth(300);
		create.setPrefHeight(50);
		change.setPrefWidth(300);
		change.setPrefHeight(50);
		select.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: Black;");
		ta.setStyle("-fx-font-weight: bold; -fx-text-fill: Black;");
		create.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: Black;");
		change.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: Black;");
		
		firstPane.add(select, 0, 0);
		firstPane.add(change, 0, 3);
		firstPane.add(ta, 0, 4);
		firstPane.setAlignment(Pos.CENTER);
		firstPane.setStyle("-fx-background-image: url('"+wall+"')");
		
		GridPane.setMargin(select, new Insets(0, 0, 10, 0));
		GridPane.setMargin(create, new Insets(0, 0, 10, 0));
		GridPane.setMargin(ta, new Insets(10, 0, 10, 0));
		
		//setting actions for buttons
		change.setOnAction(value ->{
			System.exit(-1);
		});
		select.setOnAction(event -> {
			//only show page when image is selected
			fc.getExtensionFilters().add(imageFltr);
			selectedFile = fc.showOpenDialog(stage);
			if (selectedFile != null && connected == true) {
				try {
					FileInputStream inputstream = new FileInputStream(selectedFile);
					image = new Image(inputstream ,300,260,false,true);///
					imageReset = image;
					ImageSelected();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}else if (!connected){
				alert.setAlertType(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setContentText("Error connecting to the server\nPlease make Sure The Server Is Running");
				alert.show();
			}else {
				alert.setAlertType(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setContentText("Invalid File Selection");
				alert.show();
			}
		});
		stage.setScene(sceneP1);
	}
	
	//second page that will be showed after an image has been selected
	private void ImageSelected() {
		
		secondPane = new BorderPane();
		left = new GridPane();
		center = new GridPane();
		right = new GridPane();
		
		Alert alert = new Alert(AlertType.NONE);
		Button grayScale = new Button("Black And White");
		Button rotate = new Button("Rotate");
		Button crop = new Button("Zoom In");
		Button Dilation = new Button("Dilate");
		Button Erosion = new Button("Erode");
		Button submit = new Button("Submit");
		Button Extract = new Button("Feature Extraction");
		Button change = new Button("EXIT PROGRAME");
		Button reset = new Button("Reset Image");
		Button canny = new Button("Canny");
		Button orb = new Button("HightLight Features");
		Button fast = new Button("fast");
		Button upload = new Button("New Upload");
		Button save = new Button("Save Image");
		Button undo = new Button("Undo");
		Button redo = new Button("Redo");
		
		secondPane.setPadding(new Insets(40, 40, 40, 40));
		TextArea ta = new TextArea();
		sceneP2 = new Scene(secondPane, 1500, 800);
		String options[] = {"GrayScale", "Rotate", "Erosion", "Dilation", "Crop"};
		connected = false;
		if ((connected = ctr.connectServer())) {
			ta.appendText("Connected to the server\n");
		}
		
		imageView = new ImageView(image);
//		//setting button properties
		redo.setPrefWidth(300);
		redo.setPrefHeight(50);
		undo.setPrefWidth(300);
		undo.setPrefHeight(50);
		ta.setPrefWidth(300);
		ta.setPrefHeight(50);
		submit.setPrefWidth(300);
		submit.setPrefHeight(50);
		change.setPrefWidth(300);
		change.setPrefHeight(50);
		Extract.setPrefWidth(300);
		Extract.setPrefHeight(50);
		//button edit buttons
		reset.setPrefWidth(300);
		reset.setPrefHeight(50);
		grayScale.setPrefWidth(300);
		grayScale.setPrefHeight(50);
		rotate.setPrefWidth(300);
		rotate.setPrefHeight(50);
		crop.setPrefWidth(300);
		crop.setPrefHeight(50);
		Dilation.setPrefWidth(300);
		Dilation.setPrefHeight(50);
		Erosion.setPrefWidth(300);
		Erosion.setPrefHeight(50);
		Erosion.setPrefHeight(50);
		Erosion.setPrefHeight(50);
		canny.setPrefWidth(300);
		canny.setPrefHeight(50);
		orb.setPrefWidth(300);
		orb.setPrefHeight(50);
		fast.setPrefWidth(300);
		fast.setPrefHeight(50);
		upload.setPrefWidth(300);
		upload.setPrefHeight(50);
		save.setPrefWidth(300);
		save.setPrefHeight(50);
		
		GridPane.setMargin(canny, new Insets(0, 100, 10, 0));
		GridPane.setMargin(save, new Insets(0, 100, 10, 0));
		GridPane.setMargin(upload, new Insets(0, 100, 10, 0));
		GridPane.setMargin(orb, new Insets(0, 100, 10, 0));
		GridPane.setMargin(fast, new Insets(0, 100, 10, 0));
		GridPane.setMargin(grayScale, new Insets(0, 0, 10, 100));
		GridPane.setMargin(rotate, new Insets(0, 0, 10, 100));
		GridPane.setMargin(crop, new Insets(0, 0, 10, 100));
		GridPane.setMargin(Erosion, new Insets(0, 0, 10, 100));
		GridPane.setMargin(Dilation, new Insets(0, 0, 10, 100));
		GridPane.setMargin(reset, new Insets(0, 0, 10, 0));
		GridPane.setMargin(redo, new Insets(0, 0, 10, 0));
		GridPane.setMargin(undo, new Insets(0, 0, 10, 0));
		GridPane.setMargin(imageView, new Insets(0, 0, 10, 0));
		
		redo.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: Black;-fx-background-radius: 50px;");
		undo.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: Black;-fx-background-radius: 50px;");
		save.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: Black;-fx-background-radius: 50px;");
		upload.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: Black;-fx-background-radius: 50px;");
		canny.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: Black;-fx-background-radius: 50px;");
		orb.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: Black;-fx-background-radius: 50px;");
		fast.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: Black;-fx-background-radius: 50px;");
		grayScale.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: Black;-fx-background-radius: 50px;");
		rotate.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: Black;-fx-background-radius: 50px;");
		crop.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: Black;-fx-background-radius: 50px;");
		Dilation.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: Black;-fx-background-radius: 50px;");
		Erosion.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: Black;-fx-background-radius: 50px;");
		
		Extract.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: Black;-fx-background-radius: 50px;");
		change.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: Black;-fx-background-radius: 50px;");
		submit.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: Black;-fx-background-radius: 50px;");
		reset.setStyle("-fx-font-weight: bold; -fx-font-size: 20px; -fx-text-fill: Black;-fx-background-radius: 50px;");
		center.setAlignment(Pos.CENTER);
		left.setAlignment(Pos.CENTER);
		right.setAlignment(Pos.CENTER);
		secondPane.setStyle("-fx-background-image: url('"+wall+"')");
		
		//action that will be triggered by selected option
		change.setOnAction(value ->{
			System.exit(-1);
		});canny.setOnAction(value -> {
			image = ctr.grayScale(selectedFile, "/api/Canny");
			ImageSelected();
		});orb.setOnAction(value -> {
			image = ctr.grayScale(selectedFile, "/api/ORB");
      	  	ImageSelected();
		});fast.setOnAction(value -> {
			image = ctr.grayScale(selectedFile, "/api/Fast");
			ImageSelected();
		});Erosion.setOnAction(value -> {
			image = ctr.grayScale(selectedFile, "/api/Erosion");
			ImageSelected();
		});grayScale.setOnAction(value -> {
			image = ctr.grayScale(selectedFile, "/api/GrayScale");
       	  	ImageSelected();
		});Dilation.setOnAction(value -> {
			image = ctr.grayScale(selectedFile, "/api/Dilation");
			ImageSelected();
		});crop.setOnAction(value -> {
			image = ctr.grayScale(selectedFile, "/api/Crop");
      	  	ImageSelected();
		});rotate.setOnAction(value -> {
			image = ctr.grayScale(selectedFile, "/api/Rotate");
			ImageSelected();
		});reset.setOnAction(value ->{
			ctr.reset();
			image = imageReset;
			ImageSelected();
		});upload.setOnAction(value ->{
			ImageSelected();
			PageOne();
		});save.setOnAction(value ->{
			alert.setAlertType(AlertType.CONFIRMATION);
			alert.setTitle("Success");
			alert.setContentText("Your Image Has Been Saved To your Root Directory");
			alert.show();
		});

		secondPane.setLeft(left);
		secondPane.setCenter(center);
		secondPane.setRight(right);
		center.add(imageView, 0, 0);
		center.add(reset, 0, 3);
		center.add(redo, 0, 1);
		center.add(undo, 0, 2);
		center.add(change, 0, 4);
		
		//adding to left GridPane
		left.add(grayScale, 0, 1);
		left.add(crop, 0, 2);
		left.add(rotate, 0, 3);
		left.add(Dilation, 0, 4);
		left.add(Erosion, 0, 5);
		
		//adding to the right GridPane
		right.add(canny, 0, 0);
		right.add(orb, 0, 1);
		right.add(fast, 0, 2);
		right.add(upload, 0, 4);
		right.add(save, 0, 5);
		
		stage.setScene(sceneP2);
	}
}
