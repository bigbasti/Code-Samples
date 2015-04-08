/*
 * Tool name   : Fill IT
 * Description : This is a Demo of an application port from iPhone to java
 *               its not perfect but could be useful for demonstrating
 *               purposes
 * Author      : Sebastian Gross
 * Webpage     : http://blog.bigbasti.com
 * Version     : 0.3
 * OS          : Tested on Microsoft Windows XP; Max OS X 10.5.8
 * Todo        : Fix Problem with filling Squares on the Top Position
 *
 * Changes     : 
 * 				Fixed Problem with the "End Game" Screen. Its now shown correctly
 * 				Fixed Bug that blocks the upper Squares from changing the color
 * 
 *
 *
 *
 * License     :
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package game;

import java.awt.*;
import java.util.*;

public class Game {

	//Diese Liste beinhaltet alle einzelnen Squares
	public static ArrayList<Square> squares = new ArrayList<Square>();	//Liste mit allen Square-Objekten
	
	public static Logwriter log = new Logwriter();		//Eine Instanz des Logwriters für die Konsolenausgabe
	
	public static WinMain frmMain;						//Eine instanz des hauptformulars!
	
	public static boolean gameActive = true;			//Noch Unbenutzt
	public static int triesUsed = 0;					//Anzahl verbrauchter Versuche
	public static final int MAXUSES = 22;				//Anzahl Maximalversuche, muss zu Beginn festgelegt werden!
	
	public static void main(String[] args){
		//In der mein methode werden nur die Square-Objekte erzeugt und das Hauptfenster initialisiert!
		log.write("Game startet...");
		
		createSquares(12);
		
		frmMain = new WinMain("Bastis Fill it!");
		
	}
	
	public static void proceedClick(Color c, Square s, boolean blockRight, boolean blockTop, boolean blockLeft, boolean blockBottom){
		//Wenn ein Kreis angeklickt wird alle an den oben-links angrenzenden
		//Squares ebgenfalls so einfŠrben
		
		//Vorgehen
		//Wenn der übergebene Square s einen Nachbarn (top, left, bottom, right) hat dann
		//	Prüfen, ob der nachbarsquare auf der Seite die selbe Farbe hat wie s
		//		Wenn bei dem letzten Durchgang nicht in die entgegengesetzte Richtung geprüft wurde
		//			Sich selbst nochmalls aufrufen und weiter in die selbe Wichtung gehen.
		//			Das gehen in die entgegengesetzte Richtung deaktiveiren (durch setzen von true)
		//Wenn man dann keine Nachbarn mehr in der gleichen Farbe hat, den aktuellen Square als "Benutzt" markieren
		//Sodass, er nicht von einem anderen nachbarknoten aufgerufen serden kann!
		//Die Farbe von s in die in c übergebene Wechseln
		//Und die Squares neuzeichnen

		if(s.getNeighbour("right", squares) != null){
			if(s.getNeighbour("right", squares).getColor().getRGB() == s.getColor().getRGB()){
				if(blockRight == false && s.getNeighbour("right", squares).getUsed() == false){
					s.setUsed(true);
					proceedClick(c, s.getNeighbour("right", squares),false, false, true, false);
				}
			}
		}
		if(s.getNeighbour("bottom", squares) != null){
			if(s.getNeighbour("bottom", squares).getColor().getRGB() == s.getColor().getRGB()){
				if(blockBottom == false && s.getNeighbour("bottom", squares).getUsed() == false){
					s.setUsed(true);
					proceedClick(c, s.getNeighbour("bottom", squares), false, true, false, false);
				}
			}
		}
		if(s.getNeighbour("top", squares) != null){
			if(s.getNeighbour("top", squares).getColor().getRGB() == s.getColor().getRGB()){
				if(blockTop == false && s.getNeighbour("top", squares).getUsed() == false){
					s.setUsed(true);
					proceedClick(c, s.getNeighbour("top", squares), false, false, false, true);
				}
			}
		}
		if(s.getNeighbour("left", squares) != null){
			if(s.getNeighbour("left", squares).getColor().getRGB() == s.getColor().getRGB()){
				if(blockLeft == false && s.getNeighbour("left", squares).getUsed() == false){
					s.setUsed(true);
					proceedClick(c, s.getNeighbour("left", squares), true, false, false, false);
				}
			}
		}

		s.setColor(c);
		frmMain.gP.repaint();//paintComponent(frmMain.gP.getGraphics());
	}
	
	public static void paintSquares(Graphics g){
		//Alle Squares in der squares-liste durchgehen und bei jedem Square
		//die paintSquare-Methode aufrufen da er die Farbe gewechselt haben könnte!
		log.write("Squares zeichnen...");
		for(Square s : squares){
			s.paintSquare(g);
		}
	}
	
	public static boolean checkWin(){
		//Prüfen, ob das Spiel mit einem Sieg beendet wurde
		//Durch vergleichen der Farbe von allen Squares mit der des Oben-Links (0,0)
		log.write("Prüfen, ob das Spiel zu Ende ist...");
		Color c = squares.get(0).getColor();
		boolean retVal = true;
		for(Square s : squares){
			log.write(s.getColor().toString());
			if (s.getColor().getRGB() == c.getRGB()){
			}else{
				log.write("Spiel ist noch nicht zu ende! Weitermachen...");
				return false;
			}
		}
		log.write("Spiel ist zu ende; Sieg...");
		return retVal;
	}
	
	public static void createSquares(int fieldSize){
		//Die Square Objekte anlegen, basierend auf der Größe des Feldes!
		log.write("Erstellen der Squares...");
		for (int i = 0; i < (fieldSize); i++){
			for(int j = 0; j < (fieldSize); j++){
				//Einen neuen Square erstellen und diesen in die squares Liste packen
				Square s = new Square(i+j,new Point(i,j),"default",new Point(fieldSize,fieldSize));
				squares.add(s);
			}
		}
	}
	
	public static boolean incTries(){
		//Wenn der benutzer auf einen der Bunten Kreise klickt
		//Den Klick-zähler erhöhen und prüfen, ob alle Versuche aufgebrauchst sind!
		triesUsed++;
		if (triesUsed == MAXUSES){
			return false;
		}else{
			return true;
		}
	}
}
