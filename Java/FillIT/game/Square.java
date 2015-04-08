package game;

import java.awt.*;
import java.util.*;

public class Square {
	
	//Quadratgröße in pixel hier einstellen:
	final int SQUARESIZE = 25;					//Squaregröße in Pixeln
	
	int id;										//Noch unbenutzt
	Point pos;									//Die Logische Position auf dem Spielfeld
	Color color;								//Die Aktuelle Farbe des Square
	String theme;								//Noch unbenutzt
	Point fieldSize;							//Die Größe des Spielfeldes, aufdem sich der Square befindet
	boolean used = false;						//Gibt an ob der Square in dem aktuellen Durchlauf schon seine Farbe geändert hat
	
	Logwriter log = new Logwriter();			//Logwriterinstanz für die Konsolenausgabe
	
	public Square (int id, Point pos, String theme, Point fieldSize){
		//Im konstrucktor werden nur die Übergebenen Werte in die Klassenvariablen geschrieben
		this.id = id;
		this.pos = pos;
		this.theme = theme;
		this.fieldSize = fieldSize;
		
		//Die anfangsfarbe für den Square wird automatisch hier generiert!
		this.color = new ColorGenerator().generateColor();
		
		//log.write("Neuer Square erstellt. Farbe: "+this.color.toString()+" Pos: "+this.pos.toString());
	}
	
	//-----------------------------Getter-methoden für die Klassenvariablen
	public Point getPos(){
		return this.pos;
	}
	
	public Color getColor(){
		return this.color;
	}
	
	public boolean getUsed(){
		return this.used;
	}
	//---------------------------------------------------------------------
	
	//-----------------------------Setter-Methoden für die Klassenvariablen
	public void setColor(Color c){
		this.color = c;
	}
	
	public void setUsed(boolean wert){
		this.used = wert;
	}
	//---------------------------------------------------------------------
	
	public void paintSquare(Graphics g){
		//Den Square mit der Farbe aus der klassenvarible ausfüllen
		g.setColor(color);
		g.fillRect(pos.x*SQUARESIZE, pos.y*SQUARESIZE, SQUARESIZE, SQUARESIZE);
	}
	
	public Square getNeighbour(String ort, ArrayList<Square> squares){
		//Diese Funktion gibt den Nachbar-Square von diesem zurück
		//Undzwar den Nachbarn, der als ort angegeben wird (top, left, bottom right)
		
		//Dabei wird erst geprüft, ob der Square sich am Rand des Spielfeldes befindet
		//Wenn das nicht so ist, Werden alle Squares in der übergebenen Liste squares
		//durchlaufen, bis ein Suqare gefunden wird dessen Position neben dem eigenen liegt!
		
		//Wird kein nachbarsquare gefunden oder der gewünschte Nachbar ist der Rand wird null zurückgegeben!
		Square retSq = null;
		if (ort.equals("top")){
			if(this.pos.y == 0){
				//Wenn aktueller Square schon der oberste ist null zurückgeben!
				retSq =  null;
			}else{
				for(Square s : squares){
					if(s.getPos().y == (this.getPos().y-1) && s.getPos().x == (this.getPos().x)){
						return s;
					}
				}
			}
		}else if (ort.equals("right")){
			if(this.pos.x >= fieldSize.x-1){
				//Wenn aktueller Square schon der "rechteste" ist null zurückgeben!
				return null;
			}else{
				for(Square s : squares){
					if(s.getPos().x == (this.getPos().x+1) && s.getPos().y == (this.getPos().y)){
						return s;
					}
				}
			}
		}else if (ort.equals("bottom")){
			if(this.pos.y == fieldSize.y){
				//Wenn aktueller Square schon der unterste ist null zurückgeben!
				return null;
			}else{
				for(Square s : squares){
					if(s.getPos().y == (this.getPos().y+1) && s.getPos().x == (this.getPos().x)){
						return s;
					}
				}
			}
		}else if (ort.equals("left")){
			if(this.pos.x == 0){
				//Wenn aktueller Square schon der "linkste" ist null zurückgeben!
				return null;
			}else{
				for(Square s : squares){
					if(s.getPos().x == (this.getPos().x-1) && s.getPos().y == (this.getPos().y)){
						return s;
					}
				}
			}
		}else{
			retSq = null;
		}
		return retSq;
	}
	
	@Override public String toString(){
		//Überschriebene toString-methode zur Kontrolle der Ereignisse
		return new String(pos.x+":"+pos.y+" - "+color.toString());
	}
	
	
}
