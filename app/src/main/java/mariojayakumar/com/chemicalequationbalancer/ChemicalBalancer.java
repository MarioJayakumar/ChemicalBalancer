package mariojayakumar.com.chemicalequationbalancer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;




public class ChemicalBalancer{
	String result;
	File file;
	
	public ChemicalBalancer(String input, File gf)
	{
		setFile(gf);
		result = "";
		ArrayList<String> original = new ArrayList<String>();
		String [] ppap = input.split(" ");
		for(String ppop:ppap)
		{
			if(!ppop.equals("+") && !ppop.equals("="))
				original.add(ppop);
		}

		EquationCompressor compressor = new EquationCompressor(input);
		input = compressor.getCompressed();

		//creating a file for logs
		System.out.println("Its here");
		//file = new File("Assets/OutputFile.txt");

		
		CoefficientFinder f = new CoefficientFinder(input);
		double[][] data = f.getData();
		for(int i=0; i<data.length; i++)
		{
			for(int j=0; j<data[0].length; j++)
			{
				System.out.print(data[i][j] + " ");
			}
			System.out.println();
		
		}
		System.out.println("Solving: ");
		rref(data);
		for(int i=0; i<data.length; i++)
		{
			for(int j=0; j<data[0].length; j++)
			{
				System.out.print(data[i][j] + " ");
			}
			System.out.println();
		
		}		
		double[] coefficients = new double[data[0].length];
		for(int i=0; i<coefficients.length; i++)
		{
			coefficients[i]=0;//initialize array
		}
		boolean isZero = true;
		for(int i=0; i<data[0].length; i++)
		{
			if(data[data.length-1][i] != 0 )
				isZero = false;
		}
		if(isZero)
		{
			coefficients[coefficients.length-1] = 1;
		}
		else
		{
			coefficients[coefficients.length-1] = data[data.length-1][data[data.length-1].length-1];
		}
		
		for(int i=data.length-2; i>-1; i--)
		{
			int j=data[0].length-1;
			while(j>i)
			{
				if(data[i][i]!=0)
				{
					coefficients[i] += -1*data[i][j]*coefficients[j]/data[i][i];
				}
				else
					System.out.println("COME LOOK AT ME!!");
				j--;
			}
		}
		
		//convert doubles to ints
		boolean done = false;
		int factor = 2;
		while(!done)
		{
			boolean hasDouble = false;
			for(int i =0; i<coefficients.length; i++)
			{
				if(coefficients[i] % 1 !=0)
				{
					hasDouble = true;
				}
			}
			if(hasDouble && factor<10)
			{
				double[] temp = new double[coefficients.length];
				for(int i=0; i<temp.length; i++)
				{
					temp[i] = coefficients[i];
				}
				for(int i=0; i<temp.length; i++)
				{
					temp[i] *= factor;
				}
				boolean hasdouble2 = false;
				for(int i=0; i<temp.length; i++)
				{
					if(temp[i] % 1 !=0)
					{
						hasdouble2 = true;
					}
				}
				if(hasdouble2)
				{
					factor++;
					done = false;
					
				}
				else
				{
					for(int i=0; i<temp.length; i++)
					{
						coefficients[i] = temp[i];
					}
					done = true;
				}
			}
			else
			{
				done = true;
				if(factor>=10)
					System.out.println("Coefficients cannot be simplified further");
			}
				
		}

		//output cleaned up version to output log
		if(factor <=10)
		{
			try {
				FileWriter fileWriter = new FileWriter(file, true);
				BufferedWriter out = new BufferedWriter(fileWriter);
				out.write("Coefficients are: ");
				for(int i=0; i<coefficients.length; i++) {
					out.write(coefficients[i] + "  ");
				}
				out.newLine();
				out.close();
			}catch(IOException io)
			{
				System.err.println(io);
			}
		}
		
		result = "";
		ArrayList<String> compounds = original;
		int equalPoint = f.getEqualPoint()-1;
		for(int i=0; i<compounds.size(); i++)
		{
			if(coefficients[i] != 1.00 )
			result+= "" + coefficients[i] + "" + compounds.get(i) + " ";
			else
				result+= "" + compounds.get(i) + " ";
			if(i!= equalPoint  && i!=compounds.size()-1)
					result+="+ ";
			if(i==equalPoint)
					result+="= ";
		}
		System.out.println(result);
		try
		{
			FileWriter fileWriter = new FileWriter(file,true);
			BufferedWriter out = new BufferedWriter(fileWriter);
			out.write(result);
			out.newLine();
			out.close();
		}catch(IOException io){
			System.err.println(io);
		}



	}

