package com.mraof.minestuck.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public abstract class MinestuckPacket
{
	public enum Type
	{
		GRISTCACHE(PacketGristCache.class),
		MACHINE_STATE(PacketMachineState.class),
		GOBUTTON(PacketGoButton.class),
		ALCHEMITER_PACKET(PacketAlchemiter.class),
		PLAYER_DATA(PacketPlayerData.class),
		SBURB_CONNECT(PacketSburbConnect.class),
		SBURB_CLOSE(PacketSburbConnectClosed.class),
		LANDREGISTER(PacketLandRegister.class),
		CLEAR(PacketClearMessage.class),
		SBURB_INFO(PacketSkaianetInfo.class),
		CLIENT_EDIT(PacketClientEdit.class),
		SERVER_EDIT(PacketServerEdit.class),
		CONFIG(PacketMinestuckConfig.class),
		TRANSPORTALIZER(PacketTransportalizer.class),
		CONTAINER(PacketMiscContainer.class),
		INVENTORY(PacketInventoryChanged.class),
		CAPTCHA(PacketCaptchaDeck.class),
		SELECTION(PacketSelection.class),
		DATA_CHECKER(PacketDataChecker.class),
		EFFECT_TOGGLE(PacketEffectToggle.class),
		CHAT_MODUS_EJECT(PacketChatModusEject.class),
		UPDATE_MODUS(PacketModusUpdate.class),
		BOOK_UPDATE_PAGE(PacketBookModusPage.class),
		REQUEST_UPDATE_MODUS(PacketRequestModusUpdate.class),
		JUJU_UPDATE(PacketJujuModus.class),
		COM_UPDATE(PacketCommunistUpdate.class),
		REQUEST_COM_UPDATE(PacketRequestCommunistUpdate.class),
		ALCHEM_WILDCARD(PacketAlchemyWildcard.class),
		BOOK_PUBLISH(PacketBookPublish.class),
		WALLET_CAPTCHA(PacketWalletCaptchalogue.class),
		STONE_TABLET(PacketStoneTablet.class),
		MACHINE_CHASSIS(PacketMachineChassis.class),
		ATM(PacketPorkhollowAtm.class),
		VAULT(PacketBoondollarRegister.class),
		FLIGHT_EFFECT(PacketStopFlightEffect.class),
		BUILD_INHIBIT_EFFECT(PacketStopBuildInhibitEffect.class),
		RESET_COOLDOWN(PacketResetCooldown.class),
		UPDATE_STRIFE(PacketUpdateStrifeData.class),
		ASSIGN_STRIFE(PacketAssignStrife.class),
		RETRIEVE_STRIFE(PacketRetrieveStrifeCard.class),
		SET_ACTIVE_STRIFE(PacketSetActiveStrife.class),
		SWAP_OFFHAND_STRIFE(PacketSwapOffhandStrife.class),
		UPDATE_BEAMS(PacketUpdateBeamData.class),
		LEFT_CLICK_EMPTY(PacketLeftClickEmpty.class),
		ROCKET_BOOTS(PacketRocketBoots.class),
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
		;
		
		Class<? extends MinestuckPacket> packetType;
		private Type(Class<? extends MinestuckPacket> packetClass)
		{
			packetType = packetClass;
		}
		MinestuckPacket make()
		{
			try {
				return this.packetType.newInstance();
			} catch (Exception e) 
			{
				e.printStackTrace();
				return null;
			}
		}
	}
	
	protected ByteBuf data = Unpooled.buffer();
	
    public static MinestuckPacket makePacket(Type type, Object... dat)
    {
        return type.make().generatePacket(dat);
    }
	
	public static String readLine(ByteBuf data)
	{
		StringBuilder str = new StringBuilder();
		while(data.readableBytes() > 0)
		{
			char c = data.readChar();
			if(c == '\n')
				break;
			else str.append(c);
		}
		
		return str.toString();
	}
	
	public static void writeString(ByteBuf data, String str)
	{
		for(int i = 0; i < str.length(); i++)
			data.writeChar(str.charAt(i));
	}
	
    public abstract MinestuckPacket generatePacket(Object... data);

    public abstract MinestuckPacket consumePacket(ByteBuf data);
    public abstract void execute(EntityPlayer player);
    public abstract EnumSet<Side> getSenderSide();
}
