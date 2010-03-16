package org.openstrategies.metc.strategy.util;

import java.math.*;
import java.util.*;
import org.marketcetera.trade.*;
import org.openstrategies.metc.strategy.*;
import org.openstrategies.metc.strategy.base.*;
import org.openstrategies.metc.strategy.base.OpenStrategy.*;

/**
 * Fluent trade suggestion construction.   
 *
 * @author sfrancolla@gmail.com
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
}
