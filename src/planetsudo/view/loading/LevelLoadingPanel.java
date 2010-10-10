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
	private MenuLoadingPanel menuPanel;
	private LoadingAnimation completeLoadingProgress, nextLoadingProgress;

	public LevelLoadingPanel() {
		initComponents();
	}

	private void initComponents() {
		setBackground(Color.BLACK);
		setLayout(new BorderLayout());

		titlePanel = new LevelTitleLoadingPanel();
		menuPanel = new MenuLoadingPanel();
		completeLoadingProgress = new LoadingAnimation();
		nextLoadingProgress = new LoadingAnimation();
		
		add(titlePanel, BorderLayout.NORTH);
		add(menuPanel, BorderLayout.SOUTH);
		add(completeLoadingProgress, BorderLayout.EAST);
		add(nextLoadingProgress, BorderLayout.WEST);
	}

}
