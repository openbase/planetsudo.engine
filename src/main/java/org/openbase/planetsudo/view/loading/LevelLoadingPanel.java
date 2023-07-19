/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.openbase.planetsudo.view.loading;

/*-
 * #%L
 * PlanetSudo GameEngine
 * %%
 * Copyright (C) 2009 - 2023 openbase.org
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import org.openbase.jul.visual.swing.animation.LoadingAnimation;
import org.slf4j.Logger;
import org.openbase.planetsudo.game.GameManager;
import org.slf4j.LoggerFactory;

/**
 *
 * @author <a href="mailto:divine@openbase.org">Divine Threepwood</a>
 */
public class LevelLoadingPanel extends JPanel {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    
	private LevelTitleLoadingPanel titlePanel;
	private JPanel mainPanel, leftBorder, rightBorder;
	private MenuLoadingPanel menuPanel;
	//private LoadingAnimation completeLoadingProgress;
	private LoadingAnimation nextLoadingProgress;

	public LevelLoadingPanel() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new BorderLayout());
		titlePanel = new LevelTitleLoadingPanel();
		mainPanel = new JPanel();
		leftBorder = new JPanel();
		rightBorder = new JPanel();
		menuPanel = new MenuLoadingPanel();
		//completeLoadingProgress = new LoadingAnimation();
		nextLoadingProgress = new LoadingAnimation();
		BoxLayout layout = new BoxLayout(mainPanel, BoxLayout.LINE_AXIS);
		mainPanel.setLayout(layout);
		mainPanel.setBackground(Color.BLACK);
		mainPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, java.awt.Color.white, new java.awt.Color(204, 204, 204), java.awt.Color.gray, new java.awt.Color(0, 0, 0)));
		leftBorder.setOpaque(false);
		rightBorder.setOpaque(false);
		menuPanel.setOpaque(false);
		leftBorder.setPreferredSize(new Dimension(100,0));
		rightBorder.setPreferredSize(new Dimension(100,0));
		

		//mainPanel.add(completeLoadingProgress);
		mainPanel.add(nextLoadingProgress);

		add(titlePanel, BorderLayout.PAGE_START);
		add(mainPanel, BorderLayout.CENTER);
		add(menuPanel, BorderLayout.PAGE_END);
		add(rightBorder, BorderLayout.EAST);
		add(leftBorder, BorderLayout.WEST);
	}

	public void updateDynamicComponents() {
		titlePanel.updateDynamicComponents();
		setBackground(GameManager.getInstance().getLevel().getColor());
		nextLoadingProgress.setIndeterminate(false);
	}

	private String nextContext;
	private int maxProgress = 100;
	private int counter = 0;
	public void setLoadingStateChange(String context, int stepCount) {
		nextLoadingProgress.setText(context);
		nextContext = context;
		maxProgress = stepCount;
		counter = 0;
		nextLoadingProgress.setProcess(0);
	}

	public void setLoadingStep(int counter) {
		if(counter == -1) {
			this.counter++;
		} else {
			this.counter = counter;
		}
		nextLoadingProgress.setText(nextContext + " "+ this.counter);
		logger.info("SetProgressTo:"+(this.counter*100/maxProgress));
		nextLoadingProgress.setProcess((this.counter*100/maxProgress));
	}
}
