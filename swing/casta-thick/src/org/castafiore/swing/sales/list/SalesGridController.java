package org.castafiore.swing.sales.list;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.castafiore.swing.sales.SaveContractDTO;
import org.openswing.swing.message.receive.java.Response;
import org.openswing.swing.message.receive.java.VOListResponse;
import org.openswing.swing.table.client.GridController;
import org.openswing.swing.table.java.GridDataLocator;

public class SalesGridController extends GridController implements GridDataLocator {

	@Override
	public Response loadData(int action, int startIndex, Map filteredColumns,
			ArrayList currentSortedColumns,
			ArrayList currentSortedVersusColumns, Class valueObjectType,
			Map otherGridParams) {
		List<SaveContractDTO> result = new ArrayList<SaveContractDTO>();
		for(int i =0; i<200;i++){
			SaveContractDTO d= new SaveContractDTO();
			d.setContactFirstName("First Name");
			d.setContactLastName("Last Name");
			d.setDate(new Date());
			d.setPlan("C");
			d.setPlanDetail("Family cover");
			d.setStatus("Ongoing");
			result.add(d);
			
		}
		return new VOListResponse(result,false,result.size());
	}
	
	

}
