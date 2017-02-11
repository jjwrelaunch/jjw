package de.jjw.model;

public class EstimatedTimes
{

    protected long weightclassId=0;
    protected long userId;
    protected int orderNumber;       
    protected long addTime=0;
    protected long startTime=0;
    protected String discepline="";
    public long getWeightclassId()
    {
        return weightclassId;
    }
    public void setWeightclassId( long weightclassId )
    {
        this.weightclassId = weightclassId;
    }
    public long getUserId()
    {
        return userId;
    }
    public void setUserId( long userId )
    {
        this.userId = userId;
    }
    public int getOrderNumber()
    {
        return orderNumber;
    }
    public void setOrderNumber( int orderNumber )
    {
        this.orderNumber = orderNumber;
    }
    public long getAddTime()
    {
        return addTime;
    }
    public void setAddTime( long addTime )
    {
        this.addTime = addTime;
    }
    public long getStartTime()
    {
        return startTime;
    }
    public void setStartTime( long startTime )
    {
        this.startTime = startTime;
    }
    public String getDiscepline()
    {
        return discepline;
    }
    public void setDiscepline( String discepline )
    {
        this.discepline = discepline;
    }
    
    
    
    
}
