import sys
import re
import random
import ply.lex as lex
import ply.yacc as yacc


# token names
tokens = (
	'NAME','NUMBER', 'STRING', 'COMMA', 'SC',
	'PLUS','MINUS','TIMES','DIVIDE','EQUALS',
	'LPAREN','RPAREN', 'LBRACKET', 'RBRACKET',
	'LSBRACKET', 'RSBRACKET',
	'PRINT', 'READ', 'RETURN',
	'ITER', 'IF', 'ELSE', 'FOR',
	'LT', 'GT', 'LEQ', 'GEQ', 'DE', 'NE',
	'AND', 'OR', 'NOT',
	'NUM', 'TEXT', 'TABLE', 'DOT', 'GRAPH',
    'DOLLAR', 'FREAD', 'FWRITE', 'FSPLIT', 'FPARA', 'EXCLT', 'FUNCTION', 'AMPER',
    'BREAK', 'NL'
	)

# operator precedence
precedence = (
	('left','AND', 'OR', 'NOT'),
	('left','LT', 'GT', 'LEQ', 'GEQ', 'DE', 'NE'),
	('left','PLUS','MINUS'),
	('left','TIMES','DIVIDE'),
	)


# tokens
t_COMMA	= r','
t_PLUS	= r'\+'
t_MINUS   = r'-'
t_TIMES   = r'\*'
t_DIVIDE  = r'/'
t_EQUALS  = r'='
t_LPAREN  = r'\('
t_RPAREN  = r'\)'
t_LBRACKET  = r'\{'
t_RBRACKET  = r'\}'
t_LSBRACKET  = r'\['
t_RSBRACKET  = r'\]'
t_SC  = r';'
t_LT  = r'<'
t_GT  = r'>'
t_LEQ  = r'<='
t_GEQ  = r'>='
t_DE  = r'=='
t_NE  = r'!='
t_NAME	= r'[a-zA-Z_][a-zA-Z0-9_]*'
t_DOT = r'\.'
t_DOLLAR = r'\$'
t_FREAD = r'\@'
t_FWRITE = r'->'
t_FSPLIT = r'-<'
t_FPARA = r'=>'
t_EXCLT = r'\!'
t_AMPER = r'&'


# ignored characters
t_ignore = " \t"


# loop variable
loopVar = 0


def getVar():
	global loopVar
	loopVar += 1
	return "loopVar_%s"%loopVar


# token functions

def t_NUMBER(t):
	r'\d+'
	t.value = int(t.value)
	return t

def t_RETURN(t):
	r'return'
	return t

def t_NUM(t):
	r'num'
	return t
    
def t_BREAK(t):
	r'break'
	return t

def t_READ(t):
	r'read'
	return t

def t_AND(t):
	r'and'
	return t

def t_FUNCTION(t):
	r'function'
	return t

def t_OR(t):
	r'or'
	return t

def t_NOT(t):
	r'not'
	return t

def t_TEXT(t):
	r'text'
	return t

def t_TABLE(t):
	r'table'
	return t

def t_GRAPH(t):
	r'graph'
	return t

def t_IF(t):
	r'if'
	return t

def t_ELSE(t):
	r'else'
	return t

def t_STRING(t):
	r'"[^"]+"'
	return t

def t_PRINT(t):
	r'print'
	return t

def t_FOR(t):
	r'loop'
	return t

def t_ITER(t):
	r'iter'
	return t

def t_NL(t):
	r'\n+'
	t.lexer.lineno += t.value.count("\n")
	return t

def t_error(t):
	print("Line %s: Illegal character '%s'" % (t.lexer.lineno,t.value[0]))
	t.lexer.skip(1)


# build the lexer
lex.lex()


# dictionary of variable types
symbols = { }

loadedFuncs = []

header = ""
functions = ""

helperFuncs = {"double2dConcat" : False, "string2dConcat" : False, "double2dDiff" : False, "string2dDiff" : False}

def p_start(t):
	'start : statement'
	global body
	body = t[1]

def p_statement_conc(t):
	'statement : statement statement'
	t[0] = "%s\n%s"%(t[1],t[2])
    
def p_statement_sc(t):
	'statement : statement SC'
	t[0] = "%s;"%t[1]
    
def p_statement_free(t):
	'statement : NL'
	t[0] = ""

def p_statement_value(t):
	'statement : value'
	t[0] = "%s;"%t[1]

def p_value(t):
	'''value : STRING
			  | NUMBER
			  | NAME
			  | nameDim
			  | funcCall'''
	t[0] = t[1]

