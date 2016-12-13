package mariojayakumar.com.chemicalequationbalancer;

import java.util.ArrayList;
import java.util.Arrays;

public class CoefficientFinder {
	double[][] data;
	ArrayList<String> compound = new ArrayList<String>();
	int equalPoint=0;
	public CoefficientFinder(String s)
	{
		//s = "NaOH + H2SO4 = Na2SO4 + H2O";
		
		
		String [] c = s.split(" ");
		for(int i=0; i<c.length; i++)
		{
			if(!c[i].equals("+"))
			{
				compound.add(c[i]);
			}
			
		}
		ArrayList<Element> elements = new ArrayList<Element>();
		
		//THE FOLLOWING IS OUTDATED CODE
		/*
		int count=0;//refers to which compound is being processed
		while(count<compound.size())
		{
			char[] t = compound.get(count).toCharArray();
			ArrayList<Character> ts = new ArrayList<Character>();
			for(int j=0; j<t.length; j++)
			{
				ts.add(t[j]);//converts char array to array list
			}
			Element e;
			Compound cObj = new Compound();
			
			while(ts.size()>1)
			{
				if(ts.get(1)>96)
				{
					String name =  "" + ts.get(0).charValue()+ts.get(1).charValue();
					int sub=1;
					//add Na type
					if(ts.size()>2)
					if(ts.get(2)<58)
					{
						if(ts.size()>3)
						if(ts.get(3)<58)
						{
							sub = Integer.parseInt(""+ ts.get(2).charValue() + ts.get(3).charValue());
							ts.remove(3);
							ts.remove(2);
						}
						else
						{sub = Integer.parseInt(""+ ts.get(2).charValue());
						ts.remove(2);}
					}
					e = new Element(name,sub,count);
					ts.remove(1);
					ts.remove(0);
				}
				else
				{
					String name = "" + ts.get(0).charValue();
					int sub=1;
					// add H type
					if(ts.get(1)<58)
					{
						if(ts.size()>2)
						{
							if(ts.get(2)<58)
							{
								sub = Integer.parseInt("" + ts.get(1).charValue() +  ts.get(2).charValue());
								ts.remove(2);
							}
							else
							{
								sub = Integer.parseInt("" + ts.get(1).charValue());
							}
							ts.remove(1);
						}
						else
						{
							sub = Integer.parseInt("" + ts.get(1).charValue());
							ts.remove(1);
						}
						
					}
					e = new Element(name,sub,count);
					ts.remove(0);
				}
				
				boolean needsComp = false;// checks if needs compression
				for(int q=0; q<cObj.getList().size(); q++)
				{
					if(cObj.getList().get(q).getName().equals(e.getName()))
					{
						needsComp = true;
					}
				}
				if(!needsComp)
				{
					elements.add(e);
					cObj.addElement(e);
				}
				else
				{
					
					ArrayList<Element> cel = cObj.getList();//compresses elements
					for(int q=0; q<cel.size(); q++)
					{
						Element it = cel.get(q);
						if(it.getName().equals(e.getName()))
						{
							int compInd = it.getCompoundIndex();
							int sub = it.getSub();
							it = new Element(e.getName(), sub + e.getSub(), compInd);
						}
						cel.set(q,it);
						
					}
					for(int q=0; q<elements.size(); q++)
					{
						Element it = elements.get(q);
						if(it.getName().equals(e.getName()))
						{
							elements.get(q).setSub(elements.get(q).getSub() + e.getSub());
						}
					}

					cObj.setContain(cel);
				}
			}
			//last char case
			if(ts.size()>0 )
			{
				if(ts.get(0).charValue()!='=')
				{
					boolean needsComp = false;
					for(int q=0; q<cObj.getList().size(); q++)
					{
						if(cObj.getList().get(q).getName().equals("" + ts.get(0)))
						{
							needsComp = true;
						}
					}
					if(!needsComp)
					elements.add(new Element("" + ts.get(0).charValue(),1,count));
					else
					{
						for(int q=0; q<elements.size(); q++)
						{
							Element it = elements.get(q);
							if(it.getName().equals("" + ts.get(0)))
							{
								elements.get(q).setSub(elements.get(q).getSub() + 1);
							}
						}						
					}
				}
				else
				{
					equalPoint = count;
					compound.remove(compound.indexOf("="));
					count--;
				}
			}
			count++;
		}*/

		//updated version of finding coefficients
		int count = 0;
		while(count<compound.size())
		{
			char[] wordArray = compound.get(count).toCharArray();
			ArrayList<Character> ts = new ArrayList<Character>();

			for(char i:wordArray)// creating char arraylist of the compound
			{
				ts.add(i);
			}

			if(ts.get(0)== '=')//if the compound processed is the equal sign
			{
				equalPoint = count;
				compound.remove(compound.indexOf("="));
				count--;
			}
			else
			{
				//keep adding lower letters to the element
				// end when there is no longer a lower letter
				//keep adding numbers and add them to the element
				// end when there is no longer a number
				//check if the element already exists in the compound

				Compound cObj = new Compound();
				int nC = 0;
				while(nC<ts.size()) {

					Element e;
					String name = "" + ts.get(nC);
					nC++;
					     //counter through which part of the array list we are on
					while (nC < ts.size()) {//generate name string
						if (ts.get(nC) > 90) {
							//add lower letter to the name
							name += ts.get(nC);
							nC++;
						} else {
							//break the loop
							break;
						}
					}
					String numberString = "";
					while (nC < ts.size()) {//generate number string
						if (ts.get(nC) < 58) {
							numberString += ts.get(nC);
							nC++;
						} else
							break;
					}
					int subNum;
					if (numberString.equals("")) {//converts the number string to a number
						subNum = 1;
					} else {
						subNum = Integer.parseInt(numberString);
					}
					e = new Element(name, subNum, count);

					ArrayList<Element> addedE = cObj.getList();//checking to see if element is already in compound
					boolean matched = false;
					for(int i=0; i<addedE.size(); i++)
					{
						if(addedE.get(i).getName().equals(name))
						{
							addedE.get(i).addSub(subNum);//adding the subs together;
							elements.add(addedE.get(i));
							matched = true;
						}
					}
					if(!matched)//if element is not repeated, add the new element to the compound
					{
						cObj.addElement(e);
						elements.add(e);
					}

				}

			}

			count++;
		}

		ArrayList<String> existE = new ArrayList<String>();
		for(int i=0; i<elements.size(); i++)
		{
			if(!existE.contains(elements.get(i).getName()))
			{
				existE.add(elements.get(i).getName());
			}
		}
		int yComp = compound.size();//this is a fix for when there are more elements than compounds
		if(existE.size()>compound.size())
		{
			yComp = existE.size();
		}


		data=new double[yComp][compound.size()]; //compound.size() works when existE<compound.size()
		for(int i=0; i<yComp; i++)//
		{
			for(int j=0; j<compound.size(); j++)
			{
				data[i][j] = 0;
			}
		}

		//add to data array
		for(int y=0; y<existE.size(); y++){
			for(int x=0; x<compound.size(); x++)
			{
				for(int i=0; i<elements.size(); i++)
				{
					if(elements.get(i).getCompoundIndex()==x && elements.get(i).getName().equals(existE.get(y)))
					{

						data[y][x] = elements.get(i).getSub();
						if(x>equalPoint-1)
						{
							data[y][x]*=-1;
						}
					}
				}

			}
		}
		
	
		
	}
	
	public double[][] getData()
	{
		return data;
	}
	
	public ArrayList<String> getCompounds()
	{
		return compound;
	}
	
	public int getEqualPoint()
	{
		return equalPoint;
	}

}
