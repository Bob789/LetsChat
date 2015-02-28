package com.mstr.letschat.model;

import org.jivesoftware.smack.util.StringUtils;

import android.os.Parcel;
import android.os.Parcelable;

public class UserProfile implements Parcelable {
	public static final int TYPE_CONTACT = 1;
	public static final int TYPE_NOT_CONTACT = 2;
	public static final int TYPE_MYSELF = 3;
	public static final int TYPE_UNKNOWN = 4;
	
	private String nickname;
	private String jid;
	private String status;
	private byte[] avatar;
	
	private int type;
	
	public UserProfile(String nickname, String jid, String status, byte[] avatar) {
		this.nickname = nickname;
		this.jid = jid;
		this.status = status;
		this.avatar = avatar;
		type = TYPE_UNKNOWN;
	}
	
	public UserProfile(String nickname, String jid, String status, byte[] avatar, int type) {
		this(nickname, jid, status, avatar);
		this.type = type;
	}
	
	private UserProfile(Parcel in) {
		nickname = in.readString();
		jid = in.readString();
		status = in.readString();
		type = in.readInt();
		avatar = new byte[in.readInt()];
		in.readByteArray(avatar);
	}

	public String getUserName() {
		return StringUtils.parseName(jid);
	}

	public String getNickname() {
		return nickname;
	}

	public String getJid() {
		return jid;
	}
	
	public String getStatus() {
		return status;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public boolean canAddToContact() {
		return type == TYPE_NOT_CONTACT;
	}
	
	public void markAsContact() {
		type = TYPE_CONTACT;
	}
	
	public byte[] getAvatar() {
		return avatar;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		
		if (o == null) {
			return false;
		}
		
		if (!(o instanceof UserProfile)) {
			return false;
		}
		
		return jid.equals(((UserProfile)o).jid);
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(nickname);
		dest.writeString(jid);
		dest.writeString(status);
		dest.writeInt(type);
		dest.writeInt(avatar.length);
		dest.writeByteArray(avatar);
	}
	
	public static final Parcelable.Creator<UserProfile> CREATOR = new Parcelable.Creator<UserProfile>() {
		@Override
		public UserProfile createFromParcel(Parcel source) {
			return new UserProfile(source);
		}

		@Override
		public UserProfile[] newArray(int size) {
			return new UserProfile[size];
		}
	};
}