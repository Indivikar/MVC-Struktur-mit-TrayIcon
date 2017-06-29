package model;

public class Suffix {

	public String endungEntfernen(String dateiName){

		String ohneSuffix = null;
		if ( dateiName.lastIndexOf( '.' ) > 0 ) // das > ist pure Absicht, damit versteckte Dateien nicht als Dateiendung interpretiert werden!
		{
			ohneSuffix = dateiName.substring(0, dateiName.lastIndexOf('.'));
			System.out.println("DateiName ohne Endung: " + ohneSuffix);
		}
		else
		{
			ohneSuffix = "";
		}

		return ohneSuffix;
	}

public String dateiEndung(String dateiName){

		String suffix = null;
		if ( dateiName.lastIndexOf( '.' ) > 0 ) // das > ist pure Absicht, damit versteckte Dateien nicht als Dateiendung interpretiert werden!
		{
		  suffix = dateiName.substring(dateiName.lastIndexOf('.'));
		  System.out.println("DateiEndung: " + suffix);
		}
		else
		{
		  suffix = "";
		}

		return suffix;
	}
}
