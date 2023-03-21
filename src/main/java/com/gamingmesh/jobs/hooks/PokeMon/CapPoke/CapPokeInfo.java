package com.gamingmesh.jobs.hooks.PokeMon.CapPoke;

import com.gamingmesh.jobs.container.ActionType;
import com.gamingmesh.jobs.container.BaseActionInfo;

public class CapPokeInfo extends BaseActionInfo {
    private String name;

    public CapPokeInfo(String name, ActionType type) {
        super(type);
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getNameWithSub() {
        return name;
    }
}
