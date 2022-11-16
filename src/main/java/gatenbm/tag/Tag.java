package gatenbm.tag;

import java.util.List;

public class Tag
{

	static List<Tag> VALUES
		= List.of(new Tag("g:condition", "<g:condition if=''></g:condition>", -16),
			new Tag("g:print", "<g:print property=''/>", -3),
			new Tag("g:if", "<g:if condition=''></g:if>", -9),
			new Tag("g:iterator", "<g:iterator source=''></g:iterator>", -15),
			new Tag("g:link", "<g:link module='' screen='' action=''/>", -23),
			new Tag("g:secure", "<g:secure module='' screen='' action=''></g:secure>", -33),
			new Tag("g:template", "<g:template filename=''></g:template>", -15),
			new Tag("g:write", "<g:write property=''/>", -3));

	private final String name;
	private final String value;
	private final String filter;
	private final int offset;

	public Tag(String name, String value, int offset)
	{
		this.name = name;
		this.value = value;
		this.offset = offset;
		this.filter = "<" + name;
	}

	public String name()
	{
		return name;
	}

	public String value()
	{
		return value;
	}

	public int offset()
	{
		return offset;
	}

	public String filter()
	{
		return filter;
	}

}
