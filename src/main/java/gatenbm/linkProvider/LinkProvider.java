package gatenbm.linkProvider;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.text.Document;
import org.netbeans.api.java.lexer.JavaTokenId;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.lib.editor.hyperlink.spi.HyperlinkProviderExt;
import org.netbeans.lib.editor.hyperlink.spi.HyperlinkType;
import org.openide.util.Exceptions;

public abstract class LinkProvider implements HyperlinkProviderExt
{

	@Override
	public Set<HyperlinkType> getSupportedHyperlinkTypes()
	{
		return Stream.of(HyperlinkType.values())
			.collect(Collectors.toSet());
	}

	@Override
	public boolean isHyperlinkPoint(Document doc, int i, HyperlinkType ht)
	{
		return getLink(doc, i).isPresent();
	}

	@Override
	public int[] getHyperlinkSpan(Document doc, int i, HyperlinkType ht)
	{
		return getLink(doc, i).map(e -> new int[]
		{
			e.getMin(), e.getMax()
		}).get();
	}

	@Override
	public void performClickAction(Document doc, int i, HyperlinkType ht)
	{
		try
		{
			Optional<Link> link = getLink(doc, i);
			if (link.isPresent())
				openResource(doc, link.get().getText());
		} catch (FileNotFoundException ex)
		{
			Exceptions.printStackTrace(ex);
		} catch (IOException ex)
		{
			Exceptions.printStackTrace(ex);
		}
	}

	Optional<Link> getLink(Document doc, int position)
	{
		TokenHierarchy<?> tokenHierarchy = TokenHierarchy.get(doc);
		TokenSequence<JavaTokenId> tokenSequence = tokenHierarchy
			.tokenSequence(JavaTokenId.language());
		if (tokenSequence == null)
			return Optional.empty();

		tokenSequence.move(position);
		if (!tokenSequence.moveNext())
			return Optional.empty();

		Token<JavaTokenId> token = tokenSequence.token();
		if (token.id() != JavaTokenId.STRING_LITERAL)
			return Optional.empty();

		String string = token.text().toString();
		if (!string.endsWith("." + getExtension() + "\""))
			return Optional.empty();

		return Optional.of(new Link(string.substring(1, string.length() - 1),
			tokenSequence.offset(), tokenSequence.offset() + string.length()));
	}

	@Override
	public abstract String getTooltipText(Document dcmnt, int i, HyperlinkType ht);

	public abstract void openResource(Document doc, String resourceName) throws FileNotFoundException, IOException;

	public abstract String getExtension();
}
