package sample;

public class Message
{
    private String _id;
    private String messageBody;
    private String user;
    private String messageSentDate;
    private boolean isEdited;

    public String getId()
    {
        return _id;
    }

    public String getMessageBody()
    {
        return messageBody;
    }

    public String getMessageSentDate()
    {
        return messageSentDate;
    }

    public String getUser()
    {
        return user;
    }

    public boolean getIsEdited()
    {
        return isEdited;
    }

    public void setMessageBody(String messageBody)
    {
        this.messageBody = messageBody;
    }

    public void setMessageSentDate(String messageSentDate)
    {
        this.messageSentDate = messageSentDate;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public void setIsEdited(boolean edited)
    {
        this.isEdited = edited;
    }

    public void setId(String id)
    {
        this._id = id;
    }
}