	public String getResult()
	{
		return result;
	}

	public void setFile(File f)
	{
		file = f;
	}
	

	
	public void rref(double [][] m)
	{
		try {
			BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			output.write("Initial Matrix");
			output.newLine();
			int lead = 0;
			int rowCount = m.length;
			int colCount = m[0].length;
			int i;
			boolean quit = false;

			for (int row = 0; row < rowCount && !quit; row++) {
				print(m,output);
				System.out.println();
				output.newLine();

				if (colCount <= lead) {
					quit = true;
					break;
				}

				i = row;

				while (!quit && m[i][lead] == 0) {
					i++;
					if (rowCount == i) {
						i = row;
						lead++;

						if (colCount == lead) {
							quit = true;
							break;
						}
					}
				}

				if (!quit) {
					if (i != row) {
						System.out.println("Swapping rows, " + i + " and " + row);
						output.write("Swapping rows, " + i + " and " + row);
						output.newLine();
						swapRows(m, i, row);
					}


					if (m[row][lead] != 0 && m[row][lead] != 1) {
						System.out.println("Multiplying row " + row + " by " + 1.0f / m[row][lead]);
						output.write("Multiplying row " + row + " by " + 1.0f / m[row][lead]);
						output.newLine();
						multiplyRow(m, row, 1.0f / m[row][lead]);
					}

					for (i = 0; i < rowCount; i++) {
						if (i != row) {
							if (m[i][lead] != 0) {
								System.out.println("Subtracting " + m[i][lead] + " times row " + row + " from row " + i);
								output.write("Subtracting " + m[i][lead] + " times row " + row + " from row " + i);
								output.newLine();
								subtractRows(m, m[i][lead], row, i);
							}

						}
					}
				}
			}
			output.write("Final Result");
			output.newLine();
			print(m,output);
			output.newLine();

			output.close();
		}
		catch(IOException io)
		{
			System.err.print(io);
		}
	}

	// swaps two rows
	static void swapRows(double [][] m, int row1, int row2)
	{
	    double [] swap = new double[m[0].length];

	    for(int c1 = 0; c1 < m[0].length; c1++)
	        swap[c1] = m[row1][c1];

	    for(int c1 = 0; c1 < m[0].length; c1++)
	    {
	        m[row1][c1] = m[row2][c1];
	        m[row2][c1] = swap[c1];
	    }
	}

	static void multiplyRow(double [][] m, int row, double scalar)
	{
	    for(int c1 = 0; c1 < m[0].length; c1++)
	        m[row][c1] *= scalar;
	}

	static void subtractRows(double [][] m, double scalar, int subtract_scalar_times_this_row, int from_this_row)
	{
	    for(int c1 = 0; c1 < m[0].length; c1++)
	        m[from_this_row][c1] -= scalar * m[subtract_scalar_times_this_row][c1];
	}

	static public void print(double [][] matrix,BufferedWriter f)
	{
		try {
			for (int c1 = 0; c1 < matrix.length; c1++) {
				System.out.print("[ ");
				f.write("[ ");

				for (int c2 = 0; c2 < matrix[0].length - 1; c2++) {
					System.out.print(matrix[c1][c2] + ", ");
					f.write(matrix[c1][c2] + ", ");
				}

				System.out.println(matrix[c1][matrix[c1].length - 1] + " ]");
				f.write(matrix[c1][matrix[c1].length - 1] + " ]");
				f.newLine();
			}
		}
		catch(IOException io)
		{
			System.err.println(io);
		}
	}








}
