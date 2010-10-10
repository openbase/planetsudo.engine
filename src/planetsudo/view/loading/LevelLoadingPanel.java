/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.view.loading;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import view.components.LoadingAnimation;

/**
 *
 * @author divine
 */
public class LevelLoadingPanel extends JPanel {

	private LevelTitleLoadingPanel titlePanel;
	private JPanel mainPanel;
	private MenuLoadingPanel menuPanel;
	private LoadingAnimation completeLoadingProgress, nextLoadingProgress;

	public LevelLoadingPanel() {
		initComponents();
	}

	private void initComponents() {
		setBackground(Color.BLACK);
		setLayout(new BorderLayout());

		titlePanel = new LevelTitleLoadingPanel();
		mainPanel = new JPanel();
		menuPanel = new MenuLoadingPanel();
		completeLoadingProgress = new LoadingAnimation();
		nextLoadingProgress = new LoadingAnimation();

		mainPanel.add(completeLoadingProgress, BorderLayout.EAST);
		mainPanel.add(nextLoadingProgress, BorderLayout.WEST);

		add(titlePanel, BorderLayout.PAGE_START);
		add(mainPanel, BorderLayout.CENTER);
		add(menuPanel, BorderLayout.PAGE_END);
		
	}

	public void updateDynamicComponents() {
		titlePanel.updateDynamicComponents();
	}
	
	public void setLoadStateChange(String name, int stepCount) {
		
	}

	public void setLoadingStep(int counter) {

	}


}
