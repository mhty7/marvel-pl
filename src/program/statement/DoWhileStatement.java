package program.statement;

import parser.ParseException;
import program.Block;
import program.Expression;
import program.Statement;
import program.SymbolTable;

public class DoWhileStatement extends Statement{
	
	private Expression condition;
    private Block body;
    
	public DoWhileStatement(Expression condition, Block body) {
		this.condition = condition;
        this.body = body;
		
	}

	@Override
    public void analyze(SymbolTable table) throws ParseException {
        condition.analyze(table);
        body.analyze(table);
    }
	
	protected String conditionAsJavaCode() {
		return condition.toConditionalJavaCode();
	}

    @Override
    public String getMainJavaCode(int indentLevel) {
        return String.format("do {\r\n%s\t} while (%s)", 
        		body.toJavaCode(indentLevel + 1), conditionAsJavaCode());
    }

}
