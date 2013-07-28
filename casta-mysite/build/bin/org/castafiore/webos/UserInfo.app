import org.castafiore.spring.SpringUtil;
import org.castafiore.community.ui.users.EXUserForm;

def user = SpringUtil.getSecurityService().loadUserByUsername(Util.getRemoteUser());
def form = new EXUserForm(user);
root.addChild(form);