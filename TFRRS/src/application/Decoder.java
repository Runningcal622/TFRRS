package application;
import Server.*;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Decoder
{

	private final String SERIALIZED_FILE_NAME;

	public Decoder()
	{
		SERIALIZED_FILE_NAME = "Tfrrs.xml";
	}

	public Decoder(String fileName)
	{
		SERIALIZED_FILE_NAME = fileName;
	}

	public Tfrrs deserialize()
	{
		XMLDecoder decoder = null;
		try
		{
			decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(SERIALIZED_FILE_NAME)));
		} catch (FileNotFoundException e)
		{
			System.out.println("ERROR: File " + SERIALIZED_FILE_NAME + " not found");
		}

		Tfrrs entity = (Tfrrs) decoder.readObject();
		decoder.close();
		return entity;
	}
	
	public LocalServer deserializeTeamData()
	{
		XMLDecoder decoder = null;
		try
		{
			decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(SERIALIZED_FILE_NAME)));
		} catch (FileNotFoundException e)
		{
			System.out.println("ERROR: File " + SERIALIZED_FILE_NAME + " not found");
		}

		LocalServer entity = (LocalServer) decoder.readObject();
		decoder.close();
		return entity;
	}

}