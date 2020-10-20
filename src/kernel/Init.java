package kernel;
import kernel.utils.*;
import managers.*;
import proc.*;

public class Init extends MyRunnable
{

	@Override
	public void run()
	{
		printk.printk("init: Loading....");
		System.out.println("init: No nothing here starting shell...");
		KernelConfig.isShell = true;
		ProcessManager.addApp("shell",new Shell());
		System.out.println("init: Waiting for shell process...");
		ProcessManager.getProcess("shell").waitForPrc();
		System.out.println("init: Starting shutdown bacause shell died!");
		KernelConfig.isShell = false;
		KernelConfig.isShuttingDown = true;
		//ProcessManager.kill("init");
		//ProcessManager.waitForAll();
	}

	@Override
	public void onTerminated()
	{
		// TODO: Implement this method
		System.out.println("init: terminated!");
	}
	
}
