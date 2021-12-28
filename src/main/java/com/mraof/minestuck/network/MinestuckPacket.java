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
		SELECTION(PacketSelection.class),
		DATA_CHECKER(PacketDataChecker.class),
		EFFECT_TOGGLE(PacketEffectToggle.class),
		//CHAT_MODUS_EJECT(PacketChatModusEject.class),
		//BOOK_UPDATE_PAGE(PacketBookModusPage.class),
		//JUJU_UPDATE(PacketJujuModus.class),
		//COM_UPDATE(PacketCommunistUpdate.class),
		//REQUEST_COM_UPDATE(PacketRequestCommunistUpdate.class),
		//ALCHEM_WILDCARD(PacketAlchemyWildcard.class),
		//BOOK_PUBLISH(PacketBookPublish.class),
		//WALLET_CAPTCHA(PacketWalletCaptchalogue.class),
		STONE_TABLET(PacketStoneTablet.class),
		MACHINE_CHASSIS(PacketMachineChassis.class),
		PORKHOLLOW_WITHDRAW(PacketPorkhollowWithdraw.class),
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
		GOD_KEY_INPUT(PacketGodKeyInput.class),
		SEND_POWER_PARTICLES(PacketSendPowerParticlesState.class),
		MINDFLAYER_MOVEMENT_INPUT(PacketMindflayerMovementInput.class),
		SET_CURRENT_ITEM(PacketSetCurrentItem.class),
		EDIT_FILL_BLOCKS(PacketPlaceBlockArea.class),
		SYLLADEX_DATA(PacketSylladexData.class),
		SYLLADEX_FETCH(PacketSylladexFetch.class),
		SYLLADEX_CAPTCHALOGUE(PacketSylladexCaptchalogue.class),
		SYLLADEX_EMPTY_REQUEST(PacketSylladexEmptyRequest.class),
		CONTROL_DECK_SYNC(PacketModusControlDeckSync.class),
		;
		
		Class<? extends MinestuckPacket> packetType;
		Type(Class<? extends MinestuckPacket> packetClass)
		{
			packetType = packetClass;
		}
		private MinestuckPacket make()
		{
			try {
				return this.packetType.newInstance();
			}
			catch (InstantiationException | IllegalAccessException e)
			{
				throw new RuntimeException(e);
			}
		}
	}
	
	protected final ByteBuf data = Unpooled.buffer();
	
    public static MinestuckPacket makePacket(Type type, Object... args)
    {
    	MinestuckPacket packet = type.make();
    	packet.generatePacket(args);
        return packet;
    }
	
    public abstract void generatePacket(Object... args);
    public abstract void consumePacket(ByteBuf data);
    public abstract void execute(EntityPlayer player);
    public abstract EnumSet<Side> getSenderSide();
}