#temp production - delete after typecasting is done
def p_table_dir_assign(t):
	'value : TABLE LPAREN arrayValue RPAREN'
	dim = getArrayVDim(t[3])
        if dim != 2: raise BaseException("Line %s: Table is 2 dimensional"%(t.lexer.lineno-1))
	t[0] = "new Table(new Object[][]%s);"%t[3]

def p_table_dir_assign2(t):
	'value : TABLE LPAREN value RPAREN'
	t[0] = "new Table(%s);"%t[3]

def p_table_value(t):
	'''value : TABLE LPAREN name_or_number COMMA name_or_number RPAREN'''
	t[0] = "new Table(%s,%s)"%(t[3],t[5])

def p_graph_value(t):
	'''value : GRAPH LPAREN name_or_number COMMA name_or_number RPAREN'''
	t[0] = "new Graph(%s,%s)"%(t[3],t[5])

def p_name_dim(t):
	'nameDim : NAME dim'
	t[0] = "%s %s"%(t[1],t[2])

def p_name_or_number(t):
	'''name_or_number : NAME
					  | NUMBER'''
	t[0] = t[1]


############################### FUNC IMPORT

def p_exclamation(t):
	'''value : EXCLT value'''
	t[0] = "new Function(%s)"%t[2]

    
############################### METHODS
def p_methods(t):
	'''value : NAME DOT funcCall'''
	t[0] = "%s.%s"%(t[1],t[3])  

def p_attrib(t):
	'''value : NAME DOT NAME'''
	t[0] = "%s.%s"%(t[1],t[3])
    

############################### COMPARISONS

def p_value_lt(t):
	'''value : value LT value'''
	t[0] = "((%s < %s) ? 1 : 0)"%(t[1], t[3])

def p_value_gt(t):
	'''value : value GT value'''
	t[0] = "((%s > %s) ? 1 : 0)"%(t[1], t[3])

def p_value_leq(t):
	'''value : value LEQ value'''
	t[0] = "((%s <= %s) ? 1 : 0)"%(t[1], t[3])

def p_value_geq(t):
	'''value : value GEQ value'''
	t[0] = "((%s >= %s) ? 1 : 0)"%(t[1], t[3])

def p_value_de(t):
	'''value : value DE value'''
	t[0] = "((%s == %s) ? 1 : 0)"%(t[1], t[3])

def p_value_ne(t):
	'''value : value NE value'''
	t[0] = "((%s != %s) ? 1 : 0)"%(t[1], t[3])


############################### LOPS

def p_value_and(t):
	'''value : value AND value'''
	t[0] = "(((%s != 0) && (%s != 0)) ? 1 : 0)"%(t[1], t[3])

def p_value_or(t):
	'''value : value OR value'''
	t[0] = "(((%s != 0) || (%s != 0)) ? 1 : 0)"%(t[1], t[3])

def p_value_not(t):
	'''value : NOT value'''
	t[0] = "((%s != 0) ? 0 : 1)"%(t[2])


############################### ARITHMETICS

def p_value_plus(t):
	'''value : value PLUS value'''
	global symbols, functions, header
	type1 = get_type(t[1])
	type2 = get_type(t[3])
	if type1 == 'double' and type2 == 'double' or type1 == 'String' and type2 == 'String':
		t[0] = "%s + %s"%(t[1],t[3])
		symbols[t[0]] = type1
	elif type1 == 'double' and type2 == 'String' or type1 == 'String' and type2 == 'double':
		t[0] = "%s + %s"%(t[1],t[3])
		symbols[t[0]] = 'String'
	elif type1 == 'double[]' and type2 == 'double[]':
		if not helperFuncs["double2dConcat"]:
			helperFuncs["double2dConcat"] = True
			header += "import java.util.Arrays;;\n"
			functions += '''public static double[] concat(double[] a, double[] b) {
								int aLen = a.length;
								int bLen = b.length;
								double[] c = new double[aLen+bLen];
								System.arraycopy(a, 0, c, 0, aLen);
								System.arraycopy(b, 0, c, aLen, bLen);
								return c;}'''
		t[0] = "concat(%s, %s)"%(t[1],t[3])
		symbols[t[0]] = 'double[]'
	elif type1 == 'String[]' and type2 == 'String[]':
		if not helperFuncs["string2dConcat"]:
			helperFuncs["string2dConcat"] = True
			header += "import java.util.Arrays;;\n"
			functions += '''public static String[] concat(String[] a, String[] b) {
								int aLen = a.length;
								int bLen = b.length;
								String[] c = new String[aLen+bLen];
								System.arraycopy(a, 0, c, 0, aLen);
								System.arraycopy(b, 0, c, aLen, bLen);
								return c;}'''
		t[0] = "concat(%s, %s)"%(t[1],t[3])
		symbols[t[0]] = 'String[]'
	else:
		raise BaseException("Line %s: Semantic error, %s + %s"%((t.lexer.lineno-1), type1, type2))

