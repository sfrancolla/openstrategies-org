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

   /**
    * Send order.
    *
     * @param inOrder an <code>OrderSingle</code> value
     * @return an <code>OrderID</code> value representing the submitted order or null if the order could not be sent
    */
   public boolean sendOrder(OpenDelegate delegate) {
      return true;//send(delegate.<OrderSingle> nextArg());
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
