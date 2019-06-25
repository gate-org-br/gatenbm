package gatenbm.create;

import gate.code.ClassName;
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
	id = "gateplugin.CreateDao"
)
@ActionRegistration(
	displayName = "#CTL_CreateDao"
)
@Messages("CTL_CreateDao=Create Dao")
public final class CreateDao implements ActionListener
{

	private final DataObject context;

	public CreateDao(DataObject context)
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
				File file = Utils.createDao(type, dao);
				Utils.openInEditor(FileUtil.toFileObject(file));
			}
		} catch (ClassNotFoundException | FileNotFoundException | RuntimeException ex)
		{
			NotifyDescriptor.Message message = new NotifyDescriptor.Message(ex.getMessage());
			DialogDisplayer.getDefault().notify(message);
		}
	}
}