def p_value_minus(t):
	'''value : value MINUS value'''
	global symbols, functions, header
	type1 = get_type(t[1])
	type2 = get_type(t[3])
	if type1 == 'double' and type2 == 'double':
		t[0] = "%s - %s"%(t[1],t[3])
		symbols[t[0]] = type1
	elif type1 == 'double[]' and type2 == 'double[]':
		if not helperFuncs["double2dDiff"]:
			helperFuncs["double2dDiff"] = True
			header += "import java.util.*;\n"
			functions += '''static double[] difference( double[] b, double[] a ) {
								HashSet<Double> res = new HashSet<>();
								Arrays.sort( a );
								for ( double elem : b ) {
									if ( !res.contains( elem ) && Arrays.binarySearch( a, elem ) < 0 )
										res.add( elem );
								}
								double[] ar = new double[ res.size() ];
								int i = 0;
								for ( double elem : res )
									ar[ i++ ] = elem;
								return ar;
								}'''
		t[0] = "difference(%s, %s)"%(t[1],t[3])
		symbols[t[0]] = 'double[]'
	elif type1 == 'String[]' and type2 == 'String[]':
		if not helperFuncs["string2dDiff"]:
			helperFuncs["string2dDiff"] = True
			header += "import java.util.*;\n"
			functions += '''static String[] difference( String[] b, String[] a ) {
								HashSet<String> res = new HashSet<>();
								Arrays.sort( a );
								for ( String elem : b ) {
									if ( !res.contains( elem ) && Arrays.binarySearch( a, elem ) < 0 )
										res.add( elem );
								}
								String[] ar = new String[ res.size() ];
								int i = 0;
								for ( String elem : res )
									ar[ i++ ] = elem;
								return ar;
								}'''
		t[0] = "difference(%s, %s)"%(t[1],t[3])
		symbols[t[0]] = 'String[]'
	else:
		raise BaseException("Line %s: Semantic error, %s - %s"%((t.lexer.lineno-1), type1, type2))

def p_value_times(t):
	'''value : value TIMES value'''
	global symbols
	if get_type(t[1]) == 'double' and get_type(t[3]) == 'double':
		t[0] = "%s * %s"%(t[1],t[3])
		symbols[t[0]] = 'double'
	else:
		raise BaseException("Line %s: Semantic error, %s * %s"%((t.lexer.lineno-1), type1, type2))

def p_value_divide(t):
	'''value : value DIVIDE value'''
	global symbols
	if get_type(t[1]) == 'double' and get_type(t[3]) == 'double':
		t[0] = "%s / %s"%(t[1],t[3])
		symbols[t[0]] = 'double'
	else:
		raise BaseException("Line %s: Semantic error, %s / %s"%((t.lexer.lineno-1), type1, type2))


############################### READ

def p_value_read(t):
	'''value : READ'''
	global header
	header += "import java.io.*;\n"
	t[0] = "(new BufferedReader(new InputStreamReader(System.in))).readLine()"


############################### ASSIGNMENTS

def p_declr(t):
	'statement : typename'
	t[0] = "%s;"%t[1]

def p_type_name(t):
	'typename : type NAME'
	global symbols, loadedFuncs
	if t[1] == "Function": loadedFuncs.append(t[2])
	symbols[t[2]] = t[1]
	t[0] = "%s %s"%(t[1],t[2])

def p_declr_assign(t):
	'statement : type NAME EQUALS value'
	global symbols, loadedFuncs
	if t[1] == "Function": loadedFuncs.append(t[2])
	symbols[t[2]] = t[1]
	t[0] = "%s %s = %s;"%(t[1],t[2],t[4])

def p_assign(t):
	'''statement : NAME EQUALS value
				 | nameDim EQUALS value'''
	t[0] = "%s = %s;"%(t[1],t[3])


############################### FUNCTIONS

def p_func2(t):
	'statement : type dim NAME LPAREN rev_params RPAREN LBRACKET statement RBRACKET'
	global functions
	functions += "public static %s %s %s ( %s ) { %s }\n"%(t[1],t[2],t[3],t[5],t[8])
	t[0] = ""

def p_func(t):
	'statement : typename LPAREN rev_params RPAREN LBRACKET statement RBRACKET'
	global functions
	functions += "public static %s ( %s ) { %s }\n"%(t[1],t[3],t[6])
	t[0] = ""

