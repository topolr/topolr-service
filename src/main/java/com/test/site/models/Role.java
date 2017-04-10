package com.test.site.models;

import com.axes.mvc.annotation.Id;
import com.axes.mvc.annotation.Model;

@Model(name = "role")
public class Role {

    @Id
    private int id;
    private String rolename;
    private String rolemapping;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getRolemapping() {
        return rolemapping;
    }

    public void setRolemapping(String rolemapping) {
        this.rolemapping = rolemapping;
    }
}
