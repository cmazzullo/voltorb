package com.example.statesofmatter;

public interface Player {

    String getName();
    String getID();
    Monster[] getTeam();
    Monster getLead();
    void setLead(Monster m);
    void addMonster(Monster m, int position) throws Exception;
    Boolean allFainted();
}
