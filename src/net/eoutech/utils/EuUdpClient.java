package net.eoutech.utils;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

//05x
public class EuUdpClient {
	private DatagramSocket m_socket;
	private String m_serverIP;
	private int m_serverPort;

	public EuUdpClient(String servIp, int servPort) {
		m_serverIP = servIp;
		m_serverPort = servPort;
	}

	public int sendMsg(String msg) {

		try {
			m_socket = new DatagramSocket();

			m_socket.setSoTimeout(3000);

			byte[] buffer = msg.getBytes();
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(m_serverIP),
					m_serverPort);
			m_socket.send(packet);

			byte[] b = new byte[4096];
			DatagramPacket in = new DatagramPacket(b, 0, b.length);
			m_socket.receive(in);

			System.out.println("服务器返回字节长度。。。" + in.getLength());

		} catch (Exception e) {
			return -1;
		} finally {
			if (m_socket != null) {
				m_socket.close();
			}
		}

		return 0;
	}
}
