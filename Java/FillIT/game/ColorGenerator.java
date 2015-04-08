package game;

//Diese Klasse generiert Farben basierend auf dem ausgewählten Theme!

import java.awt.*;

public class ColorGenerator {
	
	private Logwriter log = new Logwriter();				//Instanz des Logwriter
	private String[] colors = new String[]{"255-0-0", 			//rot
										"0-0-255",			//blau
										"0-255-0",			//grün
										"255-255-0",		//gelb
										"0-255-255",		//türkis
										"255-0-255"};		//pink
	
	public ColorGenerator(){
		//Konstrucktor leer
	}

	public Color generateColor(){
		//Diese Funktion wählt zufällig einen String aus dem Array colors aus
		//und wandelt diesen in ein Color-Objekt um, das dan zurückgegeben wird!
		double cIndex = -1;
		//Sichergehen, dass keine zu große Zahl generiert wird!
		while (!(cIndex < 6 && cIndex >= 0)){
			cIndex = (Math.random() * colors.length-1);
		}
		
		//Farben aus dem zufälligen Feld lesen und in einzelne Werte aufteilen
		String[] col = colors[(int) (cIndex+0.5)].split("-");
		
		//Neue Farbe aus den Werten generieren
		Color retCol = new Color(Integer.valueOf(col[0]), 
								Integer.valueOf(col[1]), 
								Integer.valueOf(col[2]));
		
		//log.write("Neue Farbe generiert: "+retCol.toString());
		
		return retCol;
	}
}
