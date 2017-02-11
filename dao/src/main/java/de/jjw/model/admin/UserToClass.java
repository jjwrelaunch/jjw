package de.jjw.model.admin;

public class UserToClass
{

    private String dicipline;
    private long classId;
    private long userId;
    private int order;
    public String getDicipline()
    {
        return dicipline;
    }
    public void setDicipline( String dicipline )
    {
        this.dicipline = dicipline;
    }
    public long getClassId()
    {
        return classId;
    }
    public void setClassId( long classId )
    {
        this.classId = classId;
    }
    public long getUserId()
    {
        return userId;
    }
    public void setUserId( long userId )
    {
        this.userId = userId;
    }
    public int getOrder()
    {
        return order;
    }
    public void setOrder( int order )
    {
        this.order = order;
    }
    
}
