/**
 ** Copyright (C) 2010 OpenStrategies
 ** 
 ** This library is free software; you can redistribute it and/or modify it under the terms of the 
 ** GNU Lesser General Public License as published by the Free Software Foundation; either version 
 ** 2.1 of the License, or (at your option) any later version.
 **
 ** This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 ** without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See 
 ** the GNU Lesser General Public License for more details.
 ** 
 ** You should have received a copy of the GNU Lesser General Public License along with this 
 ** library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, 
 ** Boston, MA 02111-1307 USA 
 ** 
 ** http://www.gnu.org/licenses/lgpl-3.0.txt
 **
 ** @author sfrancolla@gmail.com
 **/
package org.openstrategies.metc.strategy.base;

import org.marketcetera.trade.*;

/**
 * Simple suggestion identifier resolution based on an unbound UUID.
 */
public class UUIDSuggestionIdentifier implements SuggestionIdentifier {

   /**
    * Resolve the id from a strategy to be passed into the OrderSingleSuggestion.
    */
   public String resolveIdentifier(OpenStrategy strategy) {
      return java.util.UUID.randomUUID().toString();
   }

   /**
    * Resolve the id from the OrderSingleSuggestion.
    */
   public String resolveIdentifier(OrderSingleSuggestion strategy) {
      return strategy.getIdentifier();
   }
}
