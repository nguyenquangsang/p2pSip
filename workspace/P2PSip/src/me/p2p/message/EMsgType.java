package me.p2p.message;

public enum EMsgType {
	/**
	 * Peer node send this request to join p2p network
	 */
	JOIN,
	/**
	 * Peer node send this request to leave p2p network
	 */
	LEAVE,
	/**
	 * Peer node send this request to update peer info
	 */
	UPDATE,
	/**
	 * Bootstrap send this request to transfer data list
	 */
	TRANSFER_LIST,
	/**
	 * Add peer node
	 */
	ADD_NODE
}
