package me.p2p;

import org.json.JSONException;
import org.json.JSONObject;

public class MessageParser {
	JSONObject rawData;
	EMsgType msgType;
	JSONObject msgData;

	public MessageParser(JSONObject data) {
		this.rawData = data;
		
		parse();
	}

	private void parse() {
		try {
			// parse msg type;
			String sMsgType = rawData.getString(MsgAttribute.MSG_TYPE);
			// translate to msgType;
			if (sMsgType.equals(MsgAttribute.MSG_TYPE_JOIN)) {
				msgType = EMsgType.JOIN;
			} else {
				if (sMsgType.equals(MsgAttribute.MSG_TYPE_LEAVE)) {
					msgType = EMsgType.LEAVE;
				} else {
					if (sMsgType.equals(MsgAttribute.MSG_TYPE_UPDATE)) {
						msgType = EMsgType.UPDATE;
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// get msg data;
		try {
			String sMsgData = rawData.getString(MsgAttribute.MSG_DATA);
			msgData = new JSONObject(sMsgData);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public EMsgType getMessageType() {
		return this.msgType;
	}
	
	public JSONObject getMessageData() {
		return this.msgData;
	}
}