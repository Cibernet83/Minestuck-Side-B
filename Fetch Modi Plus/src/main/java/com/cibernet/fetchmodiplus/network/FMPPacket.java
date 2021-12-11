package com.cibernet.fetchmodiplus.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;

import java.util.EnumSet;

public abstract class FMPPacket
{
	protected ByteBuf data = Unpooled.buffer();
	
	
	public FMPPacket() {
	}
	
	public static FMPPacket makePacket(FMPPacket.Type type, Object... dat) {
		return type.make().generatePacket(dat);
	}
	
	public static String readLine(ByteBuf data) {
		StringBuilder str = new StringBuilder();
		
		while(data.readableBytes() > 0) {
			char c = data.readChar();
			if (c == '\n') {
				break;
			}
			
			str.append(c);
		}
		
		return str.toString();
	}
	
	public static void writeString(ByteBuf data, String str) {
		for(int i = 0; i < str.length(); ++i) {
			data.writeChar(str.charAt(i));
		}
		
	}
	
	public abstract FMPPacket generatePacket(Object... var1);
	
	public abstract FMPPacket consumePacket(ByteBuf var1);
	
	public abstract void execute(EntityPlayer var1);
	
	public abstract EnumSet<Side> getSenderSide();
	
	public enum Type
	{
		CHAT_MODUS_EJECT(ChatModusEjectPacket.class),
		UPDATE_MODUS(ModusUpdatePacket.class),
		BOOK_UPDATE_PAGE(BookModusPagePacket.class),
		REQUEST_UPDATE_MODUS(RequestModusUpdatePacket.class),
		JUJU_UPDATE(JujuModusPacket.class),
		COM_UPDATE(CommunistUpdatePacket.class),
		REQUEST_COM_UPDATE(RequestCommunistUpdatePacket.class),
		ALCHEM_WILDCARD(AlchemyWildcardPacket.class),
		BOOK_PUBLISH(BookPublishPacket.class),
		WALLET_CAPTCHA(WalletCaptchaloguePacket.class),
		;
		
		Class<? extends FMPPacket> packetType;
		
		private Type(Class<? extends FMPPacket> packetClass) {
			this.packetType = packetClass;
		}
		
		FMPPacket make() {
			try {
				return (FMPPacket)this.packetType.newInstance();
			} catch (Exception var2) {
				var2.printStackTrace();
				return null;
			}
		}
	}
}
