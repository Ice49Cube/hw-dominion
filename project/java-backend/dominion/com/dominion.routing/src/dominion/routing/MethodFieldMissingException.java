package dominion.routing;

import java.io.IOException;

class MethodFieldMissingException extends IOException 
{
    private static final long serialVersionUID = 6867353216247027577L;
    private final String methodField;

    public MethodFieldMissingException(String message, String methodField) 
    {
        super(message);
        this.methodField = methodField;
    }

    public String getMethodField()
    {
        return this.methodField;
    }
}
