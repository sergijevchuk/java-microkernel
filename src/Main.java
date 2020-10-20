import java.util.*;
import managers.*;
import proc.*;
import kernel.*;

public class Main
{
	public static void main(String[] args)
	{
		if (args.length == 1 && args[0].equalsIgnoreCase("isDebug")) {
			KernelConfig.isDebug = true;
		}
		new Kernel().run();
	}
}
