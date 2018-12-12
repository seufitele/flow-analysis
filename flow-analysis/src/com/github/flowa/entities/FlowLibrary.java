package com.github.flowa.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a flow analysis library. <br/>
 * It contains information about what library was analyzed.
 * 
 */
public class FlowLibrary
{
	private String version;
	private String name;

	private final List<FlowClass> flowClasses = new ArrayList<>();

	public FlowLibrary(final String version, final String name)
	{
		super();
		this.version = version;
		this.name = name;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(final String version)
	{
		this.version = version;
	}

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public List<FlowClass> getFlowClasses()
	{
		return flowClasses;
	}

	@Override
	public String toString()
	{
		StringBuilder sBuilder = new StringBuilder();
		
		sBuilder.append("FlowLibrary [\n");
		
		sBuilder.append("name= ");
		sBuilder.append(getName());
		sBuilder.append("\n");
		
		sBuilder.append("version= ");
		sBuilder.append(getVersion());
		sBuilder.append("\n");
		
		sBuilder.append("classes");
		sBuilder.append("\n");
		
		for (FlowClass curClass : getFlowClasses())
		{
			sBuilder.append("\n");
			sBuilder.append(curClass);
			sBuilder.append("\n");
		}
		
		sBuilder.append("]");
		
		return sBuilder.toString();
	}
}
