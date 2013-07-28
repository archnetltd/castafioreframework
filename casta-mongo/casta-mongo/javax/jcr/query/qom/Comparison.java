/*
 * Copyright 2009 Day Management AG, Switzerland. All rights reserved.
 */
package javax.jcr.query.qom;

/**
 * Filters node-tuples based on the outcome of a binary operation.
 * <p>
 * For any comparison, {@link #getOperand2 operand2} always evaluates to a
 * scalar value.  In contrast, {@link #getOperand1 operand1} may evaluate to an
 * array of values (for example, the value of a multi-valued property), in which
 * case the comparison is separately performed for each element of the array,
 * and the <code>Comparison</code> constraint is satisfied as a whole if the
 * comparison against <i>any</i> element of the array is satisfied.
 * <p>
 * If {@link #getOperand1 operand1} and {@link #getOperand2 operand2} evaluate
 * to values of different property types, the value of {@link #getOperand2
 * operand2} is converted to the property type of the value of {@link
 * #getOperand1 operand1}.  If the type conversion fails, the query is invalid.
 * <p>
 * If {@link #getOperator operator} is not supported for the property type of
 * {@link #getOperand1 operand1}, the query is invalid.
 * <p>
 * If {@link #getOperand1 operand1} evaluates to null (for example, if the
 * operand evaluates the value of a property which does not exist), the
 * constraint is not satisfied.
 * <p>
 * The <code>JCR_OPERATOR_EQUAL_TO</code> operator is satisfied <i>only if</i>
 * the value of {@link #getOperand1 operand1} equals the value of {@link
 * #getOperand2 operand2}.
 * <p>
 * The <code>JCR_OPERATOR_NOT_EQUAL_TO</code> operator is satisfied
 * <i>unless</i> the value of {@link #getOperand1 operand1} equals the value of
 * {@link #getOperand2 operand2}.
 * <p>
 * The <code>JCR_OPERATOR_LESSS_THAN</code> operator is satisfied <i>only if</i>
 * the value of {@link #getOperand1 operand1} is ordered <i>before</i> the value
 * of {@link #getOperand2 operand2}.
 * <p>
 * The <code>JCR_OPERATOR_LESS_THAN_OR_EQUAL_TO</code> operator is satisfied
 * <i>unless</i> the value of {@link #getOperand1 operand1} is ordered
 * <i>after</i> the value of {@link #getOperand2 operand2}.
 * <p>
 * The <code>JCR_OPERATOR_GREATER_THAN</code> operator is satisfied <i>only
 * if</i> the value of {@link #getOperand1 operand1} is ordered <i>after</i> the
 * value of {@link #getOperand2 operand2}.
 * <p>
 * The <code>JCR_OPERATOR_GREATER_THAN_OR_EQUAL_TO</code> operator is satisfied
 * <i>unless</i> the value of {@link #getOperand1 operand1} is ordered
 * <i>before</i> the value of {@link #getOperand2 operand2}.
 * <p>
 * The <code>JCR_OPERATOR_LIKE</code> operator is satisfied only if the value of
 * {@link #getOperand1 operand1} <i>matches</i> the pattern specified by the
 * value of {@link #getOperand2 operand2}, where in the pattern: <ul> <li>the
 * character "<code>%</code>" matches zero or more characters, and</li> <li>the
 * character "<code>_</code>" (underscore) matches exactly one character,
 * and</li> <li>the string "<code>\<i>x</i></code>" matches the character
 * "<code><i>x</i></code>", and</li> <li>all other characters match
 * themselves.</li> </ul>
 *
 * @since JCR 2.0
 */
public interface Comparison
        extends Constraint {
    /**
     * Gets the first operand.
     *
     * @return the operand; non-null
     */
    public DynamicOperand getOperand1();

    /**
     * Gets the operator.
     *
     * @return either <ul> <li>{@link QueryObjectModelConstants#JCR_OPERATOR_EQUAL_TO},</li>
     *         <li>{@link QueryObjectModelConstants#JCR_OPERATOR_NOT_EQUAL_TO},</li>
     *         <li>{@link QueryObjectModelConstants#JCR_OPERATOR_LESS_THAN},</li>
     *         <li>{@link QueryObjectModelConstants#JCR_OPERATOR_LESS_THAN_OR_EQUAL_TO},</li>
     *         <li>{@link QueryObjectModelConstants#JCR_OPERATOR_GREATER_THAN},</li>
     *         <li>{@link QueryObjectModelConstants#JCR_OPERATOR_GREATER_THAN_OR_EQUAL_TO},
     *         or</li> <li>{@link QueryObjectModelConstants#JCR_OPERATOR_LIKE}</li>
     *         </ul>
     */
    public String getOperator();

    /**
     * Gets the second operand.
     *
     * @return the operand; non-null
     */
    public StaticOperand getOperand2();
}
