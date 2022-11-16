package gatenbm.attribute;

import java.util.List;

public class Attribute
{

	static List<Attribute> VALUES
		= List.of(new Attribute("g:action"),
			new Attribute("g:condition"),
			new Attribute("g:icon"),
			new Attribute("g:if"),
			new Attribute("g:insert"),
			new Attribute("g:iterate"),
			new Attribute("g:labels"),
			new Attribute("g:module"),
			new Attribute("g:options"),
			new Attribute("g:paginator"),
			new Attribute("g:property"),
			new Attribute("g:print"),
			new Attribute("g:screen"),
			new Attribute("g:stacktrace"),
			new Attribute("g:superuser"),
			new Attribute("g:template"),
			new Attribute("g:values"),
			new Attribute("g:with"),
			new Attribute("g:write"));

	private final String name;

	public Attribute(String name)
	{
		this.name = name;
	}

	public String name()
	{
		return name;
	}
}
