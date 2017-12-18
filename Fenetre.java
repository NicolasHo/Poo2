import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Dimension;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.event.*;
import java.io.*;

public class Fenetre extends JFrame {

	private Placement drawing = new Placement();
	private JPanel pan =new JPanel();
	private String path="";
	private Color menu_color;
	private Color txt_color;
	
	public static void main(String[] args)
	{
		Fenetre fen = new Fenetre();
	}       

	private void ChooseButtonActionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser(path);
    	fileChooser.setFileFilter(new FileNameExtensionFilter("SVG format",
        "svg", "SVG"));
		int returnValue = fileChooser.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION)
		{
			File selectedFile = fileChooser.getSelectedFile();
			path=selectedFile.getPath();
		}
	}

	public Fenetre()
	{
		this.setTitle("Ma première fenêtre Java");
		this.setSize(1620, 737);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menu_color= new Color(107, 106, 104);
		txt_color= new Color(255, 255, 255);
		/**************************************
		 *
		 *		  GESTION DES BOUTONS
		 *
		***************************************/
		JLabel label1 = new JLabel("Algorithme",JLabel.CENTER);
		JLabel label2 = new JLabel("Debug",JLabel.CENTER);

		JButton bouton1 = new JButton("Nouveau un fichier");
		JButton bouton2 = new JButton("Ajouter un fichier");
		JCheckBox rect = new JCheckBox("Rectangles");
		JCheckBox point = new JCheckBox("Points");
		JCheckBox limit = new JCheckBox("limites");

		rect.setSelected(false);	
		point.setSelected(false);
		limit.setSelected(false);

		bouton1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				ChooseButtonActionPerformed(event);
				drawing.setFic(path,false);
			}
		});

		bouton2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				ChooseButtonActionPerformed(event);
				drawing.setFic(path,true);
			}
		});



		JRadioButton simple = new JRadioButton("Simple (stable)");
		simple.setSelected(true);
		JRadioButton dev = new JRadioButton("Développement");
		dev.setSelected(true);


		simple.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				drawing.setAlgo(1);
			}
		});

		dev.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				drawing.setAlgo(2);
			}
		});


   		ButtonGroup algo = new ButtonGroup();
   		algo.add(simple);
   		algo.add(dev);

		rect.addItemListener(new ItemListener() 
		{
	    	public void itemStateChanged(ItemEvent e) 
	    	{
		        drawing.setRect((e.getStateChange() == ItemEvent.SELECTED) );
		    }
		});


		point.addItemListener(new ItemListener() 
		{
	    	public void itemStateChanged(ItemEvent e) 
	    	{
		        drawing.setPoint((e.getStateChange() == ItemEvent.SELECTED) );
		    }
		});		

		limit.addItemListener(new ItemListener() 
		{
	    	public void itemStateChanged(ItemEvent e) 
	    	{
		        drawing.setLimit((e.getStateChange() == ItemEvent.SELECTED) );
		    }
		});

		/**************************************
		 *
		 *		  GESTION DE L'AFFICHAGE
		 *
		***************************************/
		label1.setForeground(txt_color);
		label2.setForeground(txt_color);
		rect.setForeground(txt_color);
		point.setForeground(txt_color);
		limit.setForeground(txt_color);
		simple.setForeground(txt_color);
		dev.setForeground(txt_color);

		rect.setBackground(menu_color);
		point.setBackground(menu_color);
		limit.setBackground(menu_color);
		simple.setBackground(menu_color);
		dev.setBackground(menu_color);

		JPanel cell1 = new JPanel();
		cell1.setBackground(menu_color);
		cell1.setPreferredSize(new Dimension(220, 700));	

		drawing.setBackground(Color.WHITE);
		drawing.setPreferredSize(new Dimension(1400, 700));


		JPanel content = new JPanel();
		content.setPreferredSize(new Dimension(1500, 700));

		GridBagConstraints gbc = new GridBagConstraints();
		content.setLayout(new GridBagLayout());
		cell1.setLayout(new GridBagLayout());

		//On positionne la case de départ du composant
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.gridwidth = 1;
		content.add(cell1, gbc);
		gbc.gridx = 1;
		content.add(drawing, gbc);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.ipady = 10;
		cell1.add(bouton1, gbc);
		gbc.gridy = 1;
		cell1.add( Box.createVerticalGlue(),gbc);
		gbc.gridy = 2;
		cell1.add(bouton2, gbc);
		gbc.gridy = 3;
		cell1.add( Box.createVerticalGlue(),gbc);
		gbc.gridy = 4;
		cell1.add(label1, gbc);
		gbc.gridy = 5;
		cell1.add(simple, gbc);
		gbc.gridy = 6;
		cell1.add(dev, gbc);
		gbc.gridy = 7;
		cell1.add( Box.createVerticalGlue(),gbc);
		gbc.gridy = 8;
		cell1.add(label2, gbc);
		gbc.gridy = 9;
		cell1.add(rect, gbc);
		gbc.gridy = 10;
		cell1.add(point, gbc);
		gbc.gridy = 11;
		cell1.add(limit, gbc);

		this.setContentPane(content);
		this.setVisible(true);
	}
}