package Table;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 病历表
 */
public class TableOPCInfo {
    private final StringProperty WorkName;
    private final StringProperty OpcText;


    public TableOPCInfo(String workName, String opcText) {
        WorkName = new SimpleStringProperty(workName);
        OpcText = new SimpleStringProperty(opcText);
    }

    public String getOpcText() {
        return OpcText.get();
    }

    public String getWorkName() {
        return WorkName.get();
    }
}
