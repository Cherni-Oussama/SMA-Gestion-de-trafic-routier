
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Container extends Application {

	RoadPoint roadPoint = new RoadPoint();
	public StackPane stackPane = new StackPane();
	
	public static final double normalCarCost = 100;
	public static final double policeCarCost = 300;
	public static final double ambulanceCarCost = 1000;
	
	public double normalAgentNumber=20;
	public double policeAgentNumber=1;
	public double ambulanceAgentNumber=1;
	
	public double vehicleAgentNumber=policeAgentNumber+ambulanceAgentNumber+normalAgentNumber;

	
	public ArrayList<Shape> agentShapeList;

	public static void main(String[] args) {
		launch(Container.class);
	}

	public void startcontainer() {
		try {
			Runtime runtime = Runtime.instance();
			ProfileImpl profileImpl = new ProfileImpl(false);
			profileImpl.setParameter(ProfileImpl.MAIN_HOST, "localhost");
			AgentContainer agentContainer = runtime.createAgentContainer(profileImpl);
			agentShapeList = new ArrayList<Shape>();
			
			//Adding the TrafficLight
			for (int i = 0; i < roadPoint.getSignalLocList().size(); i++) {
				RoadPoint.SignalLoc signalInfo = roadPoint.getSignalLocList().get(i);

				AgentController signalTrafficAgent;
				signalTrafficAgent = agentContainer.createNewAgent("signalTrafficAgent" + i, "SignalTrafficAgent",
						new Object[] { this, signalInfo });
				signalTrafficAgent.start();

			}
			
			//adding the Cars
			int j=0;
			for (int i = 0; i < normalAgentNumber; i++) {
				AgentController vehicleAgent;
				vehicleAgent = agentContainer.createNewAgent("vehicleAgent" + j, "VehicleAgent", new Object[] { this ,normalCarCost});
				j++;
				vehicleAgent.start();
			}
			for (int i = 0; i < policeAgentNumber; i++) {
				AgentController vehicleAgent;
				vehicleAgent = agentContainer.createNewAgent("vehicleAgent" + j, "VehicleAgent", new Object[] { this ,policeCarCost});
				j++;
				vehicleAgent.start();
			}
			for (int i = 0; i < ambulanceAgentNumber; i++) {
				AgentController vehicleAgent;
				vehicleAgent = agentContainer.createNewAgent("vehicleAgent" + j, "VehicleAgent", new Object[] { this ,ambulanceCarCost});
				j++;
				vehicleAgent.start();
			}

		} catch (StaleProxyException e1) {
			System.out.println("excep.....");
			e1.printStackTrace();
		}
	}

	public List<String> getLocalAgents(int xmin, int xmax, int ymin, int ymax) {

		List<String> localAgentList = new ArrayList<String>();
		for (Node node : stackPane.getChildren()) {
			if (node.getTranslateX() > xmin && node.getTranslateX() < xmax && node.getTranslateY() > ymin
					&& node.getTranslateY() < ymax) {
				if (node.getUserData() != null) {
					localAgentList.add(node.getUserData().toString());
					localAgentList.add(node.toString());
				}
			}
		}
		return localAgentList;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		 startcontainer();

		Image image = new Image("/image/cityA.png");
		ImageView imageView = new ImageView(image);

		stackPane.getChildren().add(imageView);
		imageView.toBack();
		
		VBox vbox = new VBox(stackPane);

		Scene scene = new Scene(vbox);
		primaryStage.setScene(scene);

		primaryStage.setResizable(false);

		primaryStage.sizeToScene();

		primaryStage.show();
	}
}
