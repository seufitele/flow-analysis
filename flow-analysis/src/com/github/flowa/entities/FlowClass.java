package com.github.flowa.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Flow Analysis class. <br/>
 *
 */
public class FlowClass
{
	private final String name;
	private final List<FlowMethod> methods = new ArrayList<>();

	public FlowClass(final String name)
	{
		super();
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public List<FlowMethod> getMethods()
	{
		return methods;
	}

	@Override
	public String toString()
	{
		final StringBuilder sBuilder = new StringBuilder();

		sBuilder.append("FlowClass [\n");

		sBuilder.append("name= ");
		sBuilder.append(getName());
		sBuilder.append("\n");

		sBuilder.append("methods");
		sBuilder.append("\n");

		for (final FlowMethod method : getMethods())
		{
			sBuilder.append("\t");
			sBuilder.append(method);
			sBuilder.append("\n");
		}

		sBuilder.append("]");

		return sBuilder.toString();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (name == null ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final FlowClass other = (FlowClass) obj;
		if (name == null)
		{
			if (other.name != null)
			{
				return false;
			}
		}
		else if (!name.equals(other.name))
		{
			return false;
		}
		return true;
	}

}
