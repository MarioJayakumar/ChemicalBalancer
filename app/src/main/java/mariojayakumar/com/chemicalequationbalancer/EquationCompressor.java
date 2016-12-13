package mariojayakumar.com.chemicalequationbalancer;

import java.util.ArrayList;

public class EquationCompressor {

	String input,compressed;
	
	public EquationCompressor (String n)
	{
		input = n;
		compressed= "";
		//distribute subs to paranthesis
		//combine repeating elements
		String [] c = input.split(" ");
		ArrayList<String> compound = new ArrayList<String>();
		int equalPoint =0 ;
		for(int i=0; i<c.length; i++)
		{
			if(!c[i].equals("+"))
			{
				compound.add(c[i]);
			}
		}
		for(int i=0; i<compound.size(); i++)
		{
			if(compound.get(i).equals("="))
			{
				compound.remove(i);
				equalPoint = i;
			}
		}
		int count = 0;
		
		while(count<compound.size())
		{
			char[] t= compound.get(count).toCharArray();
			ArrayList<Character> ts = new ArrayList<Character>();
			for(char chara:t)
			{
				ts.add(chara);
			}
			int pos=-1;
			for(int i=0; i<ts.size(); i++)
			{
				if(ts.get(i) == '(')
				{
					pos = i;
				}
			}
			if(pos>-1)
			{
				
				compound.set(count,distribute(compound.get(count),pos));
			}
			count++;
		}
		equalPoint--;
		for(int i=0; i<compound.size(); i++)
		{
			compressed+= compound.get(i) + " ";
			if(i!=equalPoint && i!=compound.size()-1)
			{
				compressed+= "+ ";
			}
			if(i==equalPoint)
			{
				compressed+= "= ";
			}
		}
		
		
	}
	
	public String getCompressed()
	{
		return compressed;
	}
	
	public String distribute(String s, int pos)
	{
		String res = "";
		String pref = s.substring(0,pos);//creating substring from beginning to parenthesis
		String suffix; //substring from last parenthesis to end
		
		char[] t = s.toCharArray();
		ArrayList<Character> ts = new ArrayList<Character>();
		for(char chara:t)
		{
			ts.add(chara);
		}
		int end = ts.indexOf(')');
		ArrayList<Character>  sub = new ArrayList<Character>();
		for(int i=pos+1; i<ts.size(); i++)//only have inside para
		{
			sub.add(ts.get(i));
		}
		for(int i = ts.size()-1; i>end; i--)
		{
			if(ts.get(i)>57)
			{
				ts.remove(i);
			}
		}
		int numCount=0;
		for(int i=end+1; i<ts.size(); i++)
		{
			numCount ++;
		}
		System.out.println(sub);

		int doubledig = numCount;
		if(end == sub.size()-1)
		{
			doubledig = 1;
		}
		
		int factor =0;
		if(doubledig>1)
		{
			factor = Integer.parseInt("" + sub.get(sub.size()-2) + sub.get(sub.size()-1));
			sub.remove(sub.size()-1);
			sub.remove(sub.size()-1);
			sub.remove(sub.size()-1);//remove both digits  and para
		}
		else
		{
			factor = Integer.parseInt("" + sub.get(sub.size()-1));
			sub.remove(sub.size()-1);//remove digit and para
			sub.remove(sub.size()-1);
		}
		//sub is supposed to be compound inside paranthesis
		
		//remove first para
		int count=0;
		while(count<sub.size())
		{
			if(count<sub.size()-2)//more than two char left
			{
				//Na case
				if(sub.get(count+1)>96 )
				{
					//factor case
					if(sub.get(count+2)<65)//Na2
					{
						int num = Integer.parseInt("" + sub.get(count+2));
						num*=factor;
						String mememe = num + "";
						sub.remove(count+2);
						for(int i=mememe.length(); i>0; i--)
						{
							sub.add(count+2, mememe.charAt(i-1));
						}
						count+=mememe.length();
						count++;
						count++;
					}
					else//Na
					{
						String mememe = factor + "";
						for(int i=mememe.length(); i>0; i--)
						{
							sub.add(count+2, mememe.charAt(i-1));
						}
						count+=mememe.length();
						count++;
						count++;
					}
				}
				else
				if(sub.get(count+1)<58)
				{
					int num = Integer.parseInt("" + sub.get(count+1));
					num*=factor;
					String mem = num + "";
					sub.remove(count+1);
					for(int i=mem.length(); i>0; i--)
					{
						sub.add(count+1, mem.charAt(i-1));
					}
					count+=mem.length();
					count++;
				}
				else
				{
					String mememe = factor + "";
					for(int i=mememe.length(); i>0; i--)
					{
						sub.add(count+1, mememe.charAt(i-1));
					}
					count+=mememe.length();
					count++;
				}
			}
			else
			if(count<sub.size()-1)//only two char left
			{
				if(sub.get(count+1)>96)
				{
					String mem = factor + "";
					for(int i=mem.length(); i>0; i--)
					sub.add((factor+"").charAt(i-1));
					count+=mem.length();
					count++;
				}
				else
				if(sub.get(count+1)<58)
				{
					int num = Integer.parseInt(sub.get(count+1) + "");
					num*=factor;
					sub.remove(count+1);
					String mem = num+"";
					for(int i=mem.length(); i>0; i--)
					{

						sub.add(count+1,mem.charAt(i-1));
					}
					count++;
					count+=mem.length();

				}
				else
				{
					String mem = factor+"";
					for(int i=mem.length(); i>0; i--)
					sub.add(count+1,(factor+"").charAt(i-1));
					count+=mem.length();
					count++;
				}
			}
			else if(sub.get(count) >58)//one char left
			{
				String mem = factor + "";
				for(int i=mem.length(); i>0; i--)
				sub.add((factor+"").charAt(i-1));
				count+=mem.length();
				count++;
			}
			else
			{
				count++;
			}
		
		}
		
		for(int i=0; i<sub.size(); i++)
		{
			res+=sub.get(i);
		}
		
		return (pref+res);
	}
	
	public String compress(String s)
	{
		String res = "";
		char[] t = s.toCharArray();
		ArrayList<Character> sub = new ArrayList<Character>();
		for(int i=0; i<t.length; i++)
		{
			sub.add(t[i]);
		}
		
		int count = 0;
		String temp = s;
		while(count<sub.size())
		{
			
		}
		
		return res;
	}
	
}
