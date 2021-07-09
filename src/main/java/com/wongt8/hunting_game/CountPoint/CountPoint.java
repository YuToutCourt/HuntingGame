package com.wongt8.hunting_game.CountPoint;

import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CountPoint implements Comparable<CountPoint>{

    public static List<CountPoint> pointOfEveryone = new ArrayList<CountPoint>();

    private Integer pts;
    private UUID uuid;
    private int nbKill;
    private int nbKillerKill;

    public CountPoint(UUID id, Integer point){
        this.uuid = id;
        this.pts = point;
        this.nbKill = 0;
        this.nbKillerKill = 0;
    }

    public Integer getPts() {
        return pts;
    }

    public void setPts(Integer pts) {
        this.pts = pts;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getNbKill() {
        return nbKill;
    }

    public void setNbKill(int nbKill) {
        this.nbKill = nbKill;
    }

    public int getNbKillerKill() {
        return nbKillerKill;
    }

    public void setNbKillerKill(int nbKillerKill) {
        this.nbKillerKill = nbKillerKill;
    }

    public void addPts(Integer point){
        this.pts += point;
    }

    public void addKill(int nb){
        this.nbKill += nb;
    }

    public void addKillKiller(int nb){
        this.nbKillerKill += nb;
    }

    public static int getClassementOf(UUID uuid){
        int classement = 0;
        while (!(pointOfEveryone.get(classement).getUuid().equals(uuid))){
            classement++;
        }
        return pointOfEveryone.size()-classement;
    }

    public static int getPtsOf(UUID uuid){
        for(CountPoint index : pointOfEveryone){
            if(uuid.equals(index.getUuid())){
                return index.getPts();
            }
        }
        return 0;
    }

    public static int getNbKillOf(UUID uuid){
        for(CountPoint index : pointOfEveryone){
            if(uuid.equals(index.getUuid())){
                return index.getNbKill();
            }
        }
        return 0;
    }

    public static int getNbKillTargetOf(UUID uuid){
        for(CountPoint index : pointOfEveryone){
            if(uuid.equals(index.getUuid())){
                return index.getNbKill();
            }
        }
        return 0;
    }

    @Override
    public int compareTo(CountPoint o) {
        return this.getPts().compareTo(o.getPts());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CountPoint)) return false;
        CountPoint that = (CountPoint) o;
        return Objects.equals(getPts(), that.getPts()) && Objects.equals(getUuid(), that.getUuid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPts(), getUuid());
    }

    @Override
    public String toString() {
        return ("§7"+ Bukkit.getPlayer(this.uuid).getName() + " : §a§l" + this.pts);
    }


}
