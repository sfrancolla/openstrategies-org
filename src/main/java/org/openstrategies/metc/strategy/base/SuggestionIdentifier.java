package org.openstrategies.metc.strategy.base;

import org.marketcetera.trade.*;

/**
 * Suggestion identifier resolution.
 * 
 * @author sfrancolla@gmail.com
 */
public interface SuggestionIdentifier {

   /**
    * Resolve the id from an OpenStrategy to be passed into the OrderSingleSuggestion.
    */
   public String resolveIdentifier(OpenStrategy strategy);

   /**
    * Resolve the id from the OrderSingleSuggestion.
    */
   public String resolveIdentifier(OrderSingleSuggestion strategy);
}
