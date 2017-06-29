package properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.PfadErmitteln;
import stage.SystemTrayIcon;

public class WindowProperties {

	// Klassen
	SystemTrayIcon systemTrayIcon;

	// Variablen
	File fileProperties;

    private String osName;                					// Name des Betriebssystems
	private String userHome;            					// bei Windows C:\Dokumente und Einstellungen\User
	private String applicationData;   						// bei Windows C:\Dokumente und Einstellungen\User\Anwendungsdaten
	private File workingDirectoryPath = new File(".");      // bei Windows C:\Dokumente und Einstellungen\User\Anwendungsdaten
	private final String programGroupName = "IndivikarAG";  // GruppenName wird benötigt um einen Ordner abzulegen im Arbeitsverzeichnis(AppData)

	// Modul RP
	private boolean isFensterModulRPMax;
	private Double fensterModulRPPosX;
	private Double fensterModulRPPosY;
	private Double fensterModulRPDividerPositions1;
	private Double fensterModulRPDividerPositions2;
	private Double fensterModulRPWidth;
	private Double fensterModulRPHeight;

	public WindowProperties() {
//		PfadErmitteln pe = new PfadErmitteln(new String[]{SystemTrayIcon.propertiesFile}, false , false );
//		this.fileProperties = pe.getErmittelterFile();


	}

	private void userVerzeichnisAppData(){
		// https://www.tutorials.de/threads/java-app-unter-windows-keine-schreibrechte.354265/
        userHome = System.getProperty("user.home");
        osName = System.getProperty("os.name").toLowerCase();

        if (osName.contains("windows")){
          applicationData = System.getenv("APPDATA");
             if (applicationData != null) {
	               this.workingDirectoryPath = new File( applicationData, ""+ programGroupName);
	               System.out.println("AppData-Ordner 1: " + workingDirectoryPath);
	               if (erstelleIndivikarOrdnerInAppData()) {
	            	   erstelleProgrammOrdnerInAppData();
	               }

             }else{
	               this.workingDirectoryPath = new File( userHome, ""+ programGroupName);
	               System.out.println("AppData-Ordner 2: " + workingDirectoryPath);
             }
        }else if ( osName.contains("linux") || osName.contains("mac") || osName.contains("solaris") ){
        	this.workingDirectoryPath = new File(userHome, ""+ programGroupName);
            System.out.println("AppData-Ordner 3: " + workingDirectoryPath);
        }
	}

	public boolean erstelleIndivikarOrdnerInAppData(){
		if (!workingDirectoryPath.exists()) {
			if (workingDirectoryPath.mkdir()) {
				System.out.println("Ordner erstellt");
				return true;
			}else {
				System.out.println("Ordner nicht erstellt");
				// TODO - Meldung für das Projekt anpassen
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Fehler");
				alert.setHeaderText("Fehler");
				alert.setContentText(	"Der Ordner\n\""
										+ workingDirectoryPath
										+ "\"\nkonnte nicht erstellt werden.\n\n"
										+ "Weitere Nutzung der Software ist somit nicht mehr möglich.\n"
										+ "Bitte wenden Sie sich an den Support.");

				alert.showAndWait();
			}
		}else {
			System.out.println("Ordner gibt es schon");
			return true;
		}
		return false;
	}

	public boolean erstelleProgrammOrdnerInAppData(){
		File programmOrdner = new File(workingDirectoryPath + File.separator + systemTrayIcon.getProgrammName());
		if (!programmOrdner.exists()) {
			if (programmOrdner.mkdir()) {
				System.out.println("Ordner erstellt - " + programmOrdner);
				return true;
			}else {
				System.out.println("Ordner nicht erstellt");
				// TODO - Meldung für das Projekt anpassen
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Fehler");
				alert.setHeaderText("Fehler");
				alert.setContentText(	"Der Ordner\n\""
										+ programmOrdner
										+ "\"\nkonnte nicht erstellt werden.\n\n"
										+ "Eine fehlerfreie Nutzung der Software somit nicht mehr gewährleistet werden.\n"
										+ "Bitte wenden Sie sich an unseren Support.");

				alert.showAndWait();
			}
		}else {
			System.out.println("Ordner gibt es schon - " + programmOrdner);
			return true;
		}
		return false;
	}

	public boolean gibtEsPropertiesFile() {
		System.out.println("fileProperties: " + fileProperties);
		if(fileProperties.exists()){
			return true;
    	}
        return false;
    }

