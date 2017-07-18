package menu;

import java.awt.Font;
import java.awt.Menu;
import java.awt.PopupMenu;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.stage.Screen;
import javafx.stage.Stage;
import properties.WindowProperties;
import stage.SystemTrayIcon;

public class MenuTrayIcon {

	private PopupMenu popup;

	private WindowProperties configProperties;

	private Stage primaryStage;


	public MenuTrayIcon(WindowProperties configProperties, Stage primaryStage) {

		this.configProperties = configProperties;
		this.primaryStage = primaryStage;


	       try {
	            // ensure awt toolkit is initialized.
	            java.awt.Toolkit.getDefaultToolkit();

	            // app requires system tray support, just exit if there is no support.
	            if (!java.awt.SystemTray.isSupported()) {
	                System.out.println("No system tray support, application exiting.");
	                Platform.exit();
	            }

	            // set up a system tray icon.
	            java.awt.SystemTray tray = java.awt.SystemTray.getSystemTray();
	            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	            InputStream inputStream = SystemTrayIcon.class.getResourceAsStream( "/view/images/img.png" );
	            java.awt.Image image = ImageIO.read(inputStream);
	            java.awt.TrayIcon trayIcon = new java.awt.TrayIcon(image);
	            trayIcon.setImageAutoSize(true);

	            // if the user double-clicks on the tray icon, show the main app stage.
//	            trayIcon.addActionListener(event -> Platform.runLater(this::showStage));

	            // if the user selects the default menu item (which includes the app name),
	            // show the main app stage.

	            java.awt.MenuItem itemModulRP = new java.awt.MenuItem("     Modul RP");
//	            itemModulRP.addActionListener(event -> Platform.runLater(this::showModulRPStage));
//	            itemModulRP.setEnabled(false);
	            itemModulRP.setFont(new Font("", Font.BOLD, 12));

	            if(true){itemModulRP.setEnabled(true);} // Modul an oder aus

	            java.awt.MenuItem itemModulPR  = new java.awt.MenuItem("     Modul PR");
//	            itemModulPR.addActionListener(event -> Platform.runLater(this::showModulPRStage));
//	            itemModulPR.setEnabled(false);
//	            if(true){itemModulPR.setEnabled(true);} // Modul an oder aus

	            Menu submenu = new Menu("     Modul GK");



	            java.awt.MenuItem itemModulGK = new java.awt.MenuItem("Eintrag in Register");
//	            itemModulGK.addActionListener(event -> Platform.runLater(this::showModulGKStage_EintragInRegister));
//	            itemModulGK.setEnabled(false);
//	            if(true){itemModulGK.setEnabled(true);} // Modul an oder aus

	            java.awt.MenuItem itemEinstellungen = new java.awt.MenuItem("Einstellungen");
	            itemEinstellungen.addActionListener(event -> Platform.runLater(this::showStageEinstellungen));

	            // to really exit the application, the user must go to the system tray icon
	            // and select the exit option, this will shutdown JavaFX and remove the
	            // tray icon (removing the tray icon will also shut down AWT).
	            java.awt.MenuItem exitItem = new java.awt.MenuItem("Exit");
	            exitItem.addActionListener(event -> {
//	                notificationTimer.cancel();
	                Platform.exit();
	                tray.remove(trayIcon);
	            });

	            // setup the popup menu for the application.
	            popup = new java.awt.PopupMenu();

	            popup.add("ProtoTOP\n");
	            popup.addSeparator();
	            popup.add(itemModulRP);
	            popup.add(itemModulPR);

	            submenu.add(itemModulGK);
				popup.add(submenu);

	            popup.addSeparator();
	            popup.add(itemEinstellungen);
	            popup.add(exitItem);
	            trayIcon.setPopupMenu(popup);

	            // Erstellt eine Nachricht in einer Sprechblase
//	            notificationTimer.schedule(
//	                    new TimerTask() {
//	                        @Override
//	                        public void run() {
//	                            javax.swing.SwingUtilities.invokeLater(() ->
//	                                trayIcon.displayMessage(
//	                                        "hello",
//	                                        "The time is now " + timeFormat.format(new Date()),
//	                                        java.awt.TrayIcon.MessageType.INFO
//	                                )
//	                            );
//	                        }
//	                    },
//	                    5_000,
//	                    60_000
//	            );

	            tray.add(trayIcon);
	        } catch (java.awt.AWTException | IOException e) {
	            System.out.println("Unable to init system tray");
	            e.printStackTrace();
	        }
	    }


    /**
     * Shows the application stage and ensures that it is brought ot the front of all stages.
     */



    private void showStageEinstellungen() {
        if (primaryStage != null) {
			if(configProperties.gibtEsPropertiesFile()){

				if(!isScreenWidthOKForStage(primaryStage)){
					primaryStage.setX(0);
					primaryStage.setY(0);

					primaryStage.centerOnScreen();
				}
			}
			primaryStage.show();
			primaryStage.toFront();
        }
    }


    private boolean isScreenWidthOKForStage(Stage mainStageModulRP2){

		ObservableList<Screen> screenBounds = Screen.getScreens();

		double screenWidth = 0;

		for (int i = 0; i < screenBounds.size(); i++) {
			screenWidth = screenWidth + screenBounds.get(i).getBounds().getWidth();
			System.out.println(i + " - " + screenBounds.get(i).getBounds().getWidth() + " X " + screenBounds.get(i).getBounds().getHeight());
		}

		if(configProperties.getFensterModulRPPosX() != null || configProperties.getFensterModulRPWidth() != null){
			double stagePosXPlusStage = configProperties.getFensterModulRPPosX() + configProperties.getFensterModulRPWidth();
			System.out.println("screenWidthMinusStage: " + stagePosXPlusStage + "  <=  screenWidth: " + screenWidth + "      scene.getWidth(): " + mainStageModulRP2.getScene().getWidth());

			if (stagePosXPlusStage <= screenWidth) {
				System.out.println("true");
				return true;
			}
		}

		return false;
	}



}



