package zuke.com.qxapp.Bean;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

public class Community extends BmobObject {

    private User user;
    private String name, info, owner;
    private BmobRelation relation;

    private String isrelated;  //标识值

    public String getIsrelated() {   //ALT + INS
        return isrelated;
    }

    public void setIsrelated(String isrelated) {
        this.isrelated = isrelated;
    }

    public BmobRelation getRelation() {
        return relation;
    }

    public void setRelation(BmobRelation relation) {
        this.relation = relation;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
