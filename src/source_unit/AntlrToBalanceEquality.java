package source_unit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.antlr.v4.runtime.Token;

import antlr.ExprBaseVisitor;
import antlr.ExprParser.ExpressionContext;

public class AntlrToBalanceEquality extends ExprBaseVisitor<Type_Name_Expressions> {
	public String balance_equality_warning;

	Pattern pattern_statement = Pattern.compile("^.*balance.*$");
	Pattern pattern_expression_condition = Pattern.compile("^==|!=$");

	public AntlrToBalanceEquality(String balance_equality_warning) {
		this.balance_equality_warning = balance_equality_warning;
	}

	@Override
	public Type_Name_Expressions visitExpression(ExpressionContext ctx) {
		Token sttoken = ctx.getStart();
		int line = sttoken.getLine();

		if (ctx.getChildCount() > 2) {
			String statement = ctx.getChild(0).getText();
			String expression = ctx.getChild(1).getText();

			Matcher expression_condition_matcher = pattern_expression_condition.matcher(expression);
			Matcher pattern_statement_matcher = pattern_statement.matcher(statement);

			if (expression_condition_matcher.find() && pattern_statement_matcher.find()) {
				System.out.println("[Security issues] detected at line no: " + line
						+ ", Balance equality. Avoid checking for strict balance equality:");
			}

		}

		String balance_equality_expression = ctx.getChild(0).getText();

		return new Balance_Equality(balance_equality_expression);
	}

}
