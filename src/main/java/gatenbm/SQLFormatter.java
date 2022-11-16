package gatenbm;

import com.github.vertical_blank.sqlformatter.SqlFormatter;
import com.github.vertical_blank.sqlformatter.core.FormatConfig;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.netbeans.api.lexer.Language;
import org.netbeans.modules.editor.indent.spi.Context;
import org.netbeans.modules.editor.indent.spi.ExtraLock;
import org.netbeans.modules.editor.indent.spi.ReformatTask;

public class SQLFormatter implements ReformatTask
{

	static Language sqlLanguage = Language.find("text/x-sql");

	private final Context context;

	public SQLFormatter(Context context)
	{
		this.context = context;
	}

	@Override
	public ExtraLock reformatLock()
	{
		return null;
	}

	@Override
	public void reformat() throws BadLocationException
	{
		Document document = context.document();
		String text = document.getText(0, document.getLength() - 1);
		String formatted = SqlFormatter.format(text, FormatConfig.builder()
			.indent("\t")
			.uppercase(true)
			.build())
			.replaceAll(" \\$ ", "\\$")
			.replaceAll("\\=\\?", "= ?");
		document.remove(0, document.getLength() - 1);
		document.insertString(0, formatted, null);
	}
}
