package com.piscope;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Path;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;

public class PiMain extends Application {

	// Variable declarations

	// Line Path Declaratrions
	Path linePath;
	PiController piController;
	String label;
	
	//Measurement Variables
	int sf; // Scaling Factor

	// Axis Declarations
	double xa1, xa2, ya1, ya2, diff;

	@Override
	public void start(Stage primaryStage) {
		try {
			// Create a Stage and a Scene
			FXMLLoader fxmlLoader = new FXMLLoader();
			BorderPane root = fxmlLoader.load(getClass().getResource(
					"PiView.fxml").openStream());
			piController = (PiController) fxmlLoader.getController();
			Scene PiScene = new Scene(root, 1150, 720);

			// Add Mouse Handler to the Scene
			PiMain.MouseHandler mouseHandler = new PiMain.MouseHandler(root);

			// Associate Handler to various Mouse Events
			root.setOnMouseClicked(mouseHandler);
			root.setOnMouseDragged(mouseHandler);
			root.setOnMousePressed(mouseHandler);
			root.setOnMouseReleased(mouseHandler);
			root.setOnMouseMoved(mouseHandler);
			root.setOnMouseDragEntered(mouseHandler);

			// Add Path for the line and Path definition
			linePath = new Path();
			linePath.setStrokeWidth(1.5);
			linePath.setStroke(Color.BLACK);
			root.getChildren().add(linePath);

			PiScene.getStylesheets().add(
					getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(PiScene);
			primaryStage.getIcons().add(
					new Image(PiMain.class.getResourceAsStream("icon.png")));
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class MouseHandler implements EventHandler<MouseEvent> {

		// Variable Declarations
		private boolean gotFirst = false;
		private Line line;
		private Pane pane;
		private double x1, y1, x2, y2;
		private LineHandler lineHandler;

		public MouseHandler(Pane pane) {
			this.pane = pane;
			lineHandler = new LineHandler(pane);
		}

		@Override
		public void handle(MouseEvent event) {
			if (event.getClickCount() == 2 && event.isPrimaryButtonDown()) {
				if (!gotFirst) {

					x1 = x2 = event.getX();
					xa1 = piController.getxAxis(xa1);
					y1 = y2 = event.getY();
					ya1 = piController.getyAxis(ya1);
					line = new Line(x1, y1, x2, y2);
					pane.getChildren().add(line);
					gotFirst = true;
				}
				
				else {
	        	 	line.setOnMouseEntered( lineHandler );
	                line.setOnMouseExited( lineHandler );
	                line.setOnMouseDragged( lineHandler );
	                line.setOnMousePressed( lineHandler );
	                // to consume the event
	                line.setOnMouseClicked( lineHandler );
	                line.setOnMouseReleased( lineHandler );
	                line = null;
	                gotFirst = false;
				}
				


			}
			 else {
		            if( line != null ) {
		                x2 = event.getX();
		                xa2=piController.getxAxis(xa2);
		                y2 = event.getY();
		                ya2=piController.getyAxis(ya2);
		                // update line
		                line.setEndX( x2 );
		                line.setEndY( y2 );
		                diff=ya2-ya1;
		                if (diff <=1)
		                	sf=1000;
		                else if(diff>1 && diff<10)
		                	sf=10000;
		                else if(diff>=10 && diff<100)
		                	sf=100000;
		                else if(diff>=100 && diff<1000)
		                	sf=1000000;
		                else if(diff>=1000 && diff<10000)
		                	sf=10000000;
		                else if(diff>=10000 && diff<100000)
		                	sf=100000000;
		                	
		              label = String.format("Voltage : %f V  Time: %f ms Frequency : %f Hz", xa2-xa1,ya2-ya1, (1/(ya2-ya1))*sf);        
				      piController.update(label);
		        }
		}

	}

	class LineHandler implements EventHandler<MouseEvent> {
		double x, y;
		Pane pane;

		public LineHandler(Pane pane) {
			this.pane = pane;
		}

		@Override
		public void handle(MouseEvent e) {

		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}
