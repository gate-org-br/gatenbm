package gatenbm.create;

import gate.code.ClassName;
import gate.code.ControlGenerator;
import gate.code.DaoGenerator;
import gate.code.ScreenGenerator;
import gate.util.ClasspathLoader;
import gatenbm.Utils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.util.NbBundle.Messages;

@ActionID(
	category = "Gate/SubActions",
	id = "gateplugin.CreateScreen"
)
@ActionRegistration(
	displayName = "#CTL_CreateScreen"
)
@Messages("CTL_CreateScreen=Create Screen")
public final class CreateScreen implements ActionListener
{

	private final DataObject context;

	public CreateScreen(DataObject context)
	{
		this.context = context;
	}

	@Override
	public void actionPerformed(ActionEvent ev)
	{
		try
		{
			Class<?> type = ClasspathLoader.forName(getClass().getClassLoader(),
				Utils.getClassPath(context), Utils.getClassName(context));

			ClassName dao = Utils.getClassName(type,
				DaoGenerator.getDefault(type),
				"Dao", "Enter with the dao class name");
			if (dao != null)
			{
				ClassName control = Utils.getClassName(type,
					ControlGenerator.getDefault(type),
					"Control", "Enter with the control class name");
				if (control != null)
				{
					ClassName screen = Utils.getClassName(type,
						ScreenGenerator.getDefault(type),
						"Screen", "Enter with the screen class name");
					if (screen != null)
					{
						File file = Utils.createScreen(type, screen, control, dao);
						Utils.openInEditor(FileUtil.toFileObject(file));
					}
				}
			}
		} catch (ClassNotFoundException | FileNotFoundException | RuntimeException ex)
		{
			NotifyDescriptor.Message message = new NotifyDescriptor.Message(ex.getMessage());
			DialogDisplayer.getDefault().notify(message);
		}
	}

}
