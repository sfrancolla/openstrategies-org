package org.openstrategies.metc.strategy.base;

import org.marketcetera.trade.*;

/**
 * Simple factory to produce a suggestion identifier.
 *
 * @author sfrancolla@gmail.com
 */
public class SuggestionIdentifierFactory {
   
   public static SuggestionIdentifier make() {
      return new UUIDSuggestionIdentifier();
   }

}
