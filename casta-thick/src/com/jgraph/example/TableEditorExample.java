package com.jgraph.example;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.jgraph.JAutoComboBox;

public class TableEditorExample extends JFrame {

	public TableEditorExample() {

		super("ComboTest");

		JTable table = new JTable(new Object[][] { { "AAA", "aaa" },
				{ "BBB", "bbb" }, { "CCC", "ccc" }, { "DDD", "ddd" }, },
				new Object[] { "UPPERCASE", "lowercase " });
		JComboBox cbox = new JComboBox(new Object[] { "AAA", "BBB", "CCC",
				"DDD" });
		final JAutoComboBox acbox = new JAutoComboBox(new Object[] { "aaa", "bbb",
				"ccc", "ddd" });

		cbox.setEditable(true);

		table.getTableHeader().setReorderingAllowed(false);
		table.getColumnModel().getColumn(0).setCellEditor(
				new DefaultCellEditor(cbox));
		table.getColumnModel().getColumn(1).setCellEditor(
				new DefaultCellEditor(acbox));
		table.setRowHeight(22);
		
		acbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Selected Index: "+acbox.getSelectedIndex());
			}
		});

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(100, 100);
		setSize(640, 480);
		setVisible(true);

	}

	public static void main(String args[]) {
		new TableEditorExample();
	}

}