/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainGui.java
 *
 * Created on Jun 9, 2010, 9:04:17 AM
 */

package planetsudo.view;

import planetsudo.view.configuration.ConfigurationPanel;
import planetsudo.view.game.GamePanel;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import logging.Logger;
import logging.Logger.Channel;
import planetsudo.main.GUIController;
import planetsudo.game.GameManager;
import planetsudo.game.GameManager.GameState;
import planetsudo.level.LevelView;
import planetsudo.view.level.LevelDisplayPanel.VideoThreadCommand;
import planetsudo.view.level.levelobjects.AgentPanel;
import planetsudo.view.loading.LevelLoadingPanel;
import planetsudo.view.menu.GameContext;
import planetsudo.view.menu.HelpFrame;
import planetsudo.view.menu.AINetworkTransferMenu;

/**
 *
 * @author divine
 */
public class MainGUI extends javax.swing.JFrame implements PropertyChangeListener {

	public final static String GAME_PANEL="GamePanel";
	public final static String LOADING_PANEL="LoadingPanel";
	public final static String CONFIGURATION_PANEL="ConfigurationPanel";


	private ConfigurationPanel configurationPanel;
	private LevelLoadingPanel levelLoadingPanel;
	private GamePanel gamePanel;


    /** Creates new form MainGui */
    public MainGUI() {
        initComponents();
    }

	/**
	 * Create the frame
	 */
	public MainGUI(GUIController guiController) {
		super("PlanetSudo");
		instance = this;
		this.screenDim = new Dimension(X_DIM, Y_DIM);
		this.guiController = guiController;
		this.setIconImage(new ImageIcon("res/img/PlanetSudoLogoIcon.png").getImage());
	}

	public static MainGUI instance;
	public final static int X_LOCATION = 0;
	public final static int Y_LOCATION = 0;
	public final static int X_DIM = 800;
	public final static int Y_DIM = 600;
	public final static boolean DEFAULT_FULLSCREENMODE = false;

	private Dimension screenDim;
	private GUIController guiController;
	private CardLayout cardLayout;
	private boolean fullscreenMode;

