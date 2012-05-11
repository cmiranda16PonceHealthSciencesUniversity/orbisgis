/**
 * The GDMS library (Generic Datasource Management System)
 * is a middleware dedicated to the management of various kinds of
 * data-sources such as spatial vectorial data or alphanumeric. Based
 * on the JTS library and conform to the OGC simple feature access
 * specifications, it provides a complete and robust API to manipulate
 * in a SQL way remote DBMS (PostgreSQL, H2...) or flat files (.shp,
 * .csv...). It is produced by the "Atelier SIG" team of
 * the IRSTV Institute <http://www.irstv.fr/> CNRS FR 2488.
 *
 *
 * Team leader : Erwan BOCHER, scientific researcher,
 *
 * User support leader : Gwendall Petit, geomatic engineer.
 *
 * Previous computer developer : Pierre-Yves FADET, computer engineer, Thomas LEDUC,
 * scientific researcher, Fernando GONZALEZ CORTES, computer engineer, Maxence LAURENT,
 * computer engineer.
 *
 * Copyright (C) 2007 Erwan BOCHER, Fernando GONZALEZ CORTES, Thomas LEDUC
 *
 * Copyright (C) 2010 Erwan BOCHER, Alexis GUEGANNO, Maxence LAURENT, Antoine GOURLAY
 *
 * Copyright (C) 2012 Erwan BOCHER, Antoine GOURLAY
 *
 * This file is part of Gdms.
 *
 * Gdms is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * Gdms is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Gdms. If not, see <http://www.gnu.org/licenses/>.
 *
 * For more information, please consult: <http://www.orbisgis.org/>
 *
 * or contact directly:
 * info@orbisgis.org
 */
package org.gdms.sql.engine.commands.join

import org.gdms.sql.engine.commands.Command
import org.gdms.sql.engine.commands.Row
import org.gdms.sql.evaluator.Expression
import org.gdms.data.values.Value
import org.gdms.data.values.ValueFactory
import org.gdms.sql.engine.GdmSQLPredef._

/**
 * Base Join command trait that contains all Loop-based join algorithms.
 */
trait JoinCommand extends Command {
  
  def doCrossJoin(left: => RowStream, right: => RowStream): RowStream = {
    val r = right.toSeq
    for (p <- left; q <- r) yield p ++ q
  }
  
  def doInnerJoin(left: => RowStream, right: => RowStream, exp: Expression): RowStream = {
    val r = right.toSeq
    val doReduce = (i: Row, j: Row) => {
      val a = i ++ j
      val e = exp.evaluate(a).getAsBoolean
      if (e != null && e.booleanValue) {
        a :: Nil
      } else {
        Nil
      }
    }
    
    for (p <- left; q <- r; s <- doReduce(p, q)) yield s
  }
  
  def doLeftOuterJoin(left: => RowStream, right: => RowStream, exp: Expression): RowStream = {
    val r = right.toSeq
    val empty = nullArray(r.head.size)
    val doReduce = (i: Row, j: Row) => {
      val a = i ++ j
      val e = exp.evaluate(a).getAsBoolean
      if (e != null && e.booleanValue) {
        a :: Nil
      } else {
        Nil
      }
    }
    
    val doFilter = (p: Row, r: Seq[Row]) => {
      val f = r.flatMap(doReduce(p, _))
      if (f.isEmpty) {
        (p ++ empty) :: Nil
      } else {
        f
      }
    }
    
    for (p <- left; q <- doFilter(p, r)) yield q
  }
  
  private def nullArray(size: Int): Array[Value] = {
    Array.fill[Value](size)(ValueFactory.createNullValue[Value])
  }
}