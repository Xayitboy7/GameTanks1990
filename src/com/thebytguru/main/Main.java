package com.thebytguru.main;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Timer;

import com.thebytguru.display.Display;

public class Main {

	public static void main(String[] args) {

		Display.create(1200, 1000, "Tanks_Hayitboy", 0xff00ff00,3);
		
		Timer t = new Timer( 1000  /60, new AbstractAction() {
			 
			public void actionPerformed(ActionEvent e) {
				Display.clear();
				Display.render();
				Display.swapBuffers();
			}
		});
		t.setRepeats(true);
		t.start();
	}

}
