package org.castafiore.swing.sales;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openswing.swing.message.receive.java.Response;
import org.openswing.swing.message.receive.java.VOListResponse;
import org.openswing.swing.table.client.GridController;
import org.openswing.swing.table.java.GridDataLocator;

public class DependentsGridController extends GridController implements GridDataLocator{
	
	

	public DependentsGridController() {
		super();
	}

	@Override
	public Response loadData(int action, int startIndex, Map filteredColumns,
			ArrayList currentSortedColumns,
			ArrayList currentSortedVersusColumns, Class valueObjectType,
			Map otherGridParams) {
		List<DependantVO> data = new ArrayList<DependantVO>();
		for(int i =1; i <=7;i++){
			DependantVO vo = new DependantVO();
			vo.setGender("Male");
			vo.setId("" + i);
			vo.setName("");
			vo.setNic("");
			data.add(vo);
		}
		
		VOListResponse response = new VOListResponse(data, false, data.size());
		
		return response;
	}

}
