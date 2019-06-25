package gatenbm.linkProvider;

import javax.swing.text.Document;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.lib.editor.hyperlink.spi.HyperlinkProviderExt;
import org.netbeans.lib.editor.hyperlink.spi.HyperlinkType;

@MimeRegistration(mimeType = "text/x-java", service = HyperlinkProviderExt.class)
public class XMLLinkProvider extends ResourceLinkProvider
{

	@Override
	public String getExtension()
	{
		return "xml";
	}

	@Override
	public String getTooltipText(Document dcmnt, int i, HyperlinkType ht)
	{
		return "Open on text editor";
	}

}
