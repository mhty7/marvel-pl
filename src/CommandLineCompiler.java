import java.io.*;
import java.util.Scanner;
import logger.Logger;
import parser.Parser;
import program.Program;
import interpreter.*;

public class CommandLineCompiler {
	public static void main(String[] args) {
		if (args.length < 1)
			outputUsageAndExit();
		Logger logger = new Logger(System.out, System.err);
		try {
			InputStream in = new FileInputStream(new File(args[0]));
			logger.message("=====Original  file=====");
			logger.message(readFile(args[0]));

			Parser parser = new Parser(in);
			Program program = parser.parse(logger);
			
			if (program != null) {
				String javaCode = program.toJavaCode();
				logger.message("====Java translation====");
				logger.message(javaCode);
				
				logger.message("======== Output ========");
				Interpreter interpreter = new JavaInterpreter();
				interpreter.evaluate(javaCode);
			}
		} catch (Exception ex) {
			logger.exception(ex);
		}
	}

	private static String readFile(String path) throws IOException {
		File file = new File(path);
		return new Scanner(file).useDelimiter("\\Z").next();
	}
	
	private static void outputUsageAndExit() {
		System.err.println("Usage: java Compiler filepath");
		System.exit(-1);
	}
}
