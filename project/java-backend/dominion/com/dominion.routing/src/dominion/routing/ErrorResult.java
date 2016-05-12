package dominion.routing;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An instance of the ErrorResult class class is written to the response 
 * in case of an error.
 */
public class ErrorResult extends ResultBase
{
    @JsonProperty("error")
    private final ErrorInfo error;
    
    ErrorResult(MethodFieldMissingException mfme)
    {
        this(400, new ErrorInfo(mfme.getMessage()) {
            @SuppressWarnings("unused")
            public String methodField = mfme.getMethodField();
        });        
    }
    
    ErrorResult(MethodMissingException mme)
    {
        this(404, new ErrorInfo(mme.getMessage()) {
            @SuppressWarnings("unused")
            public String methodName = mme.getMethodName();
        });
    }
    
    ErrorResult(PropertyMissingException pme)
    {
        this(400, new ErrorInfo(pme.getMessage()) {
            @SuppressWarnings("unused")
            public String methodName = pme.getMethodName();
            @SuppressWarnings("unused")
            public String propertyName = pme.getPropertyName();
        });
    }
    
    ErrorResult(Exception e)
    {
        this(500, new ErrorInfo(e.getMessage()));
    }
    
    public ErrorResult(String method, Exception e)
    {
    	super(false, 400, method);
    	this.error = new ErrorInfo(e.getMessage());
    }
    
    protected ErrorResult(int code, ErrorInfo error)
    {
        super(false, code);
        this.error = error;
    }
    
    @JsonProperty("error")
    public ErrorInfo getError()
    {
        return this.error;
    }
}


