package mariojayakumar.com.chemicalequationbalancer;

public class Element {
	String name;
	int sub;
	int compoundIndex;
	
	public Element()
	{
		
	}
	
	public Element(String n, int s,int c)
	{
		name = n;
		sub = s;
		compoundIndex = c;
	}	
	
	public String getName()
	{
		return name;
	}
	
	public int getSub()
	{
		return sub;
	}
	
	public void setSub(int in)
	{
		sub=in;
	}

	public void addSub(int in) {sub+=in;}
	
	public int getCompoundIndex()
	{
		return compoundIndex;
	}

}
