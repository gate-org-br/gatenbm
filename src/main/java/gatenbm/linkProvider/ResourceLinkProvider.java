package gatenbm.linkProvider;

import gatenbm.Utils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.text.Document;
import org.netbeans.modules.editor.NbEditorUtilities;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

public abstract class ResourceLinkProvider extends LinkProvider
{
	@Override
	public void openResource(Document doc, String resourceName) throws FileNotFoundException, IOException
	{
		FileObject self = NbEditorUtilities.getFileObject(doc);

		String name = self.getParent().getPath()
			.replace("src/main/java", "src/main/resources")
			+ File.separatorChar + resourceName;

		File resource = new File(name);
		if (!resource.exists())
		{
			NotifyDescriptor.Confirmation confirmation
				= new NotifyDescriptor.Confirmation(String.format("No resource named %s found on %s",
					resourceName, resource.getPath()), "Create resource?", NotifyDescriptor.YES_NO_OPTION);

			if (DialogDisplayer.getDefault().notify(confirmation) == NotifyDescriptor.NO_OPTION)
				return;

			resource.getParentFile().mkdirs();
			resource.createNewFile();
		}

		Utils.openInEditor(FileUtil.toFileObject(resource));
	}
}
