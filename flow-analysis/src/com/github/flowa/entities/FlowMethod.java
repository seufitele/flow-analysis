package com.github.flowa.entities;

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
    private final String methodDescriptor;
    private final boolean isPublic;

    // private final List<String> invariants = new ArrayList<>();

    public FlowMethod(final String name, final String methodDescriptor, final boolean isPublic)
    {
        super();
        this.name = name;
        this.methodDescriptor = methodDescriptor;
        this.isPublic = isPublic;
    }

    public String getName()
    {
        return name;
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

    public String getMethodDescriptor()
    {
        return methodDescriptor;
    }

    @Override
    public String toString()
    {
        // return "[" + name + ":" + parameterTypes + "]";
        // return (isPublic ? "public " : "") + returnType + " " + name + "(" + Util.toString(parameterTypes) + ")";
        return Util.toString(methodDescriptor, name);
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (methodDescriptor == null ? 0 : methodDescriptor.hashCode());
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
        final FlowMethod other = (FlowMethod) obj;
        if (methodDescriptor == null)
        {
            if (other.methodDescriptor != null)
            {
                return false;
            }
        }
        else if (!methodDescriptor.equals(other.methodDescriptor))
        {
            return false;
        }
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
