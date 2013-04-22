package org.castafiore.swing.orders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JTree;

import org.castafiore.swing.payments.PaymentService;
import org.openswing.swing.lookup.client.LookupDataLocator;
import org.openswing.swing.message.receive.java.Response;
import org.openswing.swing.message.receive.java.VOListResponse;


public class LookupFSCodeLocator extends LookupDataLocator{
	private VOListResponse response = null;

	public LookupFSCodeLocator() {
		super();
	}

	@Override
	public Response validateCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response loadData(int action, int startIndex, Map filteredColumns,ArrayList currentSortedColumns,ArrayList currentSortedVersusColumns, Class valueObjectType) {
		
		if(response == null){
			List rows = new PaymentService().getFSCodes();
			response = new VOListResponse(rows,false,rows.size());
		}
		return response;
		
	}

	@Override
	public Response getTreeModel(JTree tree) {
		// TODO Auto-generated method stub
		return null;
	}

}
