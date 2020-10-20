package kernel;
import java.util.*;
import managers.*;
import proc.*;
import kernel.utils.*;

public class Shell extends MyRunnable
{

	@Override
	public void run()
	{
		while (true) {
		System.out.print("Enter a command:");
		String c = new Scanner(System.in).nextLine();
		if (!c.equalsIgnoreCase("exit")) {
		ProcessManager.addApp(c,new e(c,new String[] {}));
		} else {
			KernelConfig.isShell = false;
			break;
		}
		}
	}

	private class e extends MyRunnable
	{
		private String name;
		private String[] args;
		public e(String name,String[] args) {
			this.name = name;
			this.args = args;
		}
		@Override
		public void run()
		{
			CommandManager.exec(name,args);
		}

		@Override
		public void onTerminated()
		{
			// TODO: Implement this method
			System.out.println("init: Terminated!");
			new Panic().panic("init killed!");
		}

		
	}
}
