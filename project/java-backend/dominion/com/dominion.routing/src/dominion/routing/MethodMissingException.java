package dominion.routing;

import java.io.IOException;

class MethodMissingException extends IOException 
{
    private static final long serialVersionUID = 1982812789527282848L;
    private final String methodName;

    public MethodMissingException(String message, String methodName) 
    {
        super(message);
        this.methodName = methodName;
    }

    public String getMethodName()
    {
        return this.methodName;
    }
}
