package org.castafiore.swing.payments;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.swing.orders.FSCodeVO;
import org.openswing.swing.mdi.client.InternalFrame;
import org.openswing.swing.mdi.client.MDIFrame;
import org.openswing.swing.message.receive.java.Response;
import org.openswing.swing.message.receive.java.VOListResponse;
import org.openswing.swing.table.client.GridController;
import org.openswing.swing.table.java.GridDataLocator;

public class PaymentGridFrameController extends GridController implements GridDataLocator{
	
	private PaymentGridFrame grid = null;

	private FSCodeVO fsCode;
	
	public PaymentGridFrameController(FSCodeVO fsCode) {
		super();
		this.fsCode = fsCode;
		grid = new PaymentGridFrame(this);
		MDIFrame.add(grid);
		
	}

	
	
	public FSCodeVO getFsCode() {
		return fsCode;
	}



	public void setFsCode(FSCodeVO vo) {
		this.fsCode = vo;
		grid.setVo(vo);
		grid.reloadData();
	}



	@Override
	public Response loadData(int action, int startIndex, Map filteredColumns,
			ArrayList currentSortedColumns,
			ArrayList currentSortedVersusColumns, Class valueObjectType,
			Map otherGridParams) {
		if(fsCode == null){
			return new VOListResponse(new ArrayList(), false,0);
		}
		List data = new PaymentService().getPayments(fsCode.getFsCode());
		
		return new VOListResponse(data, false, data.size());
	}

	
	
	

}