	public boolean istFileEmpty(){

		if(fileProperties.exists()){

			Properties prop = new Properties();
			FileInputStream inputStream;
			try {

				inputStream = new FileInputStream(fileProperties);
				prop.load(inputStream);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(prop.isEmpty()){
				return true;
			}
		}

		return false;
	}

	public boolean erstellePropertiesFile() {
        if (fileProperties != null) {
            try {
            	fileProperties.createNewFile();
            } catch (IOException e) {
            	Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Fehler");
				alert.setHeaderText("Fehler");
				alert.setContentText(	"Die Datei\n\""
										+ fileProperties
										+ "\"\nkonnte nicht erstellt werden.\n\n"
										+ "Eine fehlerfreie Nutzung der Software somit nicht mehr gewährleistet werden.\n"
										+ "Bitte wenden Sie sich an unseren Support.");

				alert.showAndWait();
                System.err.println("Error creating " + fileProperties.toString());
            }
            if (fileProperties.isFile() && fileProperties.canWrite() && fileProperties.canRead())
                return true;
        }
        return false;
    }

	public String loadStartKoordinatenPropertie() throws IOException {

		String result = "";
		InputStream inputStream = null;

		try {
			Properties prop = new Properties();

			inputStream = new FileInputStream(fileProperties);

			prop.load(inputStream);

			// get the property value and print it out
			String maxFensterModulRP = prop.getProperty("maxFensterModulRP");
			String xModulRP = prop.getProperty("XModulRP");
			String yModulRP = prop.getProperty("YModulRP");
			String fensterWidthModulRP = prop.getProperty("FensterWidthModulRP");
			String fensterHeightModulRP = prop.getProperty("FensterHeightModulRP");

			this.isFensterModulRPMax = Boolean.valueOf(maxFensterModulRP);
			this.fensterModulRPPosX = Double.valueOf(xModulRP);
			this.fensterModulRPPosY = Double.valueOf(yModulRP);
			this.fensterModulRPWidth = Double.valueOf(fensterWidthModulRP);
			this.fensterModulRPHeight = Double.valueOf(fensterHeightModulRP);


		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return result;
	}

	public void saveStartKoordinatenPropertie()  {

		Properties prop = new Properties();
		OutputStream output = null;

		try {
			output = new FileOutputStream(fileProperties);

			// set the properties value
			prop.setProperty("maxFensterModulRP",  isFensterModulRPMax + "");
			prop.setProperty("XModulRP", fensterModulRPPosX + "");
			prop.setProperty("YModulRP", fensterModulRPPosY + "");

			prop.setProperty("FensterWidthModulRP",  fensterModulRPWidth + "");
			prop.setProperty("FensterHeightModulRP", fensterModulRPHeight + "");

			prop.store( output, "Fenster-Eigenschaften" );
			// save properties to project root folder

//			prop.store(output, null);
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
// Getter

	// Modul RP
	public boolean isFensterModulRPMax() {return isFensterModulRPMax;}
	public Double getFensterModulRPPosX() {return fensterModulRPPosX;}
	public Double getFensterModulRPPosY() {return fensterModulRPPosY;}
	public Double getFensterModulRPWidth() {return fensterModulRPWidth;}
	public Double getFensterModulRPHeight() {return fensterModulRPHeight;}


// Setter

	// Modul RP
	public void setFensterModulRPMax(boolean isFensterModulRPMax) {this.isFensterModulRPMax = isFensterModulRPMax;}
	public void setFensterModulRPPosX(Double fensterModulRPPosX) {this.fensterModulRPPosX = fensterModulRPPosX;}
	public void setFensterModulRPPosY(Double fensterModulRPPosY) {this.fensterModulRPPosY = fensterModulRPPosY;}
	public void setFensterModulRPWidth(Double fensterModulRPWidth) {this.fensterModulRPWidth = fensterModulRPWidth;}
	public void setFensterModulRPHeight(Double fensterModulRPHeight) {this.fensterModulRPHeight = fensterModulRPHeight;}

	public void setMain(SystemTrayIcon systemTrayIcon) {
		this.systemTrayIcon = systemTrayIcon;
		userVerzeichnisAppData();
		this.fileProperties = new File(workingDirectoryPath + File.separator + systemTrayIcon.getProgrammName() + File.separator + systemTrayIcon.getPropertiesFile());
	}

}
