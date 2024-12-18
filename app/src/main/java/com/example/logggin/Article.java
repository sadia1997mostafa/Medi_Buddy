package com.example.logggin;

import java.util.List;

public class Article {
    private String name;
    private String bodyPart;
    private String equipment;
    private String target;
    private List<String> secondaryMuscles;
    private List<String> instructions;

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBodyPart() { return bodyPart; }
    public void setBodyPart(String bodyPart) { this.bodyPart = bodyPart; }

    public String getEquipment() { return equipment; }
    public void setEquipment(String equipment) { this.equipment = equipment; }

    public String getTarget() { return target; }
    public void setTarget(String target) { this.target = target; }

    public List<String> getSecondaryMuscles() { return secondaryMuscles; }
    public void setSecondaryMuscles(List<String> secondaryMuscles) { this.secondaryMuscles = secondaryMuscles; }

    public List<String> getInstructions() { return instructions; }
    public void setInstructions(List<String> instructions) { this.instructions = instructions; }
}