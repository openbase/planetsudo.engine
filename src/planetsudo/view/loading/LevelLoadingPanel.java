/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package planetsudo.view.loading;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import planetsudo.game.GameManager;
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

		setBackground(Color.BLACK);
		mainPanel.setBackground(Color.BLACK);
	}

	public void updateDynamicComponents() {
		titlePanel.updateDynamicComponents();
		menuPanel.setBackground(GameManager.getInstance().getLevel().getColor());
		nextLoadingProgress.setIndeterminate(false);
	}

	private String nextContext;
	private int maxProgress = 100;
	private int counter = 0;
	public void setLoadingStateChange(String context, int stepCount) {
		nextLoadingProgress.setText(context);
		nextContext = context;
		maxProgress = stepCount;
		nextLoadingProgress.setProcess(0);
	}

	public void setLoadingStep(int counter) {
		this.counter = counter;
		nextLoadingProgress.setText(nextContext + " "+ counter);
		nextLoadingProgress.setProcess((counter/maxProgress)*100);
	}
}
