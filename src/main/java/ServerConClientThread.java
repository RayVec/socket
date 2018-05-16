/**
 * ��������ĳ���ͻ��˵�ͨ���߳�
 */

import common.ChatMessage;
import common.ChatMessageType;
import common.Desk;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;


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
					List<String> deskmembers=new ArrayList<String>();
					JSONArray jsonArray=JSONArray.fromObject(result);
					for(Object object:jsonArray){
						JSONObject jsonObject=JSONObject.fromObject(object);
						deskmembers.add(jsonObject.getString("userid"));
					}
					for (String deskmember : deskmembers) {
								if ( !deskmember.equals(m.getSender())) {
									if(ManageServerConClient.accounts.contains(deskmember)) {
										ServerConClientThread scc = ManageServerConClient.getClientThread(deskmember);
										ObjectOutputStream oos = new ObjectOutputStream(scc.s.getOutputStream());
										//������˷�����Ϣ
										oos.writeObject(m);
										System.out.println(m.getDesk()+"Բ�����յ�"+m.getSender()+" ��������Ϣת����"+deskmember);
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
