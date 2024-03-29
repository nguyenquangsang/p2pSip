package me.socketdemo;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.net.*;

/**
 * 
 * @author Admin
 */
public class Server {
	public static void main(String args[]) {
		try {
			String cau;// Cau tu client gui toi
			String ketQua = "";// Cau sau khi xu ly xong tra ve client
			ServerSocket ss = new ServerSocket(9999);// Tao cong 9999 de server
														// lang nghe
			while (true)// Cho client ket noi
			{
				// Khong dung multithread
				Socket connectionSocket = ss.accept();
				BufferedReader fromClient = new BufferedReader(
						new InputStreamReader(connectionSocket.getInputStream()));// Tao
																					// input
																					// stream
				DataOutputStream sendToClient = new DataOutputStream(
						connectionSocket.getOutputStream());// Tao output stream
				// Thread.sleep(5000);// Giả sử server phải xử lý mất 5s
				StringBuilder msgData = new StringBuilder();
				while (true) {
					cau = fromClient.readLine();
					System.out.println("FROM CLIENT: " + cau);// In ra chuỗi
																// server nhận
																// đc từ client
					if (cau.equals("start_msg")) {
						System.out.println("start_msg");
						msgData = new StringBuilder();
					} else {
						if (cau.equals("end_msg")) {
							System.out.println("end_msg");
							System.out.println(msgData.toString());
						} else {
							System.out.println("append_msg");
							msgData.append(cau);
						}
					}
					
					if (cau.equalsIgnoreCase("quit"))
						break;

					// Dao chuoi nhan duoc
					StringBuilder str = new StringBuilder(cau);
					ketQua = str.reverse().toString();

					sendToClient.writeBytes(ketQua + '\n');
				}

				// Su dung multithread
				// Khi co 1 client gui yeu cau toi thi se tao ra 1 thread phuc
				// vu client do
				// new ThreadSocket(ss.accept()).start();
			}
		} catch (IOException e) {
			System.out.println("Exception: " + e.getMessage());
		}
		// catch (InterruptedException ie)
		{
		}
	}
}