package com.huasun.display.database;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * author:songwenming
 * Date:2019/9/24
 * Description:
 */
@Entity(nameInDb = "user_profile")
public class UserProfile {
    @Id
    private long userId=0;
    private String name=null;
    private String department;
    private String avatar=null;
    private String gender=null;
    private String address=null;
    private String rank=null;
    private String position=null;
    @Generated(hash = 95275183)
    public UserProfile(long userId, String name, String department, String avatar,
            String gender, String address, String rank, String position) {
        this.userId = userId;
        this.name = name;
        this.department = department;
        this.avatar = avatar;
        this.gender = gender;
        this.address = address;
        this.rank = rank;
        this.position = position;
    }
    @Generated(hash = 968487393)
    public UserProfile() {
    }
    public long getUserId() {
        return this.userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDepartment() {
        return this.department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getAvatar() {
        return this.avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public String getGender() {
        return this.gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getRank() {
        return this.rank;
    }
    public void setRank(String rank) {
        this.rank = rank;
    }
    public String getPosition() {
        return this.position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
}
