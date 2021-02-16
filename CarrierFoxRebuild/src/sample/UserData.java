package sample;

import java.util.List;

public class UserData
{
    private String username;
    private String email;
    private String _id;

    private boolean notifications;

    private List<Friend> friends;
    private List<Thread> threads;

    private String language;

    public String getUsername()
    {
        return username;
    }

    public String getEmail()
    {
        return email;
    }

    public String get_id()
    {
        return _id;
    }

    public boolean getNotificationSettings()
    {
        return notifications;
    }

    public List<Friend> getFriendList()
    {
        return friends;
    }

    public List<Thread> getThreadList()
    {
        return threads;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void set_id(String _id)
    {
        this._id = _id;
    }

    public void setNotificationSettings(boolean notificationSettings)
    {
        this.notifications = notificationSettings;
    }

    public void setFriendList(List<Friend> friendList)
    {
        this.friends = friendList;
    }

    public void setThreadList(List<Thread> threadList)
    {
        this.threads = threadList;
    }

    public void addThread(Thread newThread)
    {
        this.threads.add(newThread);
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
