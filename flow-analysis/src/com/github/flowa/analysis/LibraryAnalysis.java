package com.github.flowa.analysis;

import java.util.ArrayList;
import java.util.List;

public class LibraryAnalysis
{
	private final String name;
	private final String previousVersion;
	private final String currentVersion;
	
	private final List<ClassAnalysis> unmodified = new ArrayList<>();
	private final List<ClassAnalysis> added = new ArrayList<>();
	private final List<ClassAnalysis> removed = new ArrayList<>();
	private final List<ClassAnalysis> modified = new ArrayList<>();
	
	public LibraryAnalysis(String name, String previous, String current)
	{
		super();
		this.name = name;
		this.previousVersion = previous;
		this.currentVersion = current;
	}
	
	public List<ClassAnalysis> getUnmodified()
	{
		return unmodified;
	}

	public List<ClassAnalysis> getAdded()
	{
		return added;
	}

	public List<ClassAnalysis> getRemoved()
	{
		return removed;
	}
	
	public List<ClassAnalysis> getModified()
	{
		return modified;
	}
	
	public boolean hasClass(final String className)
	{
		for (ClassAnalysis curAnalysis : getModified())
		{
			if (curAnalysis.getName().equals(className))
			{
				return true;
			}
		}
		
		for (ClassAnalysis curAnalysis : getUnmodified())
		{
			if (curAnalysis.getName().equals(className))
			{
				return true;
			}
		}
		
		return false;
	}

	@Override
	public String toString()
	{
		return "LibraryAnalysis [name=" + name + ", previousVersion=" + previousVersion + ", currentVersion=" + currentVersion
				+ ", \nunmodified=" + unmodified + ", \nnadded=" + added + "\n, removed=" + removed + ", \nmodified=" + modified + "]";
	}
	
}
