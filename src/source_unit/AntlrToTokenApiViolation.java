package source_unit;

import org.antlr.v4.runtime.Token;

import antlr.ExprBaseVisitor;
import antlr.ExprParser.FunctionDefinitionContext;

public class AntlrToTokenApiViolation extends ExprBaseVisitor<Contract_Components> {

	private static String TRANSFER_FROM = "transferFrom";
	private static String APPROVE = "approve";
	private static String TRANSFER = "transfer";

	// violations
	private static String REQUIRE = "require";
	private static String REVERT = "transfer";
	private static String THROW = "throw";
	private static String ASSERT = "assert";

	public String erc20_violation_warning;

	public AntlrToTokenApiViolation(String erc20_violation_warning) {
		this.erc20_violation_warning = erc20_violation_warning;
	}

	@Override
	public Contract_Components visitFunctionDefinition(FunctionDefinitionContext ctx) {
		String name = ctx.getChild(1).getText();

		if (name.equals(TRANSFER_FROM) || name.equals(APPROVE) || name.equals(TRANSFER)) {
			for (int i = 0; i < ctx.getChildCount(); i++) {
				if (ctx.getChild(i).getText().contains(REQUIRE) || ctx.getChild(i).getText().contains(REVERT)
						|| ctx.getChild(i).getText().contains(THROW) || ctx.getChild(i).getText().contains(ASSERT)) {
					Token sttoken = ctx.getStart();
					int line = sttoken.getLine();
					System.out.println(
							"[Developmental issues] detected in function at line no: " + line + ", ERC20 Violation");
				}

			}
		}

		return null;

	}

}
