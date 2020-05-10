package com.example.lifter;

public class Contacts {
    private String name, profile_image, gym, uid;

    public Contacts(String name, String profile_image, String gym, String uid) {
        this.name = name;
        this.profile_image = profile_image;
        this.gym = gym;
        this.uid = uid;
    }

    public Contacts() {
        //empty constructor
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getGym() {
        return gym;
    }

    public void setGym(String gym) {
        this.gym = gym;
    }

    public String getUid() { return uid; }
}
