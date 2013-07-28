package org.castafiore.swing.sales;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openswing.swing.message.receive.java.Response;
import org.openswing.swing.message.receive.java.VOListResponse;
import org.openswing.swing.table.client.GridController;
import org.openswing.swing.table.java.GridDataLocator;

public class DependentsGridController extends GridController implements GridDataLocator{
	
	private SaveContractDTO dto = null;

	public DependentsGridController() {
		super();
	}
	
	

	public DependentsGridController(SaveContractDTO dto) {
		super();
		this.dto = dto;
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
		
		if(dto != null && dto.getDependants() != null && dto.getDependants().size() > 0){
			for(int i = 0; i < dto.getDependants().size(); i++){
				DependantVO v = dto.getDependants().get(i);
				data.get(i).setGender(v.getGender());
				data.get(i).setId(v.getId());
				data.get(i).setName(v.getName());
				data.get(i).setNic(v.getNic());
			}
			
		}
		
		VOListResponse response = new VOListResponse(data, false, data.size());
		return response;
	}

}
