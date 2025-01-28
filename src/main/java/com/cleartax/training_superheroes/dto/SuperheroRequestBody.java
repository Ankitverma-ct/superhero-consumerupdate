package com.cleartax.training_superheroes.dto;

import lombok.Data;

@Data
public class SuperheroRequestBody {
    private String superheroName;
    private String power;
    private String universe;

    public SuperheroRequestBody(String superheroName, String power, String universe) {
        this.superheroName = superheroName;
        this.power = power;
        this.universe = universe;
    }

    public void setName(String superheroName) {
        
    }

//    public void setName(String superheroName) {
//    }

//    public static SuperheroRequestBodyBuilder builder() {
//        return new SuperheroRequestBodyBuilder();
//    }
//
//    public String getSuperheroName() {
//        return this.superheroName;
//    }
//
//    public String getPower() {
//        return this.power;
//    }
//
//    public String getUniverse() {
//        return this.universe;
//    }
//
//    public static class SuperheroRequestBodyBuilder {
//        private String name;
//        private String power;
//        private String universe;
//
//        SuperheroRequestBodyBuilder() {
//        }
//
//        public SuperheroRequestBodyBuilder name(String name) {
//            this.name = name;
//            return this;
//        }
//
//        public SuperheroRequestBodyBuilder power(String power) {
//            this.power = power;
//            return this;
//        }
//
//        public SuperheroRequestBodyBuilder universe(String universe) {
//            this.universe = universe;
//            return this;
//        }
//
//        public SuperheroRequestBody build() {
//            return new SuperheroRequestBody(this.name, this.power, this.universe);
//        }
//
//        public String toString() {
//            return "SuperheroRequestBody.SuperheroRequestBodyBuilder(name=" + this.name + ", power=" + this.power + ", universe=" + this.universe + ")";
//        }
//    }
}