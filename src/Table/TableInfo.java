package Table;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 用户通知表
 */
public class TableInfo {
    private final StringProperty Text;
    private final StringProperty Time;
    //private final IntegerProperty Other;

    public TableInfo( String Text,String Time){
        this.Text=new SimpleStringProperty(Text);
        this.Time=new SimpleStringProperty(Time);

    }
    public String getText(){ return Text.get();}
    public String getTime(){ return Time.get();}
}
