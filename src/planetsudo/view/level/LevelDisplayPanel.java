/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LevelDisplayPanel.java
 *
 * Created on Jun 19, 2010, 1:15:02 PM
 */

package planetsudo.view.level;

import logging.Logger;
import planetsudo.level.AbstractLevel;
import view.components.draw.ResourceDisplayPanel;

/**
 *
 * @author divine
 */
public class LevelDisplayPanel extends ResourceDisplayPanel<LevelPanel> implements Runnable {

	public enum VideoThreadCommand {Start, Stop, Pause, Resume};
	public Thread videoThread;
	private boolean isRunning, stop;

    /** Creates new form LevelDisplayPanel */
    public LevelDisplayPanel() {
        initComponents();
		isRunning = false;
    }
	
	public void setLevel(AbstractLevel level) {
		setVisibleResourcePanel(new LevelPanel(level, this));
	}

	public void displayLevelObjects() {
		if(visibleResourcePanel != null) {
			visibleResourcePanel.loadLevelObjects();
		}
	}
	
	public synchronized  void setVideoThreadCommand(VideoThreadCommand command) {
		switch(command) {
			case Start:
			case Resume:
				if(!isRunning) {
					isRunning = true;
					stop = false;
					videoThread = new Thread(this, "VideoVideoThread");
					videoThread.setPriority(Thread.NORM_PRIORITY+2);
					videoThread.start();
				} else {
					Logger.debug(this, "VideoThread allready started.");
				}
				break;
			case Stop:
			case Pause:
				if(isRunning) {
					stop = true;
				} else {
					Logger.debug(this, "VideoThread allready stopped.");
				}
				break;
		}
	}

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setOpaque(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

	@Override
	public void run() {
		Logger.info(this, "VideoThread started.");
		while(!stop) {
			if(visibleResourcePanel != null) {
				repaint();
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException ex) {
				Logger.warn(this, "VideoThread interruped!", ex);
			}
		}
		isRunning = false;
		Logger.info(this, "VideoThread stopped.");
	}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}