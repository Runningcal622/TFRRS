package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import Server.*;

public class MyWriter
{
	String txt;
	// file to write to
	File file;
	FileWriter fw;
	BufferedWriter bw;

	public MyWriter(String txt) throws IOException
	{
		this.file = new File(txt);
		fw = new FileWriter(file.getAbsoluteFile());
		bw = new BufferedWriter(fw);
	};

	public void writeStats(Athlete ath) throws IOException
	{
		String gen;
		if (ath.getSex().equals("m"))
		{
			gen="male";
		}
		else
		{
			gen="female";
		}
		bw.write(ath.getName()+" at "+ath.getCollege()+" in "+ath.getYear()+" year");
		bw.newLine();
		bw.write(gen);
		bw.newLine();
		for (Result res : ath.getEvents())
		{
			if (res.getMark() == 0)
			{
				bw.write(res.getEvent() + " " + res.getInOrOut() + " at " + res.getMeet() + " " + res.getTime());

			} else
			{
				bw.write(res.getEvent() + " " + res.getInOrOut() + " at " + res.getMeet() + " " + res.getMark());
			}
			bw.newLine();
		}
		bw.newLine();
		bw.newLine();
	}

}
