package gatenbm.attribute;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import org.netbeans.api.editor.completion.Completion;
import org.netbeans.spi.editor.completion.CompletionItem;
import org.netbeans.spi.editor.completion.CompletionTask;
import org.netbeans.spi.editor.completion.support.CompletionUtilities;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;

public class AttributeCompletionItem implements CompletionItem
{

	private final String text;
	private final int dotOffset;
	private final int caretOffset;

	private static final Color COLOR = Color.decode("0x0000B2");
	private static final ImageIcon fieldIcon = new ImageIcon(ImageUtilities.loadImage("gatenbm/icon.png"));

	public AttributeCompletionItem(String text,
		int dotOffset,
		int caretOffset
	)
	{
		this.text = text;
		this.dotOffset = dotOffset;
		this.caretOffset = caretOffset;
	}

	@Override
	public void defaultAction(JTextComponent component)
	{
		try
		{
			StyledDocument doc = (StyledDocument) component.getDocument();
			doc.remove(dotOffset, caretOffset - dotOffset);
			String complete = text + "=''";
			doc.insertString(dotOffset, complete, null);
			component.setCaretPosition(component.getCaretPosition() - 1);
			Completion.get().hideAll();
		} catch (BadLocationException ex)
		{
			Exceptions.printStackTrace(ex);
		}
	}

	@Override
	public void processKeyEvent(KeyEvent ke)
	{

	}

	@Override
	public int getPreferredWidth(Graphics graphics, Font font)
	{
		return CompletionUtilities.getPreferredWidth(text, null, graphics, font);
	}

	@Override
	public void render(Graphics graphics, Font font, Color color,
		Color color1, int width, int height, boolean selected)
	{
		CompletionUtilities.renderHtml(fieldIcon, text, null, graphics, font,
			(selected ? Color.white : COLOR), width, height, selected);
	}

	@Override
	public CompletionTask createDocumentationTask()
	{
		return null;
	}

	@Override
	public CompletionTask createToolTipTask()
	{
		return null;
	}

	@Override
	public boolean instantSubstitution(JTextComponent jtc)
	{
		return false;
	}

	@Override
	public int getSortPriority()
	{
		return 1;
	}

	@Override
	public CharSequence getSortText()
	{
		return text;
	}

	@Override
	public CharSequence getInsertPrefix()
	{
		return text;
	}

}
