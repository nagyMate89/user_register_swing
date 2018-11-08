package gui;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import controller.MessageServer;
import model.Message;

class ServerInfo {
	private String name;
	private int id;
	private boolean checked;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String toString() {
		return name;
	}

	public ServerInfo(String name, int id, boolean checked) {
		this.name = name;
		this.id = id;
		this.checked = checked;
	}
}

public class MessagePanel extends JPanel {
	private JTree serverTree;
	private ServerTreeCellRenderer treeCellRenderer;
	private ServerTreeCellEditor treeCellEditor;
	private Set<Integer> selectedServers;
	private MessageServer messageServer;
	private ProgressDialog progressDialog;

	public MessagePanel(JFrame parent) {
		messageServer = new MessageServer();
		selectedServers = new TreeSet<Integer>();
		progressDialog = new ProgressDialog(parent);
		selectedServers.add(0);
		selectedServers.add(1);
		selectedServers.add(2);
		
		
		serverTree = new JTree(createTree());
		setLayout(new BorderLayout());
		treeCellRenderer = new ServerTreeCellRenderer();
		treeCellEditor = new ServerTreeCellEditor();
		
		
		
		serverTree.setEditable(true);
		
		serverTree.setCellRenderer(treeCellRenderer);
		serverTree.setCellEditor(treeCellEditor);
		serverTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		treeCellEditor.addCellEditorListener(new CellEditorListener() {
			
			@Override
			public void editingStopped(ChangeEvent arg0) {
				ServerInfo info = (ServerInfo)treeCellEditor.getCellEditorValue();
			int serverId = info.getId();
			if (info.isChecked()) {
				selectedServers.add(serverId);
			} else {
				selectedServers.remove(serverId);
			}
			messageServer.setSelectedServers(selectedServers);
			retrieveMessages();
			}
			
			
			@Override
			public void editingCanceled(ChangeEvent arg0) {
			
				
			}
		});

		serverTree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				
						DefaultMutableTreeNode node = (DefaultMutableTreeNode) serverTree.getLastSelectedPathComponent();
						
						try {
						Object userObject = node.getUserObject();
						} catch (NullPointerException ex) {
							System.out.println("nullpoint");
						};
						
						
					
						
					
			
				
		}
		});
	
		add(new JScrollPane(serverTree), BorderLayout.CENTER);

	}
	
	public void retrieveMessages() {
		
		System.out.println(messageServer.messageCount());
		
		SwingUtilities.invokeLater(new Runnable(
				) {
			
			@Override
			public void run() {
				progressDialog.setVisible(true);
			}
		});
		
		
		SwingWorker<List<Message>,Integer> worker = new SwingWorker<List<Message>,Integer>() {
			
		

			@Override
			protected void done() {
				try {
					List<Message> retrieved = get();
					System.out.println("retrieved" + retrieved.size() + " messages");
				} catch (InterruptedException | ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				progressDialog.setVisible(false);
			}

			@Override
			protected void process(List<Integer> counts) {
				
				int retrieved=counts.get(counts.size()-1);
				System.out.println("Messages recieved: " + retrieved);
			}

			@Override
			protected List<Message> doInBackground() throws Exception {
				
				List<Message> retrievedMessages = new ArrayList<Message>();
				int count=0 ;
				for (Message message: messageServer) {
					System.out.print(message.getTitle() + " : ");
					System.out.println(message.getContents());
					retrievedMessages.add(message);
					count++;
					publish(count);
				}
				return retrievedMessages;
			}
			
		};
		
		worker.execute();
		
	}

	private DefaultMutableTreeNode createTree() {

		DefaultMutableTreeNode top = new DefaultMutableTreeNode("SERVERS");
		DefaultMutableTreeNode branch1 = new DefaultMutableTreeNode("USA");

		DefaultMutableTreeNode server1 = new DefaultMutableTreeNode(
				new ServerInfo("NEW YORK", 0, (selectedServers.contains(0))));
		DefaultMutableTreeNode server2 = new DefaultMutableTreeNode(
				new ServerInfo("SAN FRANSISCO", 1, selectedServers.contains(1)));
		branch1.add(server1);
		branch1.add(server2);

		DefaultMutableTreeNode branch2 = new DefaultMutableTreeNode("UK");

		DefaultMutableTreeNode server3 = new DefaultMutableTreeNode(
				new ServerInfo("LONDON", 2, selectedServers.contains(2)));
		DefaultMutableTreeNode server4 = new DefaultMutableTreeNode(
				new ServerInfo("MANCHESTER", 3, selectedServers.contains(3)));
		branch2.add(server3);
		branch2.add(server4);

		top.add(branch1);
		top.add(branch2);
		return top;
	}
}
