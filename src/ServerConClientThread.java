/**
 * ��������ĳ���ͻ��˵�ͨ���߳�
 */

import common.ChatMessage;
import common.ChatMessageType;
import common.Desk;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;



public class ServerConClientThread extends Thread {
	private int ID;
	Socket s;
	public ServerConClientThread(Socket s){
		this.s=s;
	}

	public void run() {
			while (true) {
				ObjectInputStream ois = null;
				ChatMessage m = null;
				try {
					ois = new ObjectInputStream(s.getInputStream());
					m = (ChatMessage) ois.readObject();
					//�Դӿͻ���ȡ�õ���Ϣ���������жϣ�����Ӧ�Ĵ���
					if (m.getType().equals(ChatMessageType.COM_MES)) {//�������ͨ��Ϣ��
						//ȡ�ý����˵�ͨ���߳�
						int deskAccount = m.getReceiver();
						if (deskAccount==Desk.ID) {
							for (int n : ManageServerConClient.accounts) {
								if ( n != m.getSender()) {
									ServerConClientThread scc = ManageServerConClient.getClientThread(n);
									ObjectOutputStream oos = new ObjectOutputStream(scc.s.getOutputStream());
									//������˷�����Ϣ
									oos.writeObject(m);
								}
							}
						}

					}
				} catch (EOFException e){
						ManageServerConClient.delClientThread(ID);
						System.out.println(ID+"���û�������");
						System.out.println("��ǰ���ӵ��߳�һ����"+ManageServerConClient.hm.size());
					    this.interrupt();
					    break;
				}
				catch (Exception e) {
					e.printStackTrace();
					try {
						s.close();
						ois.close();

					} catch (IOException e1) {

					}
				}
			}


	}
	public void setID(int account){
		this.ID=account;
	}
}
