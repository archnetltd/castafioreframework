package org.castafiore.shoppingmall.orders;

import java.util.Map;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.shoppingmall.message.ui.EXNewMessagePopup;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.DefaultCellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;

public class CustomerPaymentCellRenderer extends DefaultCellRenderer implements Event {

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		Object value = model.getValueAt(column, row, page);
		if(column == 4){
			return new EXContainer("message", "button").setText("Send Message").addEvent(this, CLICK).setAttribute("msg", value.toString());
		}else{
			return super.getComponentAt(row, column, page, model, table);
		}
		
	}

	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
		// TODO Auto-generated method stub
		super.onChangePage(component, row, column, page, model, table);
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		Merchant m = MallUtil.getMerchant("elieandsons");
		EXNewMessagePopup p = new EXNewMessagePopup("elieandsons");
		p.initForSendMail(m.getEmail()	, m.getEmail(), "Message for FS Code :" + container.getAttribute("msg"));
		container.getAncestorOfType(PopupContainer.class).addPopup(p.setStyle("z-index", "3001"));
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
