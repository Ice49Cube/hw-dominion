package dominion.commands;

import com.fasterxml.jackson.annotation.JsonProperty;

import dominion.routing.CommandBase;

public class TestServerCommand extends CommandBase {

    @JsonProperty()
    private boolean success;
    @JsonProperty()
    private Integer code;

    public TestServerCommand() {
        super("testServer");
    }
    
    @JsonProperty(value = "code")
    public Integer getCode() {
        return this.code;
    }

    @JsonProperty(value = "success")
    public boolean getSuccess() {
        return this.success;
    }

    public void setCode(Integer value) {
        this.code = value;
    }

    public void setSuccess(boolean value) {
        this.success = value;
    }
}
