package Table;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * 用户信息表
 */
public class TableUser {
    private final StringProperty RealName;
    private final IntegerProperty Age;
    private final StringProperty Sex;
    private final StringProperty IDCARD;
    private final StringProperty UserName;
    private final StringProperty PassWord;


    public TableUser( String RealName, Integer Age, String Sex, String IDCARD, String UserName, String PassWord) {
        this.RealName = new SimpleStringProperty(RealName);
        this.Age = new SimpleIntegerProperty(Age);
        this.Sex = new SimpleStringProperty(Sex);
        this.IDCARD = new SimpleStringProperty(IDCARD);
        this.UserName = new SimpleStringProperty(UserName);
        this.PassWord = new SimpleStringProperty(PassWord);
    }

    public String getPassWord() {
        return PassWord.get();
    }

    public StringProperty passWordProperty() {
        return PassWord;
    }

    public String getUserName() {
        return UserName.get();
    }

    public StringProperty userNameProperty() {
        return UserName;
    }

    public String getIDCARD() {
        return IDCARD.get();
    }

    public StringProperty IDCARDProperty() {
        return IDCARD;
    }

    public String getSex() {
        return Sex.get();
    }

    public StringProperty sexProperty() {
        return Sex;
    }

    public int getAge() {
        return Age.get();
    }

    public IntegerProperty ageProperty() {
        return Age;
    }

    public String getRealName() {
        return RealName.get();
    }

    public StringProperty realNameProperty() {
        return RealName;
    }
}
