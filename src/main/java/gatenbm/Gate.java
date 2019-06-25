package gatenbm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;
import org.openide.util.Utilities;
import org.openide.util.actions.Presenter;

@ActionID(
        category = "Gate",
        id = "de.markiewb.netbeans.sample.PopupAction"
)
@ActionRegistration(
        displayName = "#CTL_PopupAction", lazy = false
)
@ActionReferences(
        {
                @ActionReference(path = "Loaders/text/x-java/Actions", position = 2100, separatorBefore = 1050, separatorAfter = 2150),
                @ActionReference(path = "Editors/text/x-java/Popup", position = 1620, separatorBefore = 1570, separatorAfter = 1670)
        })
@Messages("CTL_PopupAction=Gate")
public final class Gate extends AbstractAction implements ActionListener, Presenter.Popup
{

        @Override
        public void actionPerformed(ActionEvent e)
        {
                //NOP
        }

        @Override
        public JMenuItem getPopupPresenter()
        {
                JMenu main = new JMenu(Bundle.CTL_PopupAction());
                List<? extends Action> actionsForPath = Utilities.actionsForPath("Actions/Gate/SubActions");
                for (Action action : actionsForPath)
                {
                        main.add(action);
                }
                return main;
        }
}
