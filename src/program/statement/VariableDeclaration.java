package program.statement;

import parser.ParseException;
import program.DataType;
import program.Expression;
import program.Statement;
import program.SymbolTable;
import program.expression.VariableReference;

public class VariableDeclaration extends Statement {
    
	private DataType type;
	private String name;
	private Expression initialValue;

	public VariableDeclaration(DataType type, String name, Expression initialValue) {
		this.type = type;
		this.name = name;
		this.initialValue = initialValue;
	}

	@Override
	public void analyze(SymbolTable table) throws ParseException {
		table.addVariable(this);
		if (initialValue != null)
			new AssignmentStatement(new VariableReference(name), initialValue).analyze(table);
	}
	
	@Override
	public String getJavaDeclarations() {
		String value = (initialValue == null) ? type.getInitialValue() : initialValue.toJavaCode();
		return String.format("private static %s %s = %s;\r\n", type.toJavaCode(), name, value);		
	}

	@Override
	public String getMainJavaCode(int indentLevel) {
		return "";
	}
	
	public DataType getType() {
		return type;
	}
	
	public String getName() {
		return name;
	}
}