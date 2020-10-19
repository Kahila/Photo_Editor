package client.clientController;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;

import client.clientModel.*;
import client.clientView.GuiView;

public class Controller extends Application{
	private Model model = new Model();
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		new GuiView(primaryStage);
		primaryStage.show();
	}
	
	//method for connecting to the server
	public boolean connectServer() {
		return (model.getConnected());
	}
	
	//method for GrayScale
	public Image grayScale(File file, String qryString) {
		model.grayScale(file, qryString);
		return (model.getImage());
	}
	
	public void reset() {
		model.reset();
	}
}
