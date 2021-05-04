package me.marty.openpixelmon.api.battle.client;

import me.marty.openpixelmon.api.battle.BattleStatus;
import me.marty.openpixelmon.config.OpenPixelmonConfig;
import me.marty.openpixelmon.sound.Sounds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;

/**
 * Used to manage ongoing battles
 */
@Environment(EnvType.CLIENT)
public class ClientBattleManager {

    public BattleStatus battleStatus = BattleStatus.NONE;

    /**
     * Attempts to start a battle with the participant's
     * @param participants the participants inside the battle
     * @return the current status of the battle
     */
    public BattleStatus startBattle(List<PlayerEntity> participants, MinecraftClient client) {
        if(battleStatus == BattleStatus.NONE) {
            battleStatus = BattleStatus.ONGOING;
            System.out.println(participants.size() + " Participant's in battle.");
            client.player.playSound(Sounds.BATTLE_2, OpenPixelmonConfig.musicVolume, 1);
            return BattleStatus.SUCCESS;
        }
        return battleStatus;
    }
}
