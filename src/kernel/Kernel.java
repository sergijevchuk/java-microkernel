package kernel;
import kernel.utils.*;
import managers.*;
import proc.*;
import managers.commands.*;
import signal.*;

public class Kernel extends SignalReceiver
{
	public void run() {
		printk.printk("Registration uncaught exception handler....");
		Thread.setDefaultUncaughtExceptionHandler(new ErrorC());
		printk.printk("Loading process manager...");
		ProcessManager.init();
		printk.printk("Loading command manager...");
		CommandManager.init();
		printk.printk("Registring commands...");
		CommandManager.add("shutdown",new ShutdownCommand());
		CommandManager.add("newPanic",new PanicCommand());
		CommandManager.add("kill",new KillCommand());
		printk.printk("Loading base ipc manager....");
		SignalManager.init();
		SignalManager.add("kernel",this);
		printk.printk("Loading init...");
		ProcessManager.addApp("init",new Init());
		ProcessManager.waitForAll();
		if (KernelConfig.isShuttingDown) {
			KernelConfig.isDebug = true;
			baseShuttdown();
			printk.printk("Shutting down is detected!");
		} else if (!KernelConfig.isShell) {
			new Panic().panic("Init died!");
		}
	}
	private void baseShuttdown() {
		SignalManager.shutdown();
		CommandManager.shutdown();
		ProcessManager.shutdown();
	}

	@Override
	public void onReceive(String sigName, String[] msg)
	{
		// TODO: Implement this method
		if (sigName.equalsIgnoreCase("shutdown")) {
			KernelConfig.isDebug = true;
			printk.printk("Shutting down signal received!");
			baseShuttdown();
			try {
				Thread.currentThread().join();
				System.out.println(Thread.currentThread().getName());
			} catch (InterruptedException e) {
				new ErrorC().uncaughtException(Thread.currentThread(),new Throwable("InterruptedException"));
			}
		}
	}
	private class ErrorC implements Thread.UncaughtExceptionHandler
	{

		@Override
		public void uncaughtException(Thread p1, Throwable p2)
		{
			new Panic().panic("Error in thread: " + p1.getName() + " error msg: " + p2.toString());
		}

		
	}
}
