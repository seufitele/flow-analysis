package com.github.flowa.main;

import java.util.List;

public class Util
{
	
    /**
     * Retorna a representacao amigavel de um method descriptor do asm
     * @param methodDescriptor
     * @return
     */
    public static String toString(final String methodDescriptor, final String methodName)
    {
        return methodName + " (" + methodDescriptor + ")";
    }
	
	public static <A> String toString(final List<A> theList)
	{
		if (theList.isEmpty())
		{
			return "";
		}
		
		final StringBuilder sBuilder = new StringBuilder();
		int i = 0;
		
		for (i = 0; i < theList.size() - 1; i++)
		{
			sBuilder.append(theList.get(i));
			sBuilder.append(",");
		}
		
		if (i == theList.size() - 1)
		{
			sBuilder.append(theList.get(i));
		}
		
		return sBuilder.toString();
	}

}
