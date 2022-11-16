package gatenbm;

import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.modules.editor.indent.spi.Context;
import org.netbeans.modules.editor.indent.spi.ReformatTask;

@MimeRegistration(mimeType = "text/x-sql", service = ReformatTask.Factory.class)
public class SQLFormatterFactory implements ReformatTask.Factory
{

	@Override
	public ReformatTask createTask(Context cntxt)
	{
		return new SQLFormatter(cntxt);
	}
}
