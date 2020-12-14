package sample;

import java.util.List;

public class Thread
{
    private String users;
    private String _id;
    private int threadId;
    private String name;

    public int getThreadId()
    {
        return threadId;
    }

    public String getName()
    {
        return name;
    }

    public String getUsers()
    {
        return users;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setThreadId(int threadId)
    {
        this.threadId = threadId;
    }

    public void setUsers(String users)
    {
        this.users = users;
    }
}
