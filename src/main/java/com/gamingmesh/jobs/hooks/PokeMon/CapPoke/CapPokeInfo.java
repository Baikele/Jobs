package com.gamingmesh.jobs.hooks.PokeMon.CapPoke;

import com.gamingmesh.jobs.container.ActionType;
import com.gamingmesh.jobs.container.BaseActionInfo;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;

public class CapPokeInfo extends BaseActionInfo {
    private String pokemon;

    public CapPokeInfo(String pokemon, ActionType type) {
        super(type);
        this.pokemon = pokemon;
    }

    @Override
    public String getName() {
        return pokemon;
    }

    @Override
    public String getNameWithSub() {
        return pokemon;
    }
}
