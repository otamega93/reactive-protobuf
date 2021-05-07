package com.example.reactiveprotobuf.models;

import java.io.Serializable;

public class PersonNonProto implements Serializable {

    private String name;

    private Integer age;

    public PersonNonProto(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public PersonNonProto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PersonNonProto{");
        sb.append("name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append('}');
        return sb.toString();
    }
}
