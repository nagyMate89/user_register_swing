package gui;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

public class Toolbar extends JToolBar implements ActionListener {

	private JButton saveBtn;
	private JButton refreshBtn;
	private ToolbarListener toolbarListener;



	public Toolbar() {
		
		setFloatable(false);
		saveBtn = new JButton();
		saveBtn.setToolTipText("Save");
		saveBtn.setIcon(Utils.createIcon("/images/save.gif"));
		saveBtn.setBackground(Color.WHITE);
		refreshBtn = new JButton();
		refreshBtn.setToolTipText("Refresh");
		refreshBtn.setIcon(Utils.createIcon("/images/refresh.gif"));
		refreshBtn.setBackground(Color.ORANGE);
		saveBtn.addActionListener(this);
		refreshBtn.addActionListener(this);

		
		add(saveBtn);
		
		add(refreshBtn);

	}

	public void setToolbarListener(ToolbarListener listener) {
		this.toolbarListener=listener;
	}
	
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clicked = (JButton) e.getSource();
		if (clicked == saveBtn) {
			toolbarListener.saveEventOccured();
			
			}
			
			if(clicked==refreshBtn){
			if (toolbarListener!=null) {
				toolbarListener.refreshEventOccured();
			}
		}

	}


}
