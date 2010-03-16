package org.openstrategies.metc.strategy.base;

import org.marketcetera.trade.*;

/**
 * Simple suggestion identifier resolution based on an unbound UUID.
 *
 * @author sfrancolla@gmail.com
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
