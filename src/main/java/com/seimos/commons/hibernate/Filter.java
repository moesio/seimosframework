package com.seimos.commons.hibernate;

import org.hibernate.sql.JoinType;

public class Filter {

	private String attribute;
	private Condition condition;
	private Object value;
	private Function function;
	private Filter[] filters;
	private Projection projection = Projection.YES;
	private Order order;
	private JoinType joinType;
	private Distinct distinct;
	private Wildcard wildcard = Wildcard.NO;

	// TODO Convert it to hibernate Restrictions
	public static enum Condition {
		// TODO some of Conditions are not implemented yet. It would be
		// necessary extend Restrictions for some specific ones
		EQUALS {
			@Override
			public String toString() {
				return " = ";
			}
		},
		NOT_EQUALS {
			@Override
			public String toString() {
				return " <> ";
			}
		},
		STARTS_WITH {
			@Override
			public String toString() {
				return " ilike %";
			}
		},
		NOT_STARTS_WITH {
			@Override
			public String toString() {
				return " not ilike %";
			}
		},
		ENDS_WITH {
			@Override
			public String toString() {
				return " like ?%";
			}
		},
		NOT_ENDS_WITH {
			@Override
			public String toString() {
				return " not like ?%";
			}
		},
		EQUALS_CASE_INSENSITIVE {
			@Override
			public String toString() {
				return " ilike ";
			}
		},
		STARTS_WITH_CASE_SENSITIVE {
			@Override
			public String toString() {
				return " like %";
			}
		},
		NOT_STARTS_WITH_CASE_SENSITIVE {
			@Override
			public String toString() {
				return " not like %";
			}
		},
		ENDS_WITH_CASE_SENSITIVE {
			@Override
			public String toString() {
				return " like ?%";
			}
		},
		NOT_ENDS_WITH_CASE_SENSITIVE {
			@Override
			public String toString() {
				return "not like ?%";
			}
		},
		GREATER {
			@Override
			public String toString() {
				return " > ";
			}
		},
		GREATER_OR_EQUALS {
			@Override
			public String toString() {
				return " >= ";
			}
		},
		LESS {
			@Override
			public String toString() {
				return " < ";
			}
		},
		LESS_OR_EQUALS {
			@Override
			public String toString() {
				return " <= ";
			}
		},
		NULL {
			@Override
			public String toString() {
				return " is null ";
			}
		},
		NOT_NULL {
			@Override
			public String toString() {
				return " is not null ";
			}
		},
		BETWEEN {
			@Override
			public String toString() {
				return " between ";
			}
		},
		OR {
			@Override
			public String toString() {
				return " or ";
			}
		},
		AND {
			@Override
			public String toString() {
				return " and ";
			}
		},
		IN {
			@Override
			public String toString() {
				return " in (?) ";
			}
		},
		NOT_IN {
			@Override
			public String toString() {
				return " not in (?) ";
			}
		},
		EQ_PROPERTY {
			@Override
			public String toString() {
				return " = ? ";
			}
		},
		LESS_THAN_PROPERTY {
			@Override
			public String toString() {
				return " <= ? ";
			}
		},
		LESS_OR_EQUALS_THAN_PROPERTY {
			@Override
			public String toString() {
				return " <= ? ";
			}
		},
		GREATER_THAN_PROPERTY {
			@Override
			public String toString() {
				return " > ? ";
			}
		},
		GREATER_OR_EQUALS_THAN_PROPERTY {
			@Override
			public String toString() {
				return " >= ? ";
			}
		};

		public abstract String toString();
	}

	public static enum Function {
		MAX {
			@Override
			public String toString() {
				return " max(?) ";
			}
		},
		MIN {
			@Override
			public String toString() {
				return " min(?) ";
			}
		},
		SUM {
			@Override
			public String toString() {
				return " sum(?) ";
			}
		},
		AVG {
			@Override
			public String toString() {
				return " avg(?) ";
			}
		},
		COUNT {
			@Override
			public String toString() {
				return " count(?) ";
			}
		},
		COUNT_DISTINCT {
			@Override
			public String toString() {
				return " distinct count(?) ";
			}
		};

		public abstract String toString();
	}

	public static enum Projection {
		YES, NO
	}

