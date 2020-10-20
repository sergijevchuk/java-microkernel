package kernel.utils;

import kernel.*;
import managers.*;

public class Panic
{
	public void panic(CharSequence s) {
		KernelConfig.isDebug = true;
		baseShuttdown();
		printk.printk("Kernel panic: " + s);
		for (;;) {}
	}
	private void baseShuttdown() {
		CommandManager.shutdown();
		ProcessManager.shutdown();
	}
	
}
