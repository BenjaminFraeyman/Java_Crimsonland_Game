package org.ibcn.gso.project.config;

public class GameConfig {

	private GraphicsConfig graphics;
	private ControlsConfig controls;

	public GameConfig() {
		this.setGraphics(new GraphicsConfig());
		this.setControls(new ControlsConfig());
	}

	public GraphicsConfig getGraphics() {
		return graphics;
	}

	public void setGraphics(GraphicsConfig graphics) {
		this.graphics = graphics;
	}

	public ControlsConfig getControls() {
		return controls;
	}

	public void setControls(ControlsConfig controls) {
		this.controls = controls;
	}
}
