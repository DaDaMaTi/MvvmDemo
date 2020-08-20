package com.csmar.mvvmdemo.bean;

public class Address {

    public Person person;

    public class Person {
        /**
         * address : 高新技术开发区---产业园
         * phone : 18714995190
         * name : 达达的马蹄
         */
        public String address;
        public String phone;
        public String name;

        @Override
        public String toString() {
            return "Person{" +
                    "address='" + address + '\'' +
                    ", phone='" + phone + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Address{" +
                "person=" + person +
                '}';
    }
}
