package org.castafiore.swing.sales;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import com.nqadmin.swingSet.formatting.SSBooleanField;
import java.awt.BorderLayout;

public class Misc extends JInternalFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Misc frame = new Misc();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Misc() {
		setBounds(100, 100, 450, 300);
		
		SSBooleanField blnfldSsdfsdf = new SSBooleanField();
		blnfldSsdfsdf.setText("ssdfsdf");
		getContentPane().add(blnfldSsdfsdf, BorderLayout.CENTER);

	}

}
