package road;

/**
 * @author wilge
 */

public class Localization
{
    public final int position;
    public final String id;
    
    public Localization(int position, String id)
	{
    	this.position = position;
    	this.id = id;
	}
    
    public String getId(int position)
    {
    	return id;
    }

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + position;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Localization other = (Localization) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		} 
		if (position != other.position)
			return false;
		return true;
	}

}
