package gatenbm.linkProvider;

public class Link
{

	public final String text;
	public final int min;
	public final int max;

	public Link(String text, int min, int max)
	{
		this.text = text;
		this.min = min;
		this.max = max;
	}

	public String getText()
	{
		return text;
	}

	public int getMin()
	{
		return min;
	}

	public int getMax()
	{
		return max;
	}
}
