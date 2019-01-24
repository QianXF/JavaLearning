package com.qianxuefeng.base;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author qianxuefeng
 * @since 2017年01月18日
 */
public class HashCodeTest {

    public static void main(String[] args){
        Collection<Person> set = new HashSet<Person>();
        set.add(new Person("张三", 21));
        set.add(new Person("李四", 19));
        set.add(new Person("王五", 22));
        set.add(new Person("张三", 21));

        for(Person person : set){
            System.out.println(person.toString());
        }
    }

    static class Person
    {

        private String name;
        private int age;

        public Person(String name, int age)
        {
            this.name = name;
            this.age = age;
        }

        public String toString()
        {
            return "{" + name + ", " + age + "}";
        }

        public int hashCode()
        {
            return name.hashCode() + age * 10;
        }

        public boolean equals(Object obj)
        {
            if (this == obj) {
                return true;
            }

            if (obj instanceof Person){
                Person p = (Person)obj;
                return this.name.equals(p.name) && this.age == p.age;
            }
            return false;
        }
    }
}