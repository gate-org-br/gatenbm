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
public class JSPLinkProvider extends LinkProvider
{

	@Override
	public String getTooltipText(Document dcmnt, int i, HyperlinkType ht)
	{
		return "Open on JSP editor";
	}

	@Override
	public String getExtension()
	{
		return "jsp";
	}

	@Override
	public void openResource(Document doc, String resourceName) throws FileNotFoundException, IOException
	{
		FileObject self = NbEditorUtilities.getFileObject(doc);

		FileObject webapp = self.getParent();
		while (!webapp.getName().equals("java"))
			webapp = webapp.getParent();
		webapp = webapp.getParent().getFileObject("webapp");

		FileObject jsp = webapp.getFileObject(resourceName);
		if (jsp == null)
		{
			NotifyDescriptor.Confirmation confirmation
				= new NotifyDescriptor.Confirmation(String.format("No JSP named %s found on %s",
					resourceName, webapp.getPath()), "Create JSP file?", NotifyDescriptor.YES_NO_OPTION);

			if (DialogDisplayer.getDefault().notify(confirmation) == NotifyDescriptor.NO_OPTION)
				return;

			jsp = FileUtil.createData(webapp, resourceName);
		}

		Utils.openInEditor(jsp);
	}
}