	public void initialize() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				initComponents();
				configurationPanel = new ConfigurationPanel();
				levelLoadingPanel = new LevelLoadingPanel();
				gamePanel = new GamePanel();
				mainPanel.add(configurationPanel, CONFIGURATION_PANEL);
				mainPanel.add(levelLoadingPanel, LOADING_PANEL);
				mainPanel.add(gamePanel, GAME_PANEL);
				((CardLayout) mainPanel.getLayout()).show(mainPanel, CONFIGURATION_PANEL);
				setFullScreenMode(DEFAULT_FULLSCREENMODE);
				guiController.addPropertyChangeListener(instance);
				displayTeamPanelCheckBoxMenuItem.setSelected(gamePanel.isTeamPanelDisplayed());
				updateButtons(GameManager.getInstance().getGameState());
			}
		});
	}

	/**
	 * @return the iPadController
	 */
	public GUIController getGuiController() {
		return guiController;
	}

	public void setFullScreenMode(boolean enabled) {
		fullscreenMode = enabled;
		Logger.info(this, "setFullscreenMode "+fullscreenMode);
		setVisible(false);
		if(fullscreenMode) {
			//setSize(guiController.getVisualFeedbackConfig().getFrameDimension());
			//setSize(screenDim);
			setLocation(0,0);
			if(isDisplayable()) dispose();
			setUndecorated(true);

			GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice device = env.getDefaultScreenDevice();

			try {
				device.setFullScreenWindow(this); // Setzen des FullScreenmodus.
				this.validate();
				//fullscreenModeMenuItem.setText("Leave FullScreen Mode");
			}
			catch(Exception e) {
				Logger.error(this,"no Fullscreen.", e);
				device.setFullScreenWindow(null);
			}
		} else {
			//pack();
			setLocation(X_LOCATION, Y_LOCATION);
			setSize(screenDim);

			if(isDisplayable()) dispose();
			setUndecorated(false);

			GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
			GraphicsDevice device = env.getDefaultScreenDevice();

			try {
					device.setFullScreenWindow(null); // Setzen des FullScreenmodus.
					//fullscreenModeMenuItem.setText("Enter FullScreen Mode");
			}
			catch(Exception e) {
				Logger.error(this, "no Fullscreen.", e);
				device.setFullScreenWindow(null);
			}
		}
		validate();
		setVisible(true);
    }

	public boolean fullscreenModeEnabled() {
		return fullscreenMode;
	}

	/**
	 * Analyse des propertyChangeEvents und setzen der neuen Werte.
	 */
	@Override
	public void propertyChange(PropertyChangeEvent changeEvent) {
		Logger.print(Channel.DEBUG, this, "PropertyChange input: "+changeEvent.getPropertyName());
		try {
			if(changeEvent.getPropertyName().equals(GUIController.GAME_STATE_CHANGE)) {
				updateButtons((GameState) changeEvent.getNewValue());
				if(changeEvent.getNewValue() == GameState.Configuration) {
					gamePanel.setVideoThreadCommand(VideoThreadCommand.Stop);
					((CardLayout) mainPanel.getLayout()).show(mainPanel, CONFIGURATION_PANEL);
				} else if(changeEvent.getNewValue() == GameState.Initialisation) {
					levelLoadingPanel.updateDynamicComponents();
					gamePanel.setVideoThreadCommand(VideoThreadCommand.Stop);
					((CardLayout) mainPanel.getLayout()).show(mainPanel, LOADING_PANEL);
				} else if(changeEvent.getNewValue() == GameState.Running) {
					if(changeEvent.getOldValue() != GameState.Break) {
						gamePanel.updateDynamicComponents();
						((CardLayout) mainPanel.getLayout()).show(mainPanel, GAME_PANEL);
					}
					gamePanel.setVideoThreadCommand(VideoThreadCommand.Start);
				} else if(changeEvent.getNewValue() == GameState.Break) {
					((CardLayout) mainPanel.getLayout()).show(mainPanel, GAME_PANEL);
					gamePanel.setVideoThreadCommand(VideoThreadCommand.Pause);
				}
			} else if(changeEvent.getPropertyName().equals(GUIController.LOADING_STATE_CHANGE)) {
				levelLoadingPanel.setLoadingStateChange((String) changeEvent.getNewValue(), (Integer) changeEvent.getOldValue());
			} else if(changeEvent.getPropertyName().equals(GUIController.LOADING_STEP)) {
				levelLoadingPanel.setLoadingStep((Integer) changeEvent.getNewValue());
			} else {
				Logger.warn(this, "Event ["+changeEvent.getPropertyName()+"] is an bad property change event!");
			}
		}
		catch (Exception e) {
			Logger.print(Channel.DEBUG, this, "Exception from PropertyChange");
		}
	}

	public void updateButtons(GameState state) {
		switch(state) {
			case Configuration:
				startPauseMenuItem.setText("Starte Spiel");
				startPauseMenuItem.setEnabled(true);
				stopMenuItem.setEnabled(false);
				finalCalculationMenuItem.setEnabled(false);
				break;
			case Initialisation:
				startPauseMenuItem.setText("Starte Spiel");
				startPauseMenuItem.setEnabled(false);
				stopMenuItem.setEnabled(false);
				finalCalculationMenuItem.setEnabled(false);
				break;
			case Running:
				startPauseMenuItem.setText("Pause");
				startPauseMenuItem.setEnabled(true);
				stopMenuItem.setEnabled(true);
				finalCalculationMenuItem.setEnabled(true);
				break;
			case Break:
				startPauseMenuItem.setText("Weiter");
				startPauseMenuItem.setEnabled(true);
				stopMenuItem.setEnabled(true);
				finalCalculationMenuItem.setEnabled(false);
				break;
		}
		if(finished) {
			startPauseMenuItem.setEnabled(false);
			stopMenuItem.setEnabled(true);
			finalCalculationMenuItem.setEnabled(false);
			stopMenuItem.setText("Zurück zum Hauptmenü");

		} else {
			stopMenuItem.setText("Stop");
		}
		finished = false;
	}

	private boolean finished;
	private void finalizeGame() {
		finished = true;
		GameManager.getInstance().switchGameState(GameState.Break);
		gamePanel.displayEndCalculation();
	}

	public void showLoadingPanel() {
		Logger.info(this, "ShowLoadingPanel");
		((CardLayout) mainPanel.getLayout()).show(mainPanel, LOADING_PANEL);
	}

	public static MainGUI getInstance() {
		return instance;
	}

	public ConfigurationPanel getConfigurationPanel() {
		return configurationPanel;
	}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jMenuItem1 = new javax.swing.JMenuItem();
        mainPanel = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        finalCalculationMenuItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        startPauseMenuItem = new javax.swing.JMenuItem();
        stopMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        displayTeamPanelCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem3 = new javax.swing.JCheckBoxMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jCheckBoxMenuItem2 = new javax.swing.JCheckBoxMenuItem();
        helpMenu = new javax.swing.JMenu();
        contentsMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PlanetSudo - The Force Of Life");

        mainPanel.setLayout(new java.awt.CardLayout());

        fileMenu.setText("Spiel");

        finalCalculationMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, 0));
        finalCalculationMenuItem.setText("Endabrechnung");
        finalCalculationMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finalCalculationMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(finalCalculationMenuItem);
        fileMenu.add(jSeparator3);

        startPauseMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_SPACE, 0));
        startPauseMenuItem.setText("Start");
        startPauseMenuItem.setEnabled(false);
        startPauseMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startPauseMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(startPauseMenuItem);

        stopMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0));
        stopMenuItem.setText("Stop");
        stopMenuItem.setEnabled(false);
        stopMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(stopMenuItem);
        fileMenu.add(jSeparator2);

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        exitMenuItem.setText("Beenden");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setText("Ansicht");

        jMenu2.setText("Spiel");

        displayTeamPanelCheckBoxMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, 0));
        displayTeamPanelCheckBoxMenuItem.setSelected(true);
        displayTeamPanelCheckBoxMenuItem.setText("Teamanzeige");
        displayTeamPanelCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                displayTeamPanelCheckBoxMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(displayTeamPanelCheckBoxMenuItem);

        jCheckBoxMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, 0));
        jCheckBoxMenuItem3.setText("AgentenStatus");
        jCheckBoxMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jCheckBoxMenuItem3);

        editMenu.add(jMenu2);
        editMenu.add(jSeparator1);

        jCheckBoxMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F11, 0));
        jCheckBoxMenuItem1.setText("Vollbild");
        jCheckBoxMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem1ActionPerformed(evt);
            }
        });
        editMenu.add(jCheckBoxMenuItem1);

        menuBar.add(editMenu);

        jMenu3.setText("Netzwerk");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
        jMenuItem2.setText("Sende KI");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem2);

        menuBar.add(jMenu3);

        jMenu1.setText("Einstellungen");

        jCheckBoxMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jCheckBoxMenuItem2.setText("DebugModus");
        jCheckBoxMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jCheckBoxMenuItem2);

        menuBar.add(jMenu1);

        helpMenu.setText("Info");

        contentsMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        contentsMenuItem.setText("Spielablauf");
        contentsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contentsMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(contentsMenuItem);

        aboutMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F2, 0));
        aboutMenuItem.setText("Über PlanetSudo");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

	private void jCheckBoxMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem1ActionPerformed
		setFullScreenMode(jCheckBoxMenuItem1.isSelected());
	}//GEN-LAST:event_jCheckBoxMenuItem1ActionPerformed

	private void startPauseMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startPauseMenuItemActionPerformed
		switch(GameManager.getInstance().getGameState()) {
			case Configuration:
				GameManager.getInstance().startGame();
				break;
			case Running:
				GameManager.getInstance().switchGameState(GameState.Break);
				break;
			case Break:
				GameManager.getInstance().switchGameState(GameState.Running);
				break;
		}
	}//GEN-LAST:event_startPauseMenuItemActionPerformed

	private void jCheckBoxMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem2ActionPerformed
		Logger.setDebugEnable(jCheckBoxMenuItem2.isSelected());
	}//GEN-LAST:event_jCheckBoxMenuItem2ActionPerformed

	private void displayTeamPanelCheckBoxMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_displayTeamPanelCheckBoxMenuItemActionPerformed
		gamePanel.displayTeamPanel(displayTeamPanelCheckBoxMenuItem.isSelected());
	}//GEN-LAST:event_displayTeamPanelCheckBoxMenuItemActionPerformed

	private void jCheckBoxMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem3ActionPerformed
		AgentPanel.showStateLabel = jCheckBoxMenuItem3.isSelected();
	}//GEN-LAST:event_jCheckBoxMenuItem3ActionPerformed

	private void stopMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopMenuItemActionPerformed
		GameManager.getInstance().switchGameState(GameState.Configuration);
	}//GEN-LAST:event_stopMenuItemActionPerformed

	private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
		HelpFrame.display();
	}//GEN-LAST:event_aboutMenuItemActionPerformed

	private void contentsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contentsMenuItemActionPerformed
		GameContext.display();
	}//GEN-LAST:event_contentsMenuItemActionPerformed

	private void finalCalculationMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finalCalculationMenuItemActionPerformed
		finalizeGame();
	}//GEN-LAST:event_finalCalculationMenuItemActionPerformed

	private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
		AINetworkTransferMenu.display();
	}//GEN-LAST:event_jMenuItem2ActionPerformed

	public static LevelView levelView;
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

			@Override
            public void run() {
                new MainGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem contentsMenuItem;
    private javax.swing.JCheckBoxMenuItem displayTeamPanelCheckBoxMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem finalCalculationMenuItem;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem2;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem3;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem startPauseMenuItem;
    private javax.swing.JMenuItem stopMenuItem;
    // End of variables declaration//GEN-END:variables

}
