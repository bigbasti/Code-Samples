package game;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

//Diese Klasse stellt das Hauptfenster des Speils dar

public class WinMain extends JFrame{

	//Steuerelemente
	public gPanel gP;					//GamePanel => SpielflŠche
	public cPanel gC;					//GamePanel => Controls
	public wPanel gW;					//WinPanel  => Sieht man wenn man gewonnen hat
	
	
	public WinMain(String title){
		//Die Vrbereitungen für das Hauptfenster treffen
		Game.log.write("Hauptfenster erstellen...");
		this.setTitle(title);
		this.setSize(300, 400);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Das Panel fŸr das Spielfeld
		Game.log.write("Spielpanel erstellen...");
		this.gP = new gPanel();
		this.gP.setBounds(0, 0, 300, 300);
		this.gP.setBackground(Color.white);
		this.add(gP);
		
		//Das Panel fŸr die Controls
		Game.log.write("Controlpanel erstellen...");
		this.gC = new cPanel();
		this.gC.setBounds(0, 300, 300, 100);
		this.gC.setBackground(Color.red);
		this.add(gC);
		
		//Das Panel für das Abschluss Screen 
		Game.log.write("Winpanel erstellen...");
		this.gW = new wPanel();
		this.gW.setBounds(0, 300, 300, 100);
		this.gW.setBackground(Color.white);
		//this.gW.setVisible(false);
		this.add(gW);
		
		//Maus events abfragen
		this.gC.addMouseListener(new MouseListener(){

			//PrŸfen wo auf dem Feld geklickt wurde
			public void mouseClicked(MouseEvent e) {
				if(e.getPoint().y > 10 && e.getPoint().y < 40){
					if(e.getPoint().x > 5 && e.getPoint().x < 35){
						Game.log.write("ROT");
						Game.proceedClick(Color.red, Game.squares.get(0),false,false,false,false);
					}
					if(e.getPoint().x > 40 && e.getPoint().x < 70){
						Game.log.write("BLAU");
						Game.proceedClick(Color.blue, Game.squares.get(0), false,false,false,false);
					}
					if(e.getPoint().x > 75 && e.getPoint().x < 105){
						Game.log.write("CYAN");
						Game.proceedClick(Color.cyan, Game.squares.get(0), false,false,false,false);
					}
					if(e.getPoint().x > 110 && e.getPoint().x < 145){
						Game.log.write("GRÜN");
						Game.proceedClick(Color.green, Game.squares.get(0), false,false,false,false);
					}
					if(e.getPoint().x > 145 && e.getPoint().x < 175){
						Game.log.write("PINK");
						Game.proceedClick(new Color(255,0,255), Game.squares.get(0), false,false,false,false);
					}
					if(e.getPoint().x > 180 && e.getPoint().x < 210){
						Game.log.write("GELB");
						Game.proceedClick(Color.yellow, Game.squares.get(0), false,false,false,false);
					}
				}
				//Nach dem Klickereignis alle Squares wieder auf "unbenuzt" stellen
				for(Square s : Game.squares){
					s.setUsed(false);
				}
				//Prüfen ob der Benutzer vielleicht schon gewonnen hat
				if(Game.checkWin() == true){
					gC.setVisible(false);
					gW.setVisible(true);
				}
				//Prüfen, ob alle Versuche Aufgebraucht sind und dann das Spiel abbrechen
				if (Game.incTries() == false){
					gC.setVisible(false);
					//Prüfen, ob mit dem Letzten Zug noch das Spiel gewonnen wurde
					
					if(Game.checkWin() == true){
						gW.setVisible(true);
					}else{
						Game.gameActive = false;		//Spiel beenden wenn man verloren hat!
						gW.setVisible(true);
					}
				}
				//Anzeige Aktualisieren
				gC.repaint();
			}

			public void mouseEntered(MouseEvent e) {

			}

			public void mouseExited(MouseEvent e) {

			}

			public void mousePressed(MouseEvent e) {

			}

			public void mouseReleased(MouseEvent e) {
				
			}
			
		});
		
		this.setVisible(true);
	}
	
	//-------------------------------------------Innere Klassen
	//Alle inneren klassen erben von JPanel und überschreiben
	//die peintComponent-Methode, die jedesmal aufgerufen wird wenn sich 
	//das fenster oder dessen Inhalt verändert!
	//Somit geht man sicher, dass man immer das aktuellste Bild sieht
	
	//Innere Klasse die die Darstellung des "Win"-Feldes regelt
	public class wPanel extends JPanel{
		@Override public void paintComponent(Graphics gr){
			Game.log.write("Winpanel zeichnen...");
			Graphics2D g2d  = (Graphics2D) gr;
			if(Game.gameActive == true){			//Gewonnen -> Ausgeben
				g2d.setColor(Color.black);
				g2d.fillRect(0, 0, 300, 100);
				g2d.setColor(Color.yellow);
				g2d.drawString("Heeey du hast es in "+Game.triesUsed+" ZŸgen geschafft!", 10, 30);
				g2d.drawString("Versuchs in unter 18 ZŸgen zu schaffen!", 10, 45);
			}else{									//Verloren -> Ausgeben
				g2d.setColor(Color.black);
				g2d.fillRect(0, 0, 300, 100);
				g2d.setColor(Color.red);
				g2d.drawString("GAME OVER!!", 110, 30);
			}
		}
	}
	
	//Innere Klasse, die die Darstellunge des Spielfeldes regelt
	public class gPanel extends JPanel{
		@Override public void paintComponent(Graphics gr){
			Graphics2D g2d  = (Graphics2D) gr;
	        Game.paintSquares(g2d);
		}
	}
	
	//Innere Klasse, die die Darstellung des Spielstandes und der Runden Buttons regelt
	public class cPanel extends JPanel{
		@Override public void paintComponent(Graphics gr){
			Game.log.write("Aktualisieren der Statusanzeige...");
			Graphics2D g2d  = (Graphics2D) gr;
			g2d.fillRect(0, 0, 300, 100);
	        g2d.setColor(Color.red);
			g2d.fillOval(5, 10, 30, 30);
	        g2d.setColor(Color.blue);
			g2d.fillOval(40, 10, 30, 30);
	        g2d.setColor(Color.cyan);
			g2d.fillOval(75, 10, 30, 30);
	        g2d.setColor(Color.green);
			g2d.fillOval(110, 10, 30, 30);
	        g2d.setColor(new Color(255,0,255));
			g2d.fillOval(145, 10, 30, 30);
	        g2d.setColor(Color.yellow);
			g2d.fillOval(180, 10, 30, 30);
			g2d.setColor(Color.white);
			g2d.drawString(Game.triesUsed+" / "+Game.MAXUSES, 220, 30);
			g2d.drawString("By Sebastian Gross, yaaaaaaay!", 50, 60);
		}
	}
}
