package com.tracker;


public abstract class Person {
    protected String name;
    protected int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // getters and setters
    public String getName() { return name; }
    public int getAge() { return age; }


    public boolean isValid() {
        return name != null && !name.isEmpty() && age > 0;
    }
}
