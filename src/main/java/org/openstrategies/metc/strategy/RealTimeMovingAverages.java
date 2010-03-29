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
package org.openstrategies.metc.strategy;

import java.math.*;
import java.util.*;
import org.apache.log4j.*;
import org.kohera.metctools.*;
import org.kohera.metctools.delegate.*;
import org.kohera.metctools.util.*;
import org.marketcetera.client.*;
import org.marketcetera.marketdata.*;
import org.marketcetera.trade.*;
import org.openstrategies.metc.strategy.util.*;

/**
 * Follow the 2, 4, and 8 moving averages.  Suggest positive strength on 2 and 4 cross above the 8
 * and positive weakness on 2 below 8.  Suggest negative strength on 2 and 4 cross below 8 and neg-
 * ative weakness on 2 above 8. 
 * 
 * Does not issue trades, just trade suggestions.
 * 
 * Improvements to this simple proof-of-concept strategy and associated tools are strongly encourag-
 * ed.  Simply fork and go on github.com
 * 
 * http://github.com/sfrancolla/openstrategies-org
 *
 * @author sfrancolla@gmail.com
 */
public class RealTimeMovingAverages extends DelegatorStrategy {

   public static final Logger logger = Logger.getLogger(RealTimeMovingAverages.class);

   public static final String[] SYMBOLS = {"zzz"}; //only 1 symbol works in this proof-of-concept.
   public static final String[] CEP_QUERY = EPL.MA_2_4_8;
   public static final int MIN_ITERATIONS = 8;

   private static final BigDecimal POCCONFIDENCE = new BigDecimal(0);
   private static final String POCIDENTIFIER = "<suggestion-idetifier>";

   public RealTimeMovingAverages() throws ClientInitException {
      requestProcessedMarketData(MarketDataRequest.newRequest().withSymbols(SYMBOLS).fromProvider(EPL.MARKET_DATA_PROVIDER).withContent(MarketDataRequest.Content.TOP_OF_BOOK), CEP_QUERY, EPL.CEP_PROVIDER);
      addDelegate(new RealTimeMovingAveragesDelegate());
   }

   private static class RealTimeMovingAveragesDelegate implements OtherDelegate {

      private int iteration = 0;

      private boolean isBuy = false;
      private boolean isSellShort = false;

      @Override
      public void onOther(DelegatorStrategy sender, Object message) {
         if (message instanceof Map) {
            Data currData = new Data((Map)message);
            if (currData.ma4.compareTo(currData.ma8) > 0 && !isBuy) {
               isBuy = true;
               String comment = "Buy - Ma-4 up-cross of ma-8.";
               if (iteration >= MIN_ITERATIONS) {
                  OrderSingle order = new OrderBuilder().withSymbol(currData.symbol).withSide(Side.Buy).withPrice(new BigDecimal(currData.price)).build();
                  sender.getFramework().suggestTrade(order, POCCONFIDENCE, POCIDENTIFIER);
                  logger.info(comment);
               }
            } else if (currData.ma2.compareTo(currData.ma4) < 0 && isBuy) {
               isBuy = false;
               String comment = "Sell - Ma-2 down-cross of ma-4.";
               if (iteration >= MIN_ITERATIONS) {
                  OrderSingle order = new OrderBuilder().withSymbol(currData.symbol).withSide(Side.Sell).withPrice(new BigDecimal(currData.price)).build();
                  sender.getFramework().suggestTrade(order, POCCONFIDENCE, POCIDENTIFIER);
                  logger.info(comment);
               }
            }
            if (currData.ma4.compareTo(currData.ma8) < 0 && !isSellShort) {
               isSellShort = true;
               String comment = "SellShort - Ma-4 down-cross of ma-8.";
               if (iteration >= MIN_ITERATIONS) {
                  OrderSingle order = new OrderBuilder().withSymbol(currData.symbol).withSide(Side.SellShort).withPrice(new BigDecimal(currData.price)).build();
                  sender.getFramework().suggestTrade(order, POCCONFIDENCE, POCIDENTIFIER);
                  logger.info(comment);
               }
            } else if (currData.ma2.compareTo(currData.ma4) > 0 && isSellShort) {
               isSellShort = false;
               String comment = "BuyToCover - Ma-2 up-cross of ma-4.";
               if (iteration >= MIN_ITERATIONS) {
                  OrderSingle order = new OrderBuilder().withSymbol(currData.symbol).withSide(Side.Buy).withPrice(new BigDecimal(currData.price)).build();
                  sender.getFramework().suggestTrade(order, POCCONFIDENCE, POCIDENTIFIER);
                  logger.info(comment);
               }
            }
            if (iteration < MIN_ITERATIONS) {
               iteration++;
            }
         }
      }

      /**
       * Delegate to translate the map.
       */
      private static class Data {
         String symbol;
         Double price;
         Double ma2;
         Double ma4;
         Double ma8;

         public Data(Map map) {
            symbol = (String)map.get("symbol");
            price = (Double)map.get("price");
            ma2 = (Double)map.get("ma2");
            ma4 = (Double)map.get("ma4");
            ma8 = (Double)map.get("ma8");
         }
      }
   }

}
