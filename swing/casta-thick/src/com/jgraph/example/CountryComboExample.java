/*
 * @(#)JAutoComboBoxExample1.java	1.0 09/12/03
 * 
 * Copyright (c) 2001-2004 Gaudenz Alder
 *  
 */
package com.jgraph.example;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jgraph.JAutoComboBox;

public class CountryComboExample extends Applet {

	// From Applet
	public void init() {
		setLayout(new BorderLayout());
		add(new CountryComboPanel());
		setSize(420, 320);
	}

	public static void main(String[] args) {
		System.out.println(JAutoComboBox.VERSION+" CountryComboExample");
		System.out.println("Copyright (C) 2003-2004 JGraph.com");
		JFrame frame = new JFrame("CountryComboExample using "+JAutoComboBox.VERSION);
		// Set Close Operation to Exit
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new CountryComboPanel());
		frame.setSize(420, 320);
		frame.setVisible(true);
	}

	public static class CountryComboPanel extends JPanel {

		public CountryComboPanel() {
			super(null);

			JLabel title = new JLabel("CountryComboExample");
			title.setFont(title.getFont().deriveFont(Font.BOLD, 18));
			title.setBounds(10, 15, 400, 20);
			add(title);

			JLabel label0 = new JLabel("Auto complete combo box:");
			label0.setBounds(10, 60, 240, 20);
			add(label0);

			JLabel label1 = new JLabel("Strict:");
			label1.setBounds(30, 90, 120, 20);
			add(label1);

			JAutoComboBox combo1 = new JAutoComboBox(tempString);
			combo1.setBounds(140, 90, 260, 25);
			add(combo1);

			JLabel label2 = new JLabel("Non-strict:");
			label2.setBounds(30, 120, 120, 20);
			add(label2);

			JAutoComboBox combo2 = new JAutoComboBox(tempString);
			combo2.setStrict(false);
			combo2.setBounds(140, 120, 260, 25);
			add(combo2);

			JLabel label3 = new JLabel("Traditional combo box:");
			label3.setBounds(10, 160, 240, 20);
			add(label3);

			JLabel label4 = new JLabel("Editable:");
			label4.setBounds(30, 190, 120, 20);
			add(label4);

			JComboBox combo3 = new JComboBox(tempString);
			combo3.setEditable(true);
			combo3.setBounds(140, 190, 260, 25);
			add(combo3);
			
			JLabel label5 = new JLabel("Non-editable:");
			label5.setBounds(30, 220, 120, 20);
			add(label5);

			JComboBox combo4 = new JComboBox(tempString);
			combo4.setBounds(140, 220, 260, 25);
			add(combo4);
		}
	}

	public static MyString[] tempString =
		{
			new MyString("Andorra"),
			new MyString("Anguilla"),
			new MyString("Afghanistan"),
			new MyString("Antigua"),
			new MyString("Albania"),
			new MyString("Algeria"),
			new MyString("American Samoa"),
			new MyString("Angola"),
			new MyString("Argentina"),
			new MyString("Armenia"),
			new MyString("Aruba"),
			new MyString("Australia"),
			new MyString("Austria"),
			new MyString("Azerbaijan"),
			new MyString("Bahamas"),
			new MyString("Bahrain"),
			new MyString("Bangladesh"),
			new MyString("Barbados"),
			new MyString("Belarus"),
			new MyString("Belgium"),
			new MyString("Belize"),
			new MyString("Benin"),
			new MyString("Bermuda"),
			new MyString("Bhutan"),
			new MyString("Bolivia"),
			new MyString("Bosnia-Herzegovina"),
			new MyString("Botswana"),
			new MyString("Brazil"),
			new MyString("British Virgin Islands"),
			new MyString("Brunei Darussalam"),
			new MyString("Bulgaria"),
			new MyString("Burkina Faso"),
			new MyString("Burundi"),
			new MyString("Cameroon"),
			new MyString("Canada"),
			new MyString("Cape Verde"),
			new MyString("Cayman Islands"),
			new MyString("Central African Republic"),
			new MyString("Chad"),
			new MyString("Chile"),
			new MyString("China"),
			new MyString("Colombia"),
			new MyString("Comoros"),
			new MyString("Congo"),
			new MyString("Congo (Democratic Republic)"),
			new MyString("Cook Islands"),
			new MyString("Costa Rica"),
			new MyString("Croatia"),
			new MyString("Cuba"),
			new MyString("Cyprus"),
			new MyString("Czech Republic"),
			new MyString("Denmark"),
			new MyString("Djibouti"),
			new MyString("Dominica"),
			new MyString("Dominican Republic"),
			new MyString("Ecuador"),
			new MyString("Egypt"),
			new MyString("El Salvador"),
			new MyString("Equatorial Guinea"),
			new MyString("Eritrea"),
			new MyString("Estonia"),
			new MyString("Ethiopia"),
			new MyString("European Patent Office"),
			new MyString("Falkland Islands"),
			new MyString("Fiji"),
			new MyString("Finland"),
			new MyString("France"),
			new MyString("French Guyana"),
			new MyString("French Polynesia"),
			new MyString("Gabon"),
			new MyString("The Gambia"),
			new MyString("Georgia"),
			new MyString("Germany"),
			new MyString("Ghana"),
			new MyString("Gibraltar"),
			new MyString("Greece"),
			new MyString("Greenland"),
			new MyString("Grenada"),
			new MyString("Guadeloupe"),
			new MyString("Guam"),
			new MyString("Guatemala"),
			new MyString("Guinea"),
			new MyString("Guinea-Bissau"),
			new MyString("Guyana"),
			new MyString("Haiti"),
			new MyString("Honduras"),
			new MyString("Hong Kong"),
			new MyString("Hungary"),
			new MyString("Iceland"),
			new MyString("India"),
			new MyString("Indonesia"),
			new MyString("Iran"),
			new MyString("Iraq"),
			new MyString("Ireland"),
			new MyString("Israel"),
			new MyString("Italy"),
			new MyString("Ivory Coast"),
			new MyString("Jamaica"),
			new MyString("Japan"),
			new MyString("Jordan"),
			new MyString("Kampuchea"),
			new MyString("Kazakhstan"),
			new MyString("Kenya"),
			new MyString("Kiribati"),
			new MyString("Korea (North)"),
			new MyString("Korea (South)"),
			new MyString("Kuwait"),
			new MyString("Kyrgyzstan"),
			new MyString("Laos"),
			new MyString("Latvia"),
			new MyString("Lebanon"),
			new MyString("Lesotho"),
			new MyString("Liberia"),
			new MyString("Libya"),
			new MyString("Liechtenstein"),
			new MyString("Lithuania"),
			new MyString("Luxembourg"),
			new MyString("Macau"),
			new MyString("Macedonia"),
			new MyString("Madagascar"),
			new MyString("Malawi"),
			new MyString("Malaysia"),
			new MyString("Maldives"),
			new MyString("Mali"),
			new MyString("Malta"),
			new MyString("Marshall Islands"),
			new MyString("Martinique"),
			new MyString("Mauritania"),
			new MyString("Mauritius"),
			new MyString("Mayotte"),
			new MyString("Mexico"),
			new MyString("Micronesia"),
			new MyString("Moldova"),
			new MyString("Monaco"),
			new MyString("Mongolia"),
			new MyString("Montserrat"),
			new MyString("Morocco"),
			new MyString("Mozambique"),
			new MyString("Myanmar"),
			new MyString("Namibia"),
			new MyString("Nauru"),
			new MyString("Nepal"),
			new MyString("Netherlands"),
			new MyString("Netherlands Antilles"),
			new MyString("New Caledonia"),
			new MyString("New Zealand"),
			new MyString("Nicaragua"),
			new MyString("Niger"),
			new MyString("Nigeria"),
			new MyString("Niue"),
			new MyString("Norfolk Island"),
			new MyString("Northern Marianas"),
			new MyString("Norway"),
			new MyString("Oman"),
			new MyString("Pakistan"),
			new MyString("Palau"),
			new MyString("Palestine"),
			new MyString("Panama"),
			new MyString("Papua New Guinea"),
			new MyString("Paraguay"),
			new MyString("Peru"),
			new MyString("Philippines"),
			new MyString("Pitcairn"),
			new MyString("Poland"),
			new MyString("Portugal"),
			new MyString("Puerto Rico"),
			new MyString("Qatar"),
			new MyString("Reunion"),
			new MyString("Romania"),
			new MyString("Russia"),
			new MyString("Rwanda"),
			new MyString("San Marino"),
			new MyString("Sao Tome and Principe"),
			new MyString("Saudi Arabia"),
			new MyString("Senegal"),
			new MyString("Serbia & Montenegro"),
			new MyString("Seychelles"),
			new MyString("Sierra Leone"),
			new MyString("Singapore"),
			new MyString("Slovakia"),
			new MyString("Slovenia"),
			new MyString("Solomon Islands"),
			new MyString("Somalia"),
			new MyString("South Africa"),
			new MyString("Spain"),
			new MyString("Sri Lanka"),
			new MyString("St Helena"),
			new MyString("St Kitts-Nevis"),
			new MyString("St Lucia"),
			new MyString("St Pierre & Miquelon"),
			new MyString("St Vincent & the Grenadines"),
			new MyString("Sudan"),
			new MyString("Surinam"),
			new MyString("Swaziland"),
			new MyString("Sweden"),
			new MyString("Switzerland"),
			new MyString("Syria"),
			new MyString("Taiwan, ROC"),
			new MyString("Tajikistan"),
			new MyString("Tanzania"),
			new MyString("Thailand"),
			new MyString("Togo"),
			new MyString("Tokelau"),
			new MyString("Tonga"),
			new MyString("Trinidad and Tobago"),
			new MyString("Tunisia"),
			new MyString("Turkey"),
			new MyString("Turkmenistan"),
			new MyString("Turks and Caicos Islands"),
			new MyString("Tuvalu"),
			new MyString("Uganda"),
			new MyString("Ukraine"),
			new MyString("United Arab Emirates"),
			new MyString("United Kingdom"),
			new MyString("United States of America"),
			new MyString("Uruguay"),
			new MyString("Uzbekistan"),
			new MyString("Vanuatu"),
			new MyString("Vatican City"),
			new MyString("Venezuela"),
			new MyString("Viet Nam"),
			new MyString("Virgin Islands (US)"),
			new MyString("Wallis & Futuna Islands"),
			new MyString("Western Sahara"),
			new MyString("Western Samoa"),
			new MyString("Yemen"),
			new MyString("Zambia"),
			new MyString("Zimbabwe") };

	
	public static class MyString {
		protected String value = null;
		public MyString(String value) {
			this.value = value;
		}
		public String toString() {
			return value;
		}
	}
	
}