def p_func_no_return(t):
	'statement : NAME LPAREN rev_params RPAREN LBRACKET statement RBRACKET'
	global functions
	functions += "public static void %s ( %s ) { %s }\n"%(t[1],t[3],t[6])
	t[0] = ""

def p_func_call(t):
	'funcCall : NAME LPAREN snd_params RPAREN'
	if t[1] in loadedFuncs:
		t[0] = "PluginManager.execute(%s, %s )"%(t[1],t[3])
	else: t[0] = "%s ( %s )"%(t[1],t[3])

def p_params_rev(t):
	'''rev_params : rev_params COMMA rev_params
                  | type dim NAME
				  | typename
				  | '''
	global symbols, loadedFuncs
	if len(t) == 1: t[0] = ""
	elif len(t) == 4: 
		if t[2] == ",":
			t[0] = "%s, %s"%(t[1],t[3])
		else:
			symbols[t[3]] = "%s[]"%t[1]
			t[0] = "%s %s %s"%(t[1],t[2],t[3])
	else: t[0] = t[1]
    

def p_params_snd(t):
	'''snd_params : snd_params COMMA snd_params
				  | value
				  | '''
	if len(t) == 1: t[0] = ""
	elif len(t) == 2: t[0] = t[1]
	else: t[0] = "%s, %s"%(t[1],t[3])

def p_return(t):
	'statement : RETURN value'
	t[0] = "return %s;"%t[2]


############################### TYPES

def p_typecast(t):
	'value : LPAREN type RPAREN value'
	if t[2] == "double" and get_type(t[4]) == 'String':
                t[0] = 'Double.parseDouble(%s)'%t[4]
	elif t[2] == "String" and get_type(t[4]) == 'double':
                t[0] = '"" + %s'%t[4]
        else: raise BaseException("Line %s: Unknown typecast"%(t.lexer.lineno-1))


def p_type_func(t):
	'type : FUNCTION'
	t[0] = 'Function'
        
def p_type_num(t):
	'type : NUM'
	t[0] = 'double'

def p_type_text(t):
	'type : TEXT'
	t[0] = 'String'

def p_type_table(t):
	'type : TABLE'
	t[0] = 'Table'

def p_type_graph(t):
	'type : GRAPH'
	t[0] = 'Graph'

############################### PRINT

def p_statement_print(t):
	'statement : PRINT value'
	global header
	type = get_type(t[2])
	if type == "double[]" or type == "String[]": 
		header += "import java.util.Arrays;;\n"
		t[0] = "System.out.println(  Arrays.toString(%s) );"%t[2]
	else: t[0] = "System.out.println(%s);"%t[2]
    


############################### IF

def p_statement_if(t):
	'statement : IF LPAREN value RPAREN LBRACKET statement RBRACKET'
	t[0] = "if((%s) != 0) { %s }"%(t[3],t[6])

def p_statement_else(t):
	'statement : ELSE LBRACKET statement RBRACKET'
	t[0] = "else { %s }"%t[3]


############################### LOOPS

def p_statement_iter(t):
	'statement : ITER LPAREN NUMBER RPAREN LBRACKET statement RBRACKET'
	v = getVar()
	t[0] = "for(int %s = 0; %s < %s; %s++) { %s }"%(v,v,t[3],v,t[6])

def p_statement_for1(t):
	'statement : FOR LPAREN statement COMMA value COMMA statement RPAREN LBRACKET statement RBRACKET'
	v = getVar()
	t[0] = "%s for(; (%s) != 0;) { %s %s}"%(t[3],t[5],t[10],t[7])

def p_statement_for2(t):
	'statement : FOR LPAREN COMMA value COMMA statement RPAREN LBRACKET statement RBRACKET'
	v = getVar()
	t[0] = "for(; (%s) != 0;) { %s %s}"%(t[4],t[9],t[6])

def p_statement_for3(t):
	'statement : FOR LPAREN COMMA value COMMA RPAREN LBRACKET statement RBRACKET'
	v = getVar()
	t[0] = "for(; (%s) != 0;) { %s}"%(t[4],t[8])

def p_statement_for4(t):
	'statement : FOR LPAREN statement COMMA value COMMA RPAREN LBRACKET statement RBRACKET'
	v = getVar()
	t[0] = "%s for(; (%s) != 0;) { %s}"%(t[3],t[5],t[9])

def p_error(t):
	raise BaseException("Line %s: Syntax error at '%s"%(t.lexer.lineno-1,[t.value]))

    
def p_break(t):
	'statement : BREAK'
	t[0] = "break;"

