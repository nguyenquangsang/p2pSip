package me.p2p.resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import me.p2p.PeerInfo;
import me.p2p.PeerInfoParser;
import me.p2p.spec.IData;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataManager implements IData {
	static final String ERROR = "Must call prepare() first";
	static final String FILE_NAME = "list_peer.json";
	static final String INIT_DATA = "[]"; // dữ liệu khởi đầu là mảng rỗng;

	static DataManager INSTANCE = null;
	String filePath;

	File fileListPeer;

	ArrayList<PeerInfo> listPeerInfo;

	public static void prepare(String filePath) {
		INSTANCE = new DataManager(filePath);
	}

	public static DataManager getInstance() {
		if (INSTANCE == null) {
			throw new IllegalStateException(ERROR);
		}

		return INSTANCE;
	}

	private DataManager(String filePath) {
		// TODO Auto-generated constructor stub
		this.filePath = filePath + "/" + FILE_NAME;
		this.fileListPeer = new File(this.filePath);

		/*
		 * Nếu file không tồn tại thì khởi tạo nó và init data;
		 */
		if (!fileListPeer.exists()) {
			try {
				fileListPeer.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				FileUtils.writeStringToFile(fileListPeer, INIT_DATA);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		listPeerInfo = new ArrayList<>();

		// read file only one time;
		readFile();
	}

	private void readFile() {
		ArrayList<PeerInfo> result = new ArrayList<>();
		PeerInfoParser peerInfoParser = new PeerInfoParser();
		String data = null;
		try {
			data = FileUtils.readFileToString(fileListPeer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONArray jsPeerList = null;
		try {
			jsPeerList = new JSONArray(data);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < jsPeerList.length(); i++) {
			try {
				JSONObject jsonObject = jsPeerList.getJSONObject(i);
				result.add(peerInfoParser.parseToDirectResult(jsonObject));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void writeToFile() {
		JSONArray jaPeerInfo = new JSONArray();
		// convert from list to json array;
		for (PeerInfo peerInfo : listPeerInfo) {
			jaPeerInfo.put(peerInfo.toJSONObject());
		}

		// write to file;
		try {
			String data = jaPeerInfo.toString();
			FileUtils.writeStringToFile(fileListPeer, data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<PeerInfo> getListPeer() {
		// TODO Auto-generated method stub
		return listPeerInfo;
	}
	
	public JSONArray getJsonListPeer() {
		JSONArray jsListPeer = new JSONArray();
		for (PeerInfo peerInfo : listPeerInfo) {
			jsListPeer.put(peerInfo.toJSONObject());
		}
		
		return jsListPeer;
	}

	@Override
	public void add(PeerInfo peerInfo) {
		// TODO Auto-generated method stub
		if (!isExist(peerInfo)) {
			listPeerInfo.add(peerInfo);

			writeToFile();
		}
	}

	@Override
	public void remove(PeerInfo peerInfo) {
		// TODO Auto-generated method stub
		for (PeerInfo p : listPeerInfo) {
			if (p.equals(peerInfo)) {
				listPeerInfo.remove(p);
				break;
			}
		}

		writeToFile();
	}

	@Override
	public void update(PeerInfo peerInfo) {
		// TODO Auto-generated method stub
		int index = -1;
		for (int i = 0; i < listPeerInfo.size(); i++) {
			PeerInfo p = listPeerInfo.get(i);
			if (p.equals(peerInfo)) {
				index = i;
				break;
			}
		}

		if (index != -1) {
			listPeerInfo.remove(index);
			listPeerInfo.add(index, peerInfo);
		}

		writeToFile();
	}

	@Override
	public boolean isExist(PeerInfo peerInfo) {
		// TODO Auto-generated method stub
		for (PeerInfo pInfo : listPeerInfo) {
			if (pInfo.equals(peerInfo)) {
				return true;
			}
		}

		return false;
	}

}