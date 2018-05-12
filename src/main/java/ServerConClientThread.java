/**
 * 服务器和某个客户端的通信线程
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
					//对从客户端取得的消息进行类型判断，做相应的处理
						//取得接收人的通信线程
					String deskAccount = m.getDesk();
					String url="http://112.74.177.29:8080/together/rtable/getMembers?rtableid="+deskAccount;
					String result=HTTPUtils.doGet(url,m.getCookie());

					if (deskAccount==Desk.ID) {
							for (String n : ManageServerConClient.accounts) {
								if ( n != m.getSender()) {
									ServerConClientThread scc = ManageServerConClient.getClientThread(n);
									ObjectOutputStream oos = new ObjectOutputStream(scc.s.getOutputStream());
									//向接收人发送消息
									oos.writeObject(m);
									System.out.println("收到"+m.getSender()+"发来的消息["+m.getContent()+"]转发给"+deskAccount);
								}
							}
					}
				} catch (EOFException e){
						ManageServerConClient.delClientThread(ID);
						System.out.println(ID+"号用户下线了");
						System.out.println("当前连接的线程一共有"+ManageServerConClient.hm.size());
					    this.interrupt();
					    break;
				}catch (SocketException e){
					ManageServerConClient.delClientThread(ID);
					System.out.println(ID+"号用户下线了");
					System.out.println("当前连接的线程一共有"+ManageServerConClient.hm.size());
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
