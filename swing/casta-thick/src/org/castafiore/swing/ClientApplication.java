package org.castafiore.swing;

import java.util.Hashtable;
import java.util.Properties;

import javax.swing.JSlider;

import org.openswing.swing.internationalization.java.EnglishOnlyResourceFactory;
import org.openswing.swing.permissions.client.LoginDialog;
import org.openswing.swing.permissions.java.ButtonsAuthorizations;
import org.openswing.swing.util.client.ClientSettings;


/**
 * <p>Title: OpenSwing Demo</p>
 * <p>Description: Used to start application from main method:
 * it creates a grid frame and a detail frame accessed by double click on the grid.
 * The detail form contains a tree lookup.</p>
 * <p>Copyright: Copyright (C) 2006 Mauro Carniel</p>
 * <p> </p>
 * @author Mauro Carniel
 * @version 1.0
 */
public class ClientApplication {






  public ClientApplication() {

    Hashtable domains = new Hashtable();
    Properties props = new Properties();
    props.setProperty("this text will be translated","This text will be translated");
    props.setProperty("date","Date");
    props.setProperty("fsCode","FS Code");
    props.setProperty("Standing Order","Standing Order");
    props.setProperty("Cash","Cash");
    props.setProperty("Cheque","Cheque");
    props.setProperty("Bank Transfer","Bank Transfer");
    props.setProperty("paymentMethod","Payment Method");
    props.setProperty("pos","Point of sale");
    props.setProperty("customer","Customer");
    props.setProperty("amount","Amount");
    props.setProperty("Search FS Code","Search FS Code");
    props.setProperty("dateCreated","Date Created");
    props.setProperty("code","Code");
    props.setProperty("total","Total");
    props.setProperty("pointOfSale","Point of Sale");
    props.setProperty("chequeNo","Cheque No");
    

    ButtonsAuthorizations auth = new ButtonsAuthorizations();
    auth.addButtonAuthorization("F1",true,false,true);

    ClientSettings clientSettings = new ClientSettings(
        new EnglishOnlyResourceFactory("MUR",props,true),
        domains,
        auth
    );

    ClientSettings.VIEW_MANDATORY_SYMBOL = true;
    ClientSettings.ALLOW_OR_OPERATOR = false;
    ClientSettings.INCLUDE_IN_OPERATOR = false;
    ClientSettings.SHOW_FILTER_SYMBOL = true;

   
    //JSlider slider = new JSlider(0, 20);
    LoginDialog login = new LoginDialog(null,false, new CastafioreLoginController());
    //login.add(slider);
   
    
   // new PaymentGridFrameController();
  }


  public static void main(String[] argv) {
	    new ClientApplication();
	  }





}
