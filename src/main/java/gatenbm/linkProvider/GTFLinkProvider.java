package gatenbm.linkProvider;

import javax.swing.text.Document;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.lib.editor.hyperlink.spi.HyperlinkProviderExt;
import org.netbeans.lib.editor.hyperlink.spi.HyperlinkType;

@MimeRegistration(mimeType = "text/x-java", service = HyperlinkProviderExt.class)
public class GTFLinkProvider extends ResourceLinkProvider
{

	@Override
	public String getExtension()
	{
		return "gtf";
	}

	@Override
	public String getTooltipText(Document dcmnt, int i, HyperlinkType ht)
	{
		return "Open on text editor";
	}
}
