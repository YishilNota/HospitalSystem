package Table;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 医师信息表
 */
public class TableChief {

    private final IntegerProperty ID;
    private final StringProperty RealName;
    private final StringProperty WorkTime;
    //private final IntegerProperty Other;

    public TableChief(Integer ID, String RealName, String WorkTime){
        this.ID=new SimpleIntegerProperty(ID);
        this.WorkTime=new SimpleStringProperty(WorkTime);
        this.RealName=new SimpleStringProperty(RealName);

    }
    public Integer getID(){ return ID.get(); }
    public String getRealName(){ return RealName.get();}
    public String getWorkTime(){return WorkTime.get();}


}