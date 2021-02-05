package sample;

public class ErrorMessage
{
    private String userMessage;
    private int errorCode;

    public int getErrorCode()
    {
        return errorCode;
    }

    public String getUserMessage()
    {
        return userMessage;
    }

    public void setErrorCode(int errorCode)
    {
        this.errorCode = errorCode;
    }

    public void setUserMessage(String userMessage)
    {
        this.userMessage = userMessage;
    }
}
