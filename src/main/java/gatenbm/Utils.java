package gatenbm;

import gate.code.ClassName;
import gate.code.ControlGenerator;
import gate.code.DMLGenerator;
import gate.code.DaoGenerator;
import gate.code.Project;
import gate.code.ScreenGenerator;
import gate.code.ViewGenerator;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.netbeans.api.java.classpath.ClassPath;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.cookies.EditCookie;
import org.openide.cookies.OpenCookie;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;

public class Utils
{

	public static void openInEditor(FileObject fileToOpen) throws FileNotFoundException
	{
		try
		{
			DataObject fileDO = DataObject.find(fileToOpen);
			if (fileDO == null)
				throw new FileNotFoundException();

			EditCookie editCookie = fileDO.getLookup().lookup(EditCookie.class);
			if (editCookie == null)
			{
				OpenCookie openCookie = fileDO.getLookup().lookup(OpenCookie.class);
				if (openCookie != null)
					openCookie.open();
			} else
				editCookie.edit();

		} catch (DataObjectNotFoundException e)
		{
			throw new FileNotFoundException();
		}
	}

	public static String getClassPath(DataObject context)
	{
		return ClassPath.getClassPath(context.getPrimaryFile(), ClassPath.EXECUTE).toString();
	}

	public static String getClassName(DataObject context)
	{
		StringBuilder className = new StringBuilder(context.getName());
		for (Path classpath = Paths.get(context.getPrimaryFile().getPath()).getParent();
			!classpath.getFileName().equals(Path.of("java"));
			classpath = classpath.getParent())
			className.insert(0, classpath.getFileName().toString() + ".");
		return className.toString();
	}

	public static ClassName getClassName(Class<?> type, ClassName standard, String title, String text)
	{
		NotifyDescriptor.InputLine prompt = new NotifyDescriptor.InputLine(text, title,
			NotifyDescriptor.OK_CANCEL_OPTION,
			NotifyDescriptor.QUESTION_MESSAGE);
		prompt.setInputText(standard.toString());

		DialogDisplayer.getDefault().notify(prompt);

		if (prompt.getValue() != NotifyDescriptor.OK_OPTION)
			return null;

		return ClassName.of(prompt.getInputText());
	}

	public static File createDao(Class<?> type, ClassName dao) throws FileNotFoundException
	{
		Utils.createSearchDML(type, dao);
		Utils.createSelectDML(type, dao);

		File file = Project.of(type).getSourceFile(dao).toFile();
		if (!file.exists())
			new DaoGenerator(type).dao(dao, file);
		return file;
	}

	public static File createControl(Class<?> type, ClassName control, ClassName dao) throws FileNotFoundException
	{
		Utils.createDao(type, dao);
		File file = Project.of(type).getSourceFile(control).toFile();
		if (!file.exists())
			new ControlGenerator(type).control(control, dao, file);
		return file;
	}

	public static File createScreen(Class<?> type, ClassName screen, ClassName control, ClassName dao) throws FileNotFoundException
	{
		Utils.createDao(type, dao);
		Utils.createControl(type, control, dao);
		Utils.createView(type, screen);
		Utils.createViewSearch(type, screen);
		Utils.createViewSelect(type, screen);
		Utils.createViewInsert(type, screen);
		Utils.createViewUpdate(type, screen);
		Utils.createViewResult(type, screen);

		File file = Project.of(type).getSourceFile(screen).toFile();
		if (!file.exists())
			new ScreenGenerator(type).screen(screen, control, file);
		return file;
	}

	public static File createView(Class<?> type, ClassName screen)
	{
		File file = Project.of(type)
			.getViewPath(screen.getPackageName(), type)
			.resolve("View.jsp").toFile();
		if (!file.exists())
			new ViewGenerator(type).view(file);
		return file;
	}

	public static File createViewSearch(Class<?> type, ClassName screen)
	{
		File file = Project.of(type)
			.getViewPath(screen.getPackageName(), type)
			.resolve("ViewSearch.jsp").toFile();
		if (!file.exists())
			new ViewGenerator(type).viewSearch(file);
		return file;
	}

	public static File createViewSelect(Class<?> type, ClassName screen)
	{
		File file = Project.of(type)
			.getViewPath(screen.getPackageName(), type)
			.resolve("ViewSelect.jsp").toFile();
		if (!file.exists())
			new ViewGenerator(type).viewSelect(file);
		return file;
	}

	public static File createViewInsert(Class<?> type, ClassName screen)
	{
		File file = Project.of(type)
			.getViewPath(screen.getPackageName(), type)
			.resolve("ViewInsert.jsp").toFile();
		if (!file.exists())
			new ViewGenerator(type).viewInsert(file);
		return file;
	}

	public static File createViewUpdate(Class<?> type, ClassName screen)
	{
		File file = Project.of(type)
			.getViewPath(screen.getPackageName(), type)
			.resolve("ViewUpdate.jsp").toFile();
		if (!file.exists())
			new ViewGenerator(type).viewUpdate(file);
		return file;
	}

	public static File createViewResult(Class<?> type, ClassName screen)
	{
		File file = Project.of(type)
			.getViewPath(screen.getPackageName(), type)
			.resolve("ViewResult.jsp").toFile();
		if (!file.exists())
			new ViewGenerator(type).viewResult(file);
		return file;
	}

	public static File createSearchDML(Class<?> type, ClassName dao)
	{
		File file = Project.of(type)
			.getResourcePath(dao)
			.resolve(String.format("search(%s).sql", type.getSimpleName())).toFile();
		if (!file.exists())
			new DMLGenerator(type).search(file);
		return file;
	}

	public static File createSelectDML(Class<?> type, ClassName dao)
	{
		File file = Project.of(type)
			.getResourcePath(dao)
			.resolve("select(ID).sql").toFile();
		if (!file.exists())
			new DMLGenerator(type).select(file);
		return file;
	}
}
