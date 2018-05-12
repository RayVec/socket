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
import java.net.SocketException;


public class ServerConClientThread extends Thread {
	private String ID;
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
						//ȡ�ý����˵�ͨ���߳�
					String deskAccount = m.getDesk();
					String url="http://112.74.177.29:8080/together/rtable/getMembers?rtableid="+deskAccount;
					String result=HTTPUtils.doGet(url,m.getCookie());

					if (deskAccount==Desk.ID) {
							for (String n : ManageServerConClient.accounts) {
								if ( n != m.getSender()) {
									ServerConClientThread scc = ManageServerConClient.getClientThread(n);
									ObjectOutputStream oos = new ObjectOutputStream(scc.s.getOutputStream());
									//������˷�����Ϣ
									oos.writeObject(m);
									System.out.println("�յ�"+m.getSender()+"��������Ϣ["+m.getContent()+"]ת����"+deskAccount);
								}
							}
					}
				} catch (EOFException e){
						ManageServerConClient.delClientThread(ID);
						System.out.println(ID+"���û�������");
						System.out.println("��ǰ���ӵ��߳�һ����"+ManageServerConClient.hm.size());
					    this.interrupt();
					    break;
				}catch (SocketException e){
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
	public void setID(String account){
		this.ID=account;
	}
}
