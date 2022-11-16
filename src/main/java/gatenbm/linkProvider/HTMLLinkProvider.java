package gatenbm.linkProvider;

import gatenbm.Utils;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.text.Document;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.lib.editor.hyperlink.spi.HyperlinkProviderExt;
import org.netbeans.lib.editor.hyperlink.spi.HyperlinkType;
import org.netbeans.modules.editor.NbEditorUtilities;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

@MimeRegistration(mimeType = "text/x-java", service = HyperlinkProviderExt.class)
public class HTMLLinkProvider extends LinkProvider
{

	@Override
	public String getTooltipText(Document dcmnt, int i, HyperlinkType ht)
	{
		return "Open on HTML editor";
	}

	@Override
	public String getExtension()
	{
		return "html";
	}

	@Override
	public void openResource(Document doc, String resourceName) throws FileNotFoundException, IOException
	{
		FileObject self = NbEditorUtilities.getFileObject(doc);

		FileObject resources = self.getParent();
		while (!resources.getName().equals("java"))
			resources = resources.getParent();
		resources = resources.getParent().getFileObject("resources");

		FileObject html = resources.getFileObject(resourceName);
		if (html == null)
		{
			NotifyDescriptor.Confirmation confirmation
				= new NotifyDescriptor.Confirmation(String.format("No HTML named %s found on %s",
					resourceName, resources.getPath()), "Create HTML file?", NotifyDescriptor.YES_NO_OPTION);

			if (DialogDisplayer.getDefault().notify(confirmation) == NotifyDescriptor.NO_OPTION)
				return;

			html = FileUtil.createData(resources, resourceName);
		}

		Utils.openInEditor(html);
	}
}
