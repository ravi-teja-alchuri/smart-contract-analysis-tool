package source_unit;

import org.antlr.v4.runtime.Token;

import antlr.ExprBaseVisitor;
import antlr.ExprParser.ElementaryTypeNameContext;

public class AntlrToByteDef extends ExprBaseVisitor<Type_Name_Expressions> {
	public String byte_Def_Warning;

	public AntlrToByteDef(String byte_Def_Warning) {
		this.byte_Def_Warning = byte_Def_Warning;
	}

	@Override
	public Type_Name_Expressions visitElementaryTypeName(ElementaryTypeNameContext ctx) {
		String byte_Def = ctx.getChild(0).getText();
		Token sttoken = ctx.getStart();
		int line = sttoken.getLine();

		if (byte_Def.equals("byte")) {
			System.out.println("[Operational issues] detected at line no: " + line + ", use bytes instead");
		}

		return new Byte_Def(byte_Def);
	}

}