	//	public static enum JoinType {
	//		INNER_JOIN {
	//			public int getValue() {
	//				return JoinFragment.INNER_JOIN;
	//			}
	//		},
	//		LEFT_OUTER_JOIN {
	//			public int getValue() {
	//				return JoinFragment.LEFT_OUTER_JOIN;
	//			}
	//		},
	//		RIGHT_OUTER_JOIN {
	//			public int getValue() {
	//				return JoinFragment.RIGHT_OUTER_JOIN;
	//			}
	//		},
	//		FULL_JOIN {
	//			public int getValue() {
	//				return JoinFragment.FULL_JOIN;
	//			}
	//		};
	//
	//		public abstract int getValue();
	//	}

	public static enum Order {
		ASC, DESC;
	}

	public static enum Distinct {
		YES, NO;
	}

	public static enum Wildcard {
		YES, NO;
	}

	public Filter() throws Exception {
		throw new Exception("O Filter deve ter pelo menos uma definição do atributo. Use Filter(String attribute)");
	}

	public Filter(String attribute) {
		setAttribute(attribute);
		if (attribute.contains("*")) {
			setWildcard(Wildcard.YES);
		}
	}

	public Filter(String attribute, Object value) {
		setAttribute(attribute);
		setValue(value);
	}

	public Filter(String attribute, Object value, Projection projection) {
		setAttribute(attribute);
		setValue(value);
		setProjection(projection);
	}

	public Filter(String attribute, Object value, Condition condition) {
		setAttribute(attribute);
		setValue(value);
		setCondition(condition);
	}

	public Filter(String attribute, Object value, Condition condition, Projection projection) {
		setAttribute(attribute);
		setValue(value);
		setCondition(condition);
		setProjection(projection);
	}

	public Filter(String attribute, Condition condition) throws Exception {
		if (!condition.equals(Condition.NULL) && !condition.equals(Condition.NOT_NULL)) {
			throw new Exception(
					"Apenas " + Condition.NULL + " e " + Condition.NOT_NULL + " são aceitas sem parâmetros.\n" //
							+ "Use Filter(String, Object, Condition)");
		}

		setAttribute(attribute);
		setCondition(condition);
	}

	public Filter(String attribute, Function function) {
		setAttribute(attribute);
		setFunction(function);
	}

	public Filter(String attribute, Object value, Function function) {
		setAttribute(attribute);
		setValue(value);
		setFunction(function);
	}

	public Filter(String attribute, Object value, Function function, Projection projection) {
		setAttribute(attribute);
		setValue(value);
		setFunction(function);
		setProjection(projection);
	}

	public Filter(String attribute, Order orders) {
		setAttribute(attribute);
		setOrder(orders);
	}

	public Filter(Condition condition, Filter... filters) {
		setCondition(condition);
		setFilters(filters);
	}

	public Filter(String attribute, JoinType joinType) {
		setAttribute(attribute);
		setJoinType(joinType);
	}

	public Filter(String attribute, Condition condition, JoinType joinType) {
		setAttribute(attribute);
		setCondition(condition);
		setJoinType(joinType);
	}

	public Filter(String attribute, Object value, Condition condition, JoinType joinType) {
		setAttribute(attribute);
		setValue(value);
		setCondition(condition);
		setJoinType(joinType);
	}

	public Filter(Distinct distinct) {
		this.distinct = distinct;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	public Filter[] getFilters() {
		return filters;
	}

	public void setFilters(Filter[] filters) {
		this.filters = filters;
	}

	public Projection getProjection() {
		return projection;
	}

	public void setProjection(Projection projection) {
		this.projection = projection;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public JoinType getJoinType() {
		return joinType;
	}

	public void setJoinType(JoinType joinType) {
		this.joinType = joinType;
	}

	public Distinct getDistinct() {
		return distinct;
	}

	public void setDistinct(Distinct distinct) {
		this.distinct = distinct;
	}

	public Wildcard getWildcard() {
		return wildcard;
	}

	public void setWildcard(Wildcard wildcard) {
		this.wildcard = wildcard;
	}

	@Override
	public String toString() {
		if (filters != null) {
			StringBuilder result = new StringBuilder(" and (");
			for (Filter filter : filters) {
				result.append(filter.toString());
				result.append(condition);
			}
			result.append(" )");
			return result.toString();
		} else if (function != null) {
			return function.toString().replace("?", attribute);
		} else {
			return attribute + (condition == null ? "" : condition) + (value == null ? "" : value);
		}
		//				+ ", projection=" + projection + ", order=" + order + ", joinType=" + joinType + ", distinct=" + distinct + ", wildcard=" + wildcard + "]";
	}
}
