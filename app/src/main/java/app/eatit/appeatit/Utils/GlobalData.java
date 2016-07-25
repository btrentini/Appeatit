package app.eatit.appeatit.Utils;

import app.eatit.appeatit.Model.User;

/**
 * Created by Tobias on 01/07/2016.
 */
public class GlobalData {

    public static GlobalData instance;
    public static GlobalData getInstance() {
        if(instance == null){
            instance = new GlobalData();
        }
        return instance;
    }

    private GlobalData(){}

    public User user;

    public void setUser(User user){
        this.user = user;

    }

    public User getUser(){
        return user;
    }

}
