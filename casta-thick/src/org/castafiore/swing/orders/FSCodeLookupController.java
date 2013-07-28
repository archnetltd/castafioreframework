package org.castafiore.swing.orders;

import java.awt.Dimension;
import java.util.Collection;

import org.castafiore.swing.payments.PaymentGridFrameController;
import org.openswing.swing.lookup.client.LookupController;
import org.openswing.swing.lookup.client.LookupListener;
import org.openswing.swing.message.receive.java.ValueObject;

public class FSCodeLookupController extends LookupController implements LookupListener{

	private PaymentGridFrameController controller;
	public FSCodeLookupController(PaymentGridFrameController controller) {
		super();
		this.controller = controller;
		this.setCodeSelectionWindow(GRID_AND_PANEL_FRAME);
		setLookupDataLocator(new LookupFSCodeLocator());
		setLookupValueObjectClassName(FSCodeVO.class.getName());
		this.addLookup2ParentLink("fsCode", "fsCode");
		this.addLookup2ParentLink("customer", "customer");
		this.setPreferredWidthColumn("fsCode", 120);
		this.setPreferredWidthColumn("customer", 330);
		this.setSortableColumn("fsCode", true);
		this.setSortableColumn("customer", true);
		this.setFilterableColumn("fsCode", true);
		this.setFilterableColumn("customer", true);
	    this.setAllColumnVisible(true);
	    this.addLookupListener(this);
	    setFramePreferedSize(new Dimension(500, 500));
	    
	}
	
	
	
	@Override
	public void codeValidated(boolean validated) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void codeChanged(ValueObject parentVO,
			Collection parentChangedAttributes) {
		//System.out.println("sdfsfs");
		FSCodeVO p = (FSCodeVO)parentVO;
		controller.setFsCode(p);
		
	}
	
	@Override
	public void beforeLookupAction(ValueObject parentVO) {
		System.out.println("sfsfsdfs");
		
	}
	
	

}
