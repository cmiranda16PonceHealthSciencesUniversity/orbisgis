/*
 * OrbisGIS is a GIS application dedicated to scientific spatial simulation.
 * This cross-platform GIS is developed at French IRSTV institute and is able
 * to manipulate and create vector and raster spatial information. OrbisGIS
 * is distributed under GPL 3 license. It is produced  by the geo-informatic team of
 * the IRSTV Institute <http://www.irstv.cnrs.fr/>, CNRS FR 2488:
 *    Erwan BOCHER, scientific researcher,
 *    Thomas LEDUC, scientific researcher,
 *    Fernando GONZALEZ CORTES, computer engineer.
 *
 * Copyright (C) 2007 Erwan BOCHER, Fernando GONZALEZ CORTES, Thomas LEDUC
 *
 * This file is part of OrbisGIS.
 *
 * OrbisGIS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OrbisGIS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OrbisGIS. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, please consult:
 *    <http://orbisgis.cerma.archi.fr/>
 *    <http://sourcesup.cru.fr/projects/orbisgis/>
 *
 * or contact directly:
 *    erwan.bocher _at_ ec-nantes.fr
 *    fergonco _at_ gmail.com
 *    thomas.leduc _at_ cerma.archi.fr
 */
/* Generated By:JJTree&JavaCC: Do not edit this line. SQLEngineConstants.java */
package org.gdms.sql.parser;

public interface SQLEngineConstants {

  int EOF = 0;
  int COMMENT_LINE = 5;
  int COMMENT_BLOCK = 6;
  int ALL = 7;
  int AND = 8;
  int AS = 9;
  int ASC = 10;
  int BEGIN = 11;
  int BETWEEN = 12;
  int BY = 13;
  int DESC = 14;
  int DISTINCT = 15;
  int DROP = 16;
  int FROM = 17;
  int GROUP = 18;
  int HAVING = 19;
  int IN = 20;
  int IS = 21;
  int KEY = 22;
  int LIKE = 23;
  int LIMIT = 24;
  int NOT = 25;
  int NULL = 26;
  int OFFSET = 27;
  int OR = 28;
  int ORDER = 29;
  int PRIMARY = 30;
  int SELECT = 31;
  int UNION = 32;
  int SPACES = 33;
  int TABLE = 34;
  int WHERE = 35;
  int CREATE = 36;
  int DELETE = 37;
  int EXISTS = 38;
  int INSERT = 39;
  int INTO = 40;
  int SET = 41;
  int UPDATE = 42;
  int VALUES = 43;
  int INTEGER_LITERAL = 44;
  int FLOATING_POINT_LITERAL = 45;
  int EXPONENT = 46;
  int STRING_LITERAL = 47;
  int BOOLEAN_LITERAL = 48;
  int ID = 49;
  int LETTER = 50;
  int DIGIT = 51;
  int QUOTED_ID = 52;
  int ASSIGN = 53;
  int CONCAT = 54;
  int SEMICOLON = 55;
  int DOT = 56;
  int TILDE = 57;
  int LESS = 58;
  int LESSEQUAL = 59;
  int GREATER = 60;
  int GREATEREQUAL = 61;
  int EQUAL = 62;
  int NOTEQUAL = 63;
  int NOTEQUAL2 = 64;
  int JOINPLUS = 65;
  int OPENPAREN = 66;
  int CLOSEPAREN = 67;
  int ASTERISK = 68;
  int SLASH = 69;
  int PLUS = 70;
  int MINUS = 71;
  int QUESTIONMARK = 72;

  int DEFAULT = 0;

  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\n\"",
    "\"\\r\"",
    "\"\\t\"",
    "<COMMENT_LINE>",
    "<COMMENT_BLOCK>",
    "\"all\"",
    "\"and\"",
    "\"as\"",
    "\"asc\"",
    "\"begin\"",
    "\"between\"",
    "\"by\"",
    "\"desc\"",
    "\"distinct\"",
    "\"drop\"",
    "\"from\"",
    "\"group\"",
    "\"having\"",
    "\"in\"",
    "\"is\"",
    "\"key\"",
    "\"like\"",
    "\"limit\"",
    "\"not\"",
    "\"null\"",
    "\"offset\"",
    "\"or\"",
    "\"order\"",
    "\"primary\"",
    "\"select\"",
    "\"union\"",
    "\"spaces\"",
    "\"table\"",
    "\"where\"",
    "\"create\"",
    "\"delete\"",
    "\"exists\"",
    "\"insert\"",
    "\"into\"",
    "\"set\"",
    "\"update\"",
    "\"values\"",
    "<INTEGER_LITERAL>",
    "<FLOATING_POINT_LITERAL>",
    "<EXPONENT>",
    "<STRING_LITERAL>",
    "<BOOLEAN_LITERAL>",
    "<ID>",
    "<LETTER>",
    "<DIGIT>",
    "<QUOTED_ID>",
    "\":=\"",
    "\"||\"",
    "\";\"",
    "\".\"",
    "\"~\"",
    "\"<\"",
    "\"<=\"",
    "\">\"",
    "\">=\"",
    "\"=\"",
    "\"!=\"",
    "\"<>\"",
    "\"(+)\"",
    "\"(\"",
    "\")\"",
    "\"*\"",
    "\"/\"",
    "\"+\"",
    "\"-\"",
    "\"?\"",
    "\",\"",
  };

}
