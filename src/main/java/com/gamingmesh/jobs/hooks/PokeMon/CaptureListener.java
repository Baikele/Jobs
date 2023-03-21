package com.gamingmesh.jobs.hooks.PokeMon;

import com.aystudio.core.forge.event.ForgeEvent;
import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.actions.ItemActionInfo;
import com.gamingmesh.jobs.container.ActionType;
import com.gamingmesh.jobs.hooks.JobsHook;
import com.gamingmesh.jobs.hooks.PokeMon.CapPoke.CapPokeInfo;
import com.gamingmesh.jobs.stuff.Util;
import com.gmail.nossr50.config.experience.ExperienceConfig;
import com.gmail.nossr50.datatypes.player.McMMOPlayer;
import com.gmail.nossr50.util.player.UserManager;
import com.pixelmonmod.pixelmon.api.events.CaptureEvent;
import net.Zrips.CMILib.Items.CMIItemStack;
import net.Zrips.CMILib.Items.CMIMaterial;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class CaptureListener implements Listener {
    @EventHandler
    public void onCap(ForgeEvent event){
        if(event.getForgeEvent() instanceof CaptureEvent.SuccessfulCapture){
            CaptureEvent.SuccessfulCapture cesc = (CaptureEvent.SuccessfulCapture) event.getForgeEvent();
            Player player = Bukkit.getPlayer(cesc.getPlayer().func_110124_au());

            if (!Jobs.getGCManager().canPerformActionInWorld(player.getWorld()))
                return;

            // check if in creative
            if (!payIfCreative(player))
                return;

            if (!Jobs.getPermissionHandler().hasWorldPermission(player, player.getLocation().getWorld().getName()))
                return;

            // check if player is riding
            if (Jobs.getGCManager().disablePaymentIfRiding && player.isInsideVehicle() && !player.getVehicle().getType().equals(EntityType.BOAT))
                return;

            if (!payForItemDurabilityLoss(player))
                return;

            Jobs.action(Jobs.getPlayerManager().getJobsPlayer(player),
                    new CapPokeInfo(cesc.getPokemon().getSpecies().getLocalizedName(), ActionType.CAPPOKE));
        }

    }
    public static boolean payIfCreative(Player player) {
        return player.getGameMode() != GameMode.CREATIVE || Jobs.getGCManager().payInCreative() || Jobs.getPermissionManager().hasPermission(Jobs.getPlayerManager().getJobsPlayer(player),
                "jobs.paycreative");
    }
    public static boolean payForItemDurabilityLoss(Player p) {
        if (Jobs.getGCManager().payItemDurabilityLoss)
            return true;

        ItemStack hand = CMIItemStack.getItemInMainHand(p);

        java.util.Map<Enchantment, Integer> got = Jobs.getGCManager().whiteListedItems.get(CMIMaterial.get(hand));
        if (got == null)
            return false;

        if (Util.getDurability(hand) == 0)
            return true;

        for (Map.Entry<Enchantment, Integer> oneG : got.entrySet()) {
            Map<Enchantment, Integer> map = hand.getEnchantments();
            Integer key = map.get(oneG.getKey());

            if (key == null || key.equals(oneG.getValue()))
                return false;
        }

        return true;
    }
}
