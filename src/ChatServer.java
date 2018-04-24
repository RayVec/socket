
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import common.ChatMessage;
import common.ChatMessageType;
import common.User;


public class ChatServer {
	public ChatServer(){
		ServerSocket ss = null;
		try {
			ss=new ServerSocket(80);
			System.out.println("������������ in "+new Date()+" "+ InetAddress.getLocalHost());
			while(true){
				Socket s=ss.accept();
				//���ܿͻ��˷�������Ϣ
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				User u=(User) ois.readObject();
				ChatMessage m=new ChatMessage();
				ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
		        if(u.getOperation().equals("login")){ //��¼
		        	    int account=u.getAccount();
						System.out.println("["+account+"]�����ˣ�");

						//�õ�������Ϣ
						//String user=udao.getUser(account);
						//����һ���ɹ���½����Ϣ����������������Ϣ
						m.setType(ChatMessageType.SUCCESS);
						m.setContent(Integer.toString(u.getAccount()));
						oos.writeObject(m);
						ServerConClientThread cct=new ServerConClientThread(s);//����һ���̣߳��ø��߳���ÿͻ��˱�������
					    cct.setID(u.getAccount());
						ManageServerConClient.addClientThread(u.getAccount(),cct);
					    System.out.println("��ǰ���ӵ��߳���"+ManageServerConClient.hm.size());
						cct.start();//������ÿͻ���ͨ�ŵ��߳�
					}
		        else if(u.getOperation().equals("register")){
				    /**ע���û�
		        	UserDao udao=new UserDao();
		        	if(udao.register(u)){
		        		//����һ��ע��ɹ�����Ϣ��
						m.setType(common.ChatMessageType.SUCCESS);
						oos.writeObject(m);
		        	}
					 */
		        }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

}