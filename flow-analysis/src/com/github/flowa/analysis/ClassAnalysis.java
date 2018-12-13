package com.github.flowa.analysis;

import java.util.ArrayList;
import java.util.List;

import com.github.flowa.entities.FlowMethod;

public class ClassAnalysis
{
	private final String name;
	private final List<FlowMethod> unmodified = new ArrayList<>();
	private final List<FlowMethod> added = new ArrayList<>();
	private final List<FlowMethod> removed = new ArrayList<>();
	
	public ClassAnalysis(String name)
	{
		super();
		this.name = name;
	}
	
	public List<FlowMethod> getUnmodified()
	{
		return unmodified;
	}

	public List<FlowMethod> getAdded()
	{
		return added;
	}

	public List<FlowMethod> getRemoved()
	{
		return removed;
	}
	
	public String getName()
	{
		return name;
	}

	@Override
	public String toString()
	{
		
		if (getAdded().isEmpty() && getRemoved().isEmpty())
		{
			return name;
		}
		
//		return "ClassAnalysis [name=" + name + ", unmodified=" + unmodified + ", added=" + added + ", removed=" + removed + "]";
		return "[" + name + ", added=" + added + ", removed=" + removed + "]";
	}
}
