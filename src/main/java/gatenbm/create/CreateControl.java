package gatenbm.create;

import gate.code.ClassName;
import gate.code.ControlGenerator;
import gate.code.DaoGenerator;
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
	id = "gateplugin.CreateControl"
)
@ActionRegistration(
	displayName = "#CTL_CreateControl"
)
@Messages("CTL_CreateControl=Create Control")
public final class CreateControl implements ActionListener
{

	private final DataObject context;

	public CreateControl(DataObject context)
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
					File file = Utils.createControl(type, control, dao);
					Utils.openInEditor(FileUtil.toFileObject(file));
				}
			}
		} catch (ClassNotFoundException | FileNotFoundException | RuntimeException ex)
		{
			NotifyDescriptor.Message message = new NotifyDescriptor.Message(ex.getMessage());
			DialogDisplayer.getDefault().notify(message);
		}

	}
}
