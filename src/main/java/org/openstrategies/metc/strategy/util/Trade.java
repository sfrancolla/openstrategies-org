/***
 ** OpenStrategies-org
 ** http://github.com/sfrancolla/openstrategies-org
 ** 
 ** Copyright (C) 2010, OpenStrategies, Steven Francolla, and individual contributors as indicated
 ** by the @authors tag. See the copyright.txt in the distribution for a full listing of individual 
 ** contributors.
 ** 
 ** This library is free software; you can redistribute it and/or modify it under the terms of the 
 ** GNU Lesser General Public License as published by the Free Software Foundation; either version 
 ** 3 of the License, or (at your option) any later version.
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
 **/
package org.openstrategies.metc.strategy.util;

import java.math.*;
import java.util.*;
import org.marketcetera.trade.*;
import org.openstrategies.metc.strategy.*;
import org.openstrategies.metc.strategy.base.*;
import org.openstrategies.metc.strategy.base.OpenStrategy.*;

/**
 * Fluent trade suggestion construction.   
 */
public class Trade {
   
   static final SuggestionIdentifier SUGGESTION_IDENTIFIER = SuggestionIdentifierFactory.make();

   private OpenStrategy strategy;
   private Map<String, String> customFields;
   private boolean conditional = true;

   //custom fields
   public static final String DESCRIPTION = "description";
   public static final String DIRECTION = "direction";

   private Trade() {
   }

   /**
    * Fluent construction.
    */
   public static Trade forStrategy(OpenStrategy strat) {
      Trade instance = new Trade();
      instance.strategy = strat;
      return instance;
   }

   private Map<String, String> getCustomFields() {
      if (customFields == null) {
         customFields = new HashMap();
      }
      return customFields;
   }

   public Trade ifTrue(boolean b) {
      this.conditional = b;
      return this;
   }

   public Trade comment(String note) {
      getCustomFields().put(DESCRIPTION, note);
      return this;
   }

   public Trade direction(Direction direction) {
      getCustomFields().put(DIRECTION, direction.toString());
      return this;
   }

   public void suggest(String symbol, Side side, double price) {
      if (conditional) {
         Direction direction = (side.equals(Side.SellShort)) ? Direction.Short : Direction.Long;
         suggest(symbol, side, direction, price);
      }
   }

   public void suggest(String symbol, Side side, Direction direction, double price) {
      suggest(symbol, side, direction, new BigDecimal(price));
   }

   public void suggest(String symbol, Side side, Direction direction, BigDecimal price) {
      if (conditional) {
         OrderSingle order = Factory.getInstance().createOrderSingle();
         order.setSide(side);
         order.setSymbol(new MSymbol(symbol, SecurityType.CommonStock));
         order.setPrice(price);
         order.setCustomFields(customFields);

         strategy.suggestTrade(new OpenDelegate(order, new BigDecimal(0), SUGGESTION_IDENTIFIER.resolveIdentifier(strategy)));
      }
   }
   
   public void order(String symbol, Side side, Direction direction, BigDecimal price) {
      if (conditional) {
         OrderSingle order = Factory.getInstance().createOrderSingle();
         order.setSide(side);
         order.setSymbol(new MSymbol(symbol, SecurityType.CommonStock));
         order.setPrice(price);
         order.setCustomFields(customFields);

         strategy.sendOrder(new OpenDelegate(order));
      }
   }
   
   
}