######################### ARRAYS
def p_array_declr(t):
	'''statement : type dim NAME'''
	global symbols
	symbols[t[3]] = "%s[]"%t[1]
	dimension = '[]' * t[2].count('[')
	t[0] = "%s %s %s = new %s %s;"%(t[1],dimension,t[3],t[1],t[2])

def p_array_assgn_declr(t):
	'''statement : type nameDim EQUALS value'''
	global symbols
	name = t[1].split('[')[0]
	symbols[name] = "%s[]"%t[1]
	dimension = '[]' * t[2].count('[')
	t[0] = "%s %s %s = new %s %s;"%(t[1],dimension,t[3],t[1],t[2])

def p_multidim(t):
	'''dim : dim dim'''
	t[0] = "%s %s"%(t[1],t[2])

def p_dim(t):
	'''dim : LSBRACKET value RSBRACKET'''
	t[0] = "[(int)(%s)]"%t[2]

def p_dim_empty(t):
	'''dim : LSBRACKET RSBRACKET'''
	t[0] = "[]"

def p_array_typeDimName(t):
	'typeDimName : type dim NAME'
	global symbols
	symbols[t[3]] = "%s[]"%t[1]
	t[0] = "%s %s %s"%(t[1],t[2],t[3])

def p_array_copy(t):
	'''statement : typeDimName EQUALS NAME'''
	t[0] = "%s = %s.clone()"%(t[1],t[3])

    
def p_array_dir_assign(t):
	'statement : typeDimName EQUALS arrayValue'
	t[0] = "%s = %s;"%(t[1],t[3])

def getArrayVDim(s):
        index = s.find("}")
        return s[:index].count("{")


def p_arrayValue(t):
	'arrayValue : LBRACKET entry RBRACKET'
	t[0] = "{ %s }"%t[2]

def p_entry_conc(t):
	'entry : entry COMMA entry'
	t[0] = "%s , %s"%(t[1],t[3])

def p_entry(t):
	'entry : value'
	t[0] = t[1]

def p_entry2(t):
	'entry : LBRACKET entry RBRACKET'
	t[0] = "{%s}"%t[2]

######################### SHELL_ARGS

def p_shell_args(t):
        'value : DOLLAR value'
        t[0] = "args[(int)(%s)]"%t[2]

######################### FILE_IO

def p_file_read(t):
        'value : FREAD value'
        t[0] = "FileManager.read(%s)"%t[2]

def p_file_write(t):
        'statement : value FWRITE value'
        t[0] = "FileManager.write(%s, %s);"%(t[3], t[1])

def p_file_split(t):
        'statement : value FSPLIT value'
        t[0] = "FileManager.split(%s, %s);"%(t[3], t[1])

######################### PARALLEL

def p_exec_parallel(t):
    'statement : AMPER NAME LPAREN snd_params RPAREN FPARA value'
    t[0] = "ProcessManager.executeParallel(%s, %s, %s);"%(t[2], t[4], t[7])


# helper functions

def get_type(x):
	if isinstance(x,str):
		if ".length" in x: return 'double'
	if x in symbols:
		return symbols[x]
	elif is_num_literal(x):
		return 'double'
	elif is_text_literal(x):
		return 'String'
	elif is_non_indexed_array(x):
		return get_non_indexed_array_type(x)
	elif is_indexed_array(x):
		return get_indexed_array_type(x)
	else:
		return None

def is_num_literal(x):
	return str(x).isdigit()

def is_text_literal(x):
	return str(x).startswith('"') and x.endswith('"')

def is_non_indexed_array(x):
	m = re.match('[a-zA-Z0-9_]+ \[\]',x)
	m = (m != None) 
	return m

def get_non_indexed_array_type(x):
	array = x.split('[')[0][:-1]
	return "%s[]"%get_type(array)
    
def is_indexed_array(x):
	m = re.match('[a-zA-Z0-9_]+ \[[^[].*$',x)
	m = (m != None)
	return m

def get_indexed_array_type(x):
	array = x.split('[')[0][:-1]
	return "%s"%(get_type(array).split('[')[0])


# perform translation

yacc.yacc(debug=False)

input_code = open(sys.argv[1],"r")

output_code = ""

for line in input_code.readlines():
	line = line.replace("\r","")
	#line = line.replace("\n","")
	line = line.replace(";","")
	#if line == "": continue
	if line[:2] == "//": continue
	output_code += "%s "%line

yacc.parse(output_code)



#print "package com.pipelinescript;"
print header
print "public class Pipeline\n{"
print functions
print "public static void main(String[] args) throws Exception\n{"
print body
print "}\n\n}"
