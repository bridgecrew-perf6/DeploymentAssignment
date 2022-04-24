package application;

import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Server extends Application {
	public static String PrimeString(int num) {
		if (num < 2) {
			return "No";
		}
		int i = 2;
		while (i < num) {
			if (num % i == 0) {
				return "No";
			}
			i++;
		}
		return "Yes";
	}

	@Override
	public void start(Stage primaryStage) {
		TextArea ta = new TextArea();
		Scene scene = new Scene(new ScrollPane(ta), 450, 200);
		primaryStage.setTitle("Server");
		primaryStage.setScene(scene);
		primaryStage.show();

		new Thread(() -> {
			try {
				ServerSocket serverSocket = new ServerSocket(8000);
				Platform.runLater(() -> ta.appendText("Server started at " + new Date() + '\n'));

				Socket socket = serverSocket.accept();

				DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
				DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());

				while (true) {
					double inputNum = inputFromClient.readDouble();
					int n = 1;
					double Prime = inputNum * n;
					outputToClient.writeDouble(Prime);

					Platform.runLater(() -> {
						ta.appendText("Number received from client to check prime is: " + inputNum + '\n');
					});
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}).start();
	}

	public static void main(String[] args) {
		launch(args);
	}
}