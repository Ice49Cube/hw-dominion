package dominion.routing;

class PropertyMissingException extends MethodMissingException 
{
    private static final long serialVersionUID = -447649157612600799L;
    private final String propertyName;

    public PropertyMissingException(String message, String methodName, String propertyName) 
    {
        super(message, methodName);
        this.propertyName = propertyName;
    }

    public String getPropertyName()
    {
        return this.propertyName;
    }
}
