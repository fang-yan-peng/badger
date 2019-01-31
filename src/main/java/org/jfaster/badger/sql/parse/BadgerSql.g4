grammar BadgerSql;


@rulecatch { 
catch (RecognitionException e) {
  throw e;
 }
}

@parser::header {
package org.jfaster.badger.sql.parse;

import java.util.ArrayList;
import java.util.List;

import org.jfaster.badger.query.sql.SqlTree;
}

@lexer::header {
package org.jfaster.badger.sql.parse;

import java.util.ArrayList;
import java.util.List;
}


@members {
  StringBuilder sqlBuilder = new StringBuilder(128);
  List<Object> values = new ArrayList<Object>(16);  
  List<String> fieldList = new ArrayList<String>(16);
  List<Object> condition = new ArrayList<Object>(16);
  int indexLevel = 1;

  StringBuilder updateBuilder = new StringBuilder(64);
  List<Object> updateValues = new ArrayList<Object>(16);
  List<String> updateFieldList = new ArrayList<String>(16);

  public String getSql(){
    return sqlBuilder.toString();
  }
  
  public List<String> getField(){
    return fieldList;
  }
  public List<Object> getSqlTree(){
  	return condition;
  }
    
  public List<Object> getValues(){
    return values;
  }

  public String getUpdateStatement(){
      if (updateBuilder.length() > 0) {
        updateBuilder.deleteCharAt(updateBuilder.length() - 1);
      }
      return updateBuilder.toString();
   }

   public List<String> getUpdateField(){
      return updateFieldList;
   }

   public List<Object> getUpdateValues(){
      return updateValues;
   }
	
}


logicalExpression 
	:	relationalExpression( logicalOperate logicalExpression ) *
	| leftBracket logicalExpression rightBracket ( logicalOperate logicalExpression ) *
	;
	

relationalExpression
	:
		{int count = 0;}
		fieldName = ID IN '(' atom {count++;} (',' atom {count++;})* ')'
		{
			condition.add(new SqlTree($fieldName.text,"in",indexLevel,count));
			sqlBuilder.append($fieldName.text)
			.append(" IN (?");
			fieldList.add($fieldName.text);
			for(; count > 1; count --){
				sqlBuilder.append(", ?");
				fieldList.add($fieldName.text);
			}
			sqlBuilder.append(")");
		}
	|	fieldName = ID  BA atom AD atom
		{
			{sqlBuilder.append($fieldName.text).append(" BETWEEN ? AND ? ");
			fieldList.add($fieldName.text);
			fieldList.add($fieldName.text);
			condition.add(new SqlTree($fieldName.text,"between and",indexLevel,2));}
		}
	|	fieldName = ID  LK atom
		{
			{sqlBuilder.append($fieldName.text).append(" LIKE ?");
			fieldList.add($fieldName.text);
			condition.add(new SqlTree($fieldName.text,"like",indexLevel,1));}
		}
	|	fieldName = ID op = operate atom
		{
			sqlBuilder.append($fieldName.text)
			.append(' ')
			.append($op.text)
			.append(' ')
			.append("?");
			fieldList.add($fieldName.text);
			condition.add(new SqlTree($fieldName.text,$op.text,indexLevel,1));
		}
	|	fieldName = INT op = operate value = aton
		{
			sqlBuilder.append($fieldName.text)
			.append(' ')
			.append($op.text)
			.append(' ')
			.append($value.text);
		}
	|	fieldName = ID IS NUL
		{
			sqlBuilder.append($fieldName.text)
			.append(' ')
			.append("IS NULL");
		}
	|	fieldName = ID IS NOT NUL
		{
			sqlBuilder.append($fieldName.text)
			.append(' ')
			.append("IS NOT NULL");
		}

	;

updateExpression
    : updateField(',' updateField)*
    ;

updateField
    : fieldName = ID '=' updateAtom
    {
        updateBuilder
        .append($fieldName.text)
        .append(" = ")
        .append("?")
        .append(",");
        updateFieldList.add($fieldName.text);
    }
    ;

logicalOperate
	:	AD {sqlBuilder.append(" AND "); condition.add("and");} 
	|	OD {sqlBuilder.append(" OR "); condition.add("or");} 
	;

leftBracket 
	:	'(' {sqlBuilder.append("("); indexLevel++;} 
	;
rightBracket
	: 	')' {sqlBuilder.append(")"); indexLevel--; }
	;

atom
	:	INT {values.add(Long.parseLong($INT.text));}
	|	DEC {values.add(Double.parseDouble($DEC.text));}
	|	QuoteString {values.add($QuoteString.text);}
	|   MARK {values.add($MARK.text);}
	;

updateAtom
	:	INT {updateValues.add(Long.parseLong($INT.text));}
	|	DEC {updateValues.add(Double.parseDouble($DEC.text));}
	|	QuoteString {updateValues.add($QuoteString.text);}
	|   MARK {updateValues.add($MARK.text);}
	;
aton	:	INT;

	
// Simple, but more permissive than the RFC allows. See number above for a validity check.
INT	:  '-'? Digit+;

DEC	:	 '-'? Digit+  '.' Digit+;

MARK : '?';

operate
	:	EQ
	|	LT
	|	GT
	|	NE
	|	LE
	|	GE
	;

AD	: ('a'|'A')('n'|'N')('d'|'D');
OD	: ('o'|'O')('r'|'R');
IN	: ('i'|'I')('n'|'N');
BA	: ('b'|'B')('e'|'E')('t'|'T')('w'|'W')('e'|'E')('e'|'E')('n'|'N');
LK	: ('l'|'L')('i'|'I')('k'|'K')('e'|'E');
IS	:	('i'|'I')('s'|'S');
NOT	:	('n'|'N')('o'|'O')('t'|'T');
NUL	:	('n'|'N')('u'|'U')('l'|'L')('l'|'L');

QuoteString 
	:	'\'' (ESCqs | ~'\'' )* '\''
	{
	setText(getText().substring(1, getText().length()-1).replaceAll("''", "'"));
	}
	;


fragment
ESCqs
	:	'\'' '\''
	;
	

ID  :   ('a'..'z'|'A'..'Z'|'_')('a'..'z'|'A'..'Z'|'_'|'0'..'9')*;

EQ	: '=';
LT	: '<';
GT	: '>';
NE	: '!='|'<>';
LE	: '<=';
GE	: '>=';



WS: (' '|'\n'|'\r'|'\t')->skip ; // ignore whitespace


fragment Digit
	: '0'..'9'
	;

