package Table;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 诊断表
 */
public class TableOPC {
    private final IntegerProperty ID;
    private final StringProperty RealName;
    private final StringProperty OPC_Text;
    private final IntegerProperty OPC_ID;
    //private final IntegerProperty Other;

    public TableOPC(Integer ID, String RealName, String OPC_Text,int OPC_ID){
        this.ID=new SimpleIntegerProperty(ID);
        this.RealName=new SimpleStringProperty(RealName);
        this.OPC_Text=new SimpleStringProperty(OPC_Text);
        this.OPC_ID=new SimpleIntegerProperty(OPC_ID);

    }
    public Integer getID(){ return ID.get(); }
    public String getRealName(){ return RealName.get();}
    public String getOPC_Text(){return OPC_Text.get();}
    public Integer getOPC_ID(){return OPC_ID.get();}
    public void setOPC_Text(String text){OPC_Text.set(text);}
}
