package org.castafiore.swing.orders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
		
		if(currentSortedColumns.size() >0){
			//order by fscode
			final String col = currentSortedColumns.get(0).toString();
			List<FSCodeVO> rows = response.getRows();
			 String type = "ASC";
			if(currentSortedVersusColumns.size() > 0){
				type = currentSortedVersusColumns.get(0).toString();
			}
			
			final String type_ = type;
			
			Collections.sort(rows, new Comparator<FSCodeVO>() {

				@Override
				public int compare(FSCodeVO o1, FSCodeVO o2) {
					if(type_.equalsIgnoreCase("ASC")){
						return compare_(o1, o2);
					}else{
						return compare_(o1, o2)*-1;
					}
				}
				
				public int compare_(FSCodeVO o1, FSCodeVO o2) {
					if(col.equals("fsCode")){
						
						if(o1.getFsCode() == null){
							return -1;
						}else if(o2.getFsCode() == null){
							return 1;
						}
						return o1.getFsCode().toLowerCase().compareTo(o2.getFsCode().toLowerCase());
					}else{
						if(o1.getCustomer() == null){
							return -1;
						}else if(o2.getCustomer() == null){
							return 1;
						}
						return o1.getCustomer().toLowerCase().compareTo(o2.getCustomer().toLowerCase());
					}
				}
			});
		}
		
		return response;
		
	}

	@Override
	public Response getTreeModel(JTree tree) {
		// TODO Auto-generated method stub
		return null;
	}

}
