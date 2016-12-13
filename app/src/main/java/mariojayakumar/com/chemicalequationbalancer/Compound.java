package mariojayakumar.com.chemicalequationbalancer;

import java.util.ArrayList;

public class Compound {
	ArrayList<Element> contain = new ArrayList<Element>();
	
	public Compound()
	{
		
	}
	
	public void addElement(Element e)
	{
		contain.add(e);
	}
	
	public boolean containsE(Element e)
	{
		return contain.contains(e);
	}
	
	public void setContain(ArrayList<Element> e)
	{
		contain = e;
	}
	
	public ArrayList<Element> getList()
	{
		return contain;
	}
	
	public void removeAll()
	{
		contain.clear();
	}

}
