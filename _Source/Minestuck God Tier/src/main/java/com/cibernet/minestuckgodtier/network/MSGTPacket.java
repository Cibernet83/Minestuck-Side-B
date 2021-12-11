package com.cibernet.minestuckgodtier.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public abstract class MSGTPacket
{
    protected ByteBuf data = Unpooled.buffer();

    public abstract MSGTPacket generatePacket(Object... args);

    public abstract MSGTPacket consumePacket(ByteBuf data);

    public abstract void execute(EntityPlayer player);

    public abstract EnumSet<Side> getSenderSide();

    public static MSGTPacket makePacket(MSGTPacket.Type type, Object... dat)
    {
        return type.make().generatePacket(dat);
    }

    public enum Type
    {
        UPDATE_DATA_CLIENT(PacketUpdateGTDataFromClient.class),
        UPDATE_DATA_SERVER(PacketUpdateGTDataFromServer.class),
        INCREASE_XP(PacketAddSkillXp.class),
        ATTEMPT_BADGE_UNLOCK(PacketAttemptBadgeUnlock.class),
        TOGGLE_BADGE(PacketToggleBadge.class),
        ADD_PLAYER_XP(PacketChangePlayerXp.class),
        REQUEST_GRIST_HOARD(PacketRequestGristHoard.class),
        SEND_GRIST_HOARD(PacketSendGristHoard.class),
        UPDATE_ALL_BADGE_EFFECTS(PacketUpdateAllBadgeEffects.class),
        SET_MOUSE_SENSITIVITY(PacketSetMouseSensitivity.class),
        SEND_PARTICLE(PacketSendParticle.class),
        UPDATE_BADGE_EFFECT(PacketUpdateBadgeEffect.class),
        KEY_INPUT(PacketKeyInput.class),
        SEND_POWER_PARTICLES(PacketSendPowerParticlesState.class),
        MINDFLAYER_MOVEMENT_INPUT(PacketMindflayerMovementInput.class),
        SET_CURRENT_ITEM(PacketSetCurrentItem.class),
        EDIT_FILL_BLOCKS(PacketPlaceBlockArea.class),
        UPDATE_CONFIG(PacketUpdateConfig.class),
        ;

        Class<? extends MSGTPacket> packetType;

        Type(Class<? extends MSGTPacket> packetClass) {
            this.packetType = packetClass;
        }

        MSGTPacket make() {
            try {
                return this.packetType.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
