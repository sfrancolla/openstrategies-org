package org.openstrategies.metc.strategy.base;

import java.math.*;
import java.util.*;
import org.marketcetera.event.*;
import org.marketcetera.strategy.java.*;
import org.marketcetera.trade.*;

/**
 * Open up the Metc Strategy api a bit.  Want external utils to make calls on this class; they need 
 * access to protected methods.  Use a delegate to relay args.  Also make be verbose about what 
 * event hooks are being called.  And, need to pass ref to the strategy model's id off with
 * suggestions.
 *
 * @author sfrancolla@gmail.com
 */
public class OpenStrategy extends Strategy {
   
   private static String MODEL_ID = "modelId";
   
   public OpenStrategy() {
      super();
   }

   public String getModelId() {
      return getParameter(MODEL_ID);
   }

   @Override
   public void onAsk(AskEvent inAsk) {
      super.onAsk(inAsk);
      warn("onAsk: " + inAsk);
   }

   @Override
   public void onBid(BidEvent inBid) {
      super.onBid(inBid);
      warn("onBid: " + inBid);
   }

   @Override
   public void onCallback(Object inData) {
      super.onCallback(inData);
      warn("onCallback: " + inData);
   }

   @Override
   public void onCancelReject(OrderCancelReject inCancel) {
      super.onCancelReject(inCancel);
      warn("OrderCancelReject: " + inCancel);
   }

   @Override
   public void onExecutionReport(ExecutionReport inExecutionReport) {
      super.onExecutionReport(inExecutionReport);
      warn("onExecutionReport: "+ inExecutionReport);
   }

   @Override
   public void onMarketstat(MarketstatEvent inStatistics) {
      super.onMarketstat(inStatistics);
      warn("onMarketstat: " + inStatistics);
   }

   @Override
   public void onStart() {
      super.onStart();
      warn("onStart");
   }

   @Override
   public void onStop() {
      super.onStop();
      warn("onStop");
   }

   @Override
   public void onOther(Object inEvent) {
      super.onOther(inEvent);
      warn("onOther: " + inEvent);
   }

   @Override
   public void onTrade(TradeEvent inTrade) {
      super.onTrade(inTrade);
      warn("onTrade: " + inTrade);
   }

   /**
    * Suggests a trade.
    *
    * @param inOrder an <code>OrderSingle</code> value containing the trade to suggest
    * @param inScore a <code>BigDecimal</code> value containing the score of this suggestion.  this value is determined by the user
    *   but is recommended to fit in the interval [0..1]
    * @param inIdentifier a <code>String</code> value containing a user-specified string to identify the suggestion
    */
   public void suggestTrade(OpenDelegate delegate) {
      suggestTrade(delegate.<OrderSingle> nextArg(), delegate.<BigDecimal> nextArg(), delegate.<String> nextArg());
   }

   public static class OpenDelegate {
      final Iterator it;

      public OpenDelegate(Object... args) {
         this.it = Arrays.asList(args).iterator();
      }

      public <T> T nextArg() {
         return (T)it.next();
      }
   }
}
