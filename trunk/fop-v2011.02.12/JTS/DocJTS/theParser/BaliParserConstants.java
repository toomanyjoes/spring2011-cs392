/* Generated By:JavaCC: Do not edit this line. BaliParserConstants.java */
package theParser;

public interface BaliParserConstants {

  int EOF = 0;
  int SINGLE_LINE_COMMENT = 9;
  int FORMAL_COMMENT = 10;
  int MULTI_LINE_COMMENT = 11;
  int LETTER = 13;
  int DIGIT = 14;
  int TYPEEQN = 15;
  int LAYER = 16;
  int REALM = 17;
  int IMPORTS = 18;
  int RELATIVE = 19;
  int ABSTRACT = 20;
  int BOOLEAN = 21;
  int BREAK = 22;
  int BYTE = 23;
  int CASE = 24;
  int CATCH = 25;
  int CHAR = 26;
  int CLASS = 27;
  int CONST = 28;
  int CONTINUE = 29;
  int _DEFAULT = 30;
  int DO = 31;
  int DOUBLE = 32;
  int ELSE = 33;
  int EXTENDS = 34;
  int FALSE = 35;
  int FINAL = 36;
  int FINALLY = 37;
  int FLOAT = 38;
  int FOR = 39;
  int GOTO = 40;
  int IF = 41;
  int IMPLEMENTS = 42;
  int IMPORT = 43;
  int INSTANCEOF = 44;
  int INT = 45;
  int INTERFACE = 46;
  int LONG = 47;
  int NATIVE = 48;
  int NEW = 49;
  int NULL = 50;
  int PACKAGE = 51;
  int PRIVATE = 52;
  int PROTECTED = 53;
  int PUBLIC = 54;
  int RETURN = 55;
  int SHORT = 56;
  int STATIC = 57;
  int SUPER = 58;
  int SWITCH = 59;
  int SYNCHRONIZED = 60;
  int THIS = 61;
  int THROW = 62;
  int THROWS = 63;
  int TRANSIENT = 64;
  int TRUE = 65;
  int TRY = 66;
  int VOID = 67;
  int VOLATILE = 68;
  int WHILE = 69;
  int LBRACE = 70;
  int RBRACE = 71;
  int LBRACKET = 72;
  int RBRACKET = 73;
  int SEMICOLON = 74;
  int COMMA = 75;
  int DOT = 76;
  int ASSIGN = 77;
  int GT = 78;
  int LT = 79;
  int BANG = 80;
  int TILDE = 81;
  int HOOK = 82;
  int COLON = 83;
  int EQ = 84;
  int LE = 85;
  int GE = 86;
  int NE = 87;
  int SC_OR = 88;
  int SC_AND = 89;
  int INCR = 90;
  int DECR = 91;
  int PLUS = 92;
  int MINUS = 93;
  int STAR = 94;
  int SLASH = 95;
  int BIT_AND = 96;
  int BIT_OR = 97;
  int XOR = 98;
  int REM = 99;
  int LSHIFT = 100;
  int RSIGNEDSHIFT = 101;
  int RUNSIGNEDSHIFT = 102;
  int PLUSASSIGN = 103;
  int MINUSASSIGN = 104;
  int STARASSIGN = 105;
  int SLASHASSIGN = 106;
  int ANDASSIGN = 107;
  int ORASSIGN = 108;
  int XORASSIGN = 109;
  int REMASSIGN = 110;
  int LSHIFTASSIGN = 111;
  int RSIGNEDSHIFTASSIGN = 112;
  int RUNSIGNEDSHIFTASSIGN = 113;
  int LPAREN = 114;
  int RPAREN = 115;
  int INTEGER_LITERAL = 116;
  int DECIMAL_LITERAL = 117;
  int HEX_LITERAL = 118;
  int OCTAL_LITERAL = 119;
  int FLOATING_POINT_LITERAL = 120;
  int EXPONENT = 121;
  int CHARACTER_LITERAL = 122;
  int STRING_LITERAL = 123;
  int IDENTIFIER = 124;
  int OTHER = 125;

  int DEFAULT = 0;
  int IN_SINGLE_LINE_COMMENT = 1;
  int IN_FORMAL_COMMENT = 2;
  int IN_MULTI_LINE_COMMENT = 3;

  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "\"\\n\"",
    "\"\\r\"",
    "\"\\f\"",
    "\"//\"",
    "<token of kind 7>",
    "\"/*\"",
    "<SINGLE_LINE_COMMENT>",
    "\"*/\"",
    "\"*/\"",
    "<token of kind 12>",
    "<LETTER>",
    "<DIGIT>",
    "\"typeEquation\"",
    "\"layer\"",
    "\"realm\"",
    "\"imports\"",
    "\"relative\"",
    "\"abstract\"",
    "\"boolean\"",
    "\"break\"",
    "\"byte\"",
    "\"case\"",
    "\"catch\"",
    "\"char\"",
    "\"class\"",
    "\"const\"",
    "\"continue\"",
    "\"default\"",
    "\"do\"",
    "\"double\"",
    "\"else\"",
    "\"extends\"",
    "\"false\"",
    "\"final\"",
    "\"finally\"",
    "\"float\"",
    "\"for\"",
    "\"goto\"",
    "\"if\"",
    "\"implements\"",
    "\"import\"",
    "\"instanceof\"",
    "\"int\"",
    "\"interface\"",
    "\"long\"",
    "\"native\"",
    "\"new\"",
    "\"null\"",
    "\"package\"",
    "\"private\"",
    "\"protected\"",
    "\"public\"",
    "\"return\"",
    "\"short\"",
    "\"static\"",
    "\"super\"",
    "\"switch\"",
    "\"synchronized\"",
    "\"this\"",
    "\"throw\"",
    "\"throws\"",
    "\"transient\"",
    "\"true\"",
    "\"try\"",
    "\"void\"",
    "\"volatile\"",
    "\"while\"",
    "\"{\"",
    "\"}\"",
    "\"[\"",
    "\"]\"",
    "\";\"",
    "\",\"",
    "\".\"",
    "\"=\"",
    "\">\"",
    "\"<\"",
    "\"!\"",
    "\"~\"",
    "\"?\"",
    "\":\"",
    "\"==\"",
    "\"<=\"",
    "\">=\"",
    "\"!=\"",
    "\"||\"",
    "\"&&\"",
    "\"++\"",
    "\"--\"",
    "\"+\"",
    "\"-\"",
    "\"*\"",
    "\"/\"",
    "\"&\"",
    "\"|\"",
    "\"^\"",
    "\"%\"",
    "\"<<\"",
    "\">>\"",
    "\">>>\"",
    "\"+=\"",
    "\"-=\"",
    "\"*=\"",
    "\"/=\"",
    "\"&=\"",
    "\"|=\"",
    "\"^=\"",
    "\"%=\"",
    "\"<<=\"",
    "\">>=\"",
    "\">>>=\"",
    "\"(\"",
    "\")\"",
    "<INTEGER_LITERAL>",
    "<DECIMAL_LITERAL>",
    "<HEX_LITERAL>",
    "<OCTAL_LITERAL>",
    "<FLOATING_POINT_LITERAL>",
    "<EXPONENT>",
    "<CHARACTER_LITERAL>",
    "<STRING_LITERAL>",
    "<IDENTIFIER>",
    "<OTHER>",
  };

}
