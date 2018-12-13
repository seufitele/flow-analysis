package com.github.flowa.entities;

import java.util.List;

import com.github.flowa.main.Util;

/**
 * This class represents a FlowMethod
 * 
 * @author vinicius
 *
 */
public class FlowMethod
{
	private final String name;
	private final List<String> parameterTypes;
	private final boolean isPublic;
	private final String returnType;

	// private final List<String> invariants = new ArrayList<>();

	public FlowMethod(final String name, final List<String> parameterTypes, final boolean isPublic, final String returnType)
	{
		super();
		this.name = name;
		this.parameterTypes = parameterTypes;
		this.isPublic = isPublic;
		this.returnType = returnType;
	}

	public String getName()
	{
		return name;
	}

	public String getReturnType()
	{
		return returnType;
	}

	// public List<String> getInvariants()
	// {
	// return invariants;
	// }
	//
	public boolean isPublic()
	{
		return isPublic;
	}

	public String[] getParameterTypes()
	{
		return parameterTypes.toArray(new String[0]);
	}

	@Override
	public String toString()
	{
//		return "[" + name + ":" + parameterTypes + "]";
//		return (isPublic ? "public " : "") + returnType + " " + name + "(" + Util.toString(parameterTypes) + ")";
		return returnType + " " + name + "(" + Util.toString(parameterTypes) + ")";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + (parameterTypes == null ? 0 : parameterTypes.hashCode());
		result = prime * result + (returnType == null ? 0 : returnType.hashCode());
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
		final FlowMethod other = (FlowMethod) obj;
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
		if (parameterTypes == null)
		{
			if (other.parameterTypes != null)
			{
				return false;
			}
		}
		else if (!parameterTypes.equals(other.parameterTypes))
		{
			return false;
		}
		if (returnType == null)
		{
			if (other.returnType != null)
			{
				return false;
			}
		}
		else if (!returnType.equals(other.returnType))
		{
			return false;
		}
		return true;
	}

}
