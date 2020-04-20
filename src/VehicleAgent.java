import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import sun.management.resources.agent;

public class VehicleAgent extends GuiAgent {
	private Container gui;
	RoadPoint.Point nextTarget = null;
	Map<String, List<Double>> signalMessage = new HashMap<String, List<Double>>();
	

	Circle agentShape;
	private double speed = 1;
	boolean stop=false;
	public VehicleAgent() {

	}
	
	public boolean isNearSig(double x, double y) {
		for (List<Double> color_coord : signalMessage.values()) {
			double signalColor = color_coord.get(0);
			double signalX = color_coord.get(1);
			double signalY = color_coord.get(2);
			double distToSignal = Math.sqrt(Math.pow(x - signalX, 2)
					+ Math.pow(y - signalY, 2));
			
			if (distToSignal < 8) {
				if (signalColor == SignalTrafficAgent.red) {
					return true;
				} 
			}
		}
		return false;
		
	}
	

	@Override
	protected void setup() {

		Object[] args = getArguments();
		if (args.length == 2) {
			gui = (Container) getArguments()[0];

			agentShape = new Circle();
			agentShape.setUserData((double) getArguments()[1]);
			agentShape.setRadius(8.0);
			double carType= (double) getArguments()[1];
			
			if(carType==Container.ambulanceCarCost) {
				agentShape.setFill(Color.RED);
				speed = 1;
			}else if (carType==Container.policeCarCost) {
				agentShape.setFill(Color.BLUE);	
				speed = 0.8;
			}else {
				agentShape.setFill(Color.AZURE);
				speed = 0.5;
			}
			
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					gui.stackPane.getChildren().add(agentShape);
					gui.agentShapeList.add(agentShape);
					agentShape.toFront();
				}
			});
			initialize();

		} else {
			System.out.println("no container exist");
			doDelete();
		}

		addBehaviour(new TickerBehaviour(this, 10) {

			@Override
			protected void onTick() {
				stop=isNearSig(agentShape.getTranslateX(), agentShape.getTranslateY());			
				if (!stop) {
					if (nextTarget != null) {
						double deltaX = nextTarget.X - agentShape.getTranslateX();
						double deltaY = nextTarget.Y - agentShape.getTranslateY();
						double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

						if (distance < speed) {
							nextTarget = gui.roadPoint.getNextPointInfo(nextTarget);
							if (nextTarget != null) {
								deltaX = nextTarget.X - agentShape.getTranslateX();
								deltaY = nextTarget.Y - agentShape.getTranslateY();

							}

						}
					}
					if (nextTarget != null) {

						double deltaX = nextTarget.X - agentShape.getTranslateX();
						double deltaY = nextTarget.Y - agentShape.getTranslateY();
						double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

						double stepX = deltaX / distance;
						double stepY = deltaY / distance;

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								agentShape.setTranslateX(agentShape.getTranslateX() + stepX * speed);
								agentShape.setTranslateY(agentShape.getTranslateY() + stepY * speed);
							}
						});

					} else {
						initialize();
					}

				}
			}

		});
		addBehaviour(new CyclicBehaviour() {

			@Override
			public void action() {
				ACLMessage msg = receive();
				if (msg != null) {
					
					List<String> stringList = Arrays.asList(msg.getContent().split(" "));
					List<Double> color_coord = new ArrayList<Double>();
					try {
						color_coord.add(Double.parseDouble(stringList.get(0)));
						color_coord.add(Double.parseDouble(stringList.get(1)));
						color_coord.add(Double.parseDouble(stringList.get(2)));
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					signalMessage.put(msg.getSender().getLocalName(), color_coord);
				} else {
					block();
				}
			}
		});


		System.out.println("I'm vehicleAgent ");
		System.out.println("My Name is " + this.getAID().getName());
	}

	public void initialize() {

		nextTarget = gui.roadPoint.getStartPointInfo();
		//agentShape.setFill(Color.color(Math.random(), Math.random(), Math.random()));
		
		if (nextTarget != null) {

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					agentShape.setTranslateX(nextTarget.X);
					agentShape.setTranslateY(nextTarget.Y);
				}
			});

		} else {
			System.out.println("problem of initializing...");
			doDelete();
		}

	}

	@Override
	protected void beforeMove() {
		System.out.println("Avant de migrer vers une nouvelle location .....");
	}

	@Override
	protected void afterMove() {
		System.out.println("Je viens d'arriver à une nouvelle location .....");
	}

	@Override
	protected void takeDown() {
		System.out.println("I'm going to die ...");
	}

	@Override
	protected void onGuiEvent(GuiEvent guiEvent) {
		// TODO Auto-generated method stub

		// }

	}
}
