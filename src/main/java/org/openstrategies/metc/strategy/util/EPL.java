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

/**
 * Repository for exploratory EPL queries.  
 */
public interface EPL {
   

   public static final String MARKET_DATA_PROVIDER = "marketcetera";
   public static final String CEP_PROVIDER = "esper";
   public static final String CEP_ALIAS = "bid";  //only bid events are propogated in the metc simulator. 

   public static final String TEST1 = "select b.symbolAsString as symbol from bid.win:length(2) b"; 
   public static final String TEST2 = "select b.symbolAsString as symbol, cast(b.price, double) as bidPrice from bid.win:length(2) b"; 
   public static final String TEST3 = "select b.symbolAsString as symbol, cast(b.price, double) as bidPrice from bid.win:length(2) b, ask.std:lastevent() a"; 
   public static final String TEST4 = "select b.symbolAsString as symbol, cast(b.price, double) as bidPrice, avg(cast(b.price, double)) as bidMa from bid.win:length(2) b, ask.std:lastevent() a"; 
   public static final String MA_2_30s = "select b.symbolAsString as symbol, cast(b.price, double) as price, avg(cast(b.price, double)) as avg from bid.win:time_batch(30 sec) b"; 
   public static final String MA_4 = "select avg(java.lang.Double.valueOf(cast(t.price, double))) as ma from map.win:length(4)";
   public static final String MA_8 = "select avg(java.lang.Double.valueOf(cast(t.price, double))) as ma from map.win:length(8)"; 

   /**
    * With group by on price in 2nd query, mult lines outputted.
    */
   public static final String[] MA_2_4_8_ERRONEOUS = {
      //periods
      "insert into LastPrice "+ 
      "  select "+ CEP_ALIAS +".symbolAsString as symbol, cast("+ CEP_ALIAS +".price, double) as price "+ 
      "  from "+ CEP_ALIAS +" "+ 
      "  output last every 5 seconds "+ 
      "",
      
      //moving averages
      "select last2.symbol as symbol, last1.price as price, avg(last2.price) as ma2, avg(last4.price) as ma4, avg(last8.price) as ma8 "+
      "from LastPrice.std:lastevent() last1, LastPrice.win:length(2) last2, LastPrice.win:length(4) last4, LastPrice.win:length(8) last8 "+
      "group by last2.symbol, last1.price "+
      "",
      
   };
   
   public static final String[] MA_2_4_8 = {
      //periods
      "insert into LastPrice "+ 
      "  select "+ CEP_ALIAS +".symbolAsString as symbol, cast("+ CEP_ALIAS +".price, double) as price "+ 
      "  from "+ CEP_ALIAS +" "+ 
      "  output last every 5 seconds "+ 
      "",
      
      //moving averages
      "select last2.symbol as symbol, avg(last1.price) as price, avg(last2.price) as ma2, avg(last4.price) as ma4, avg(last8.price) as ma8 "+
      "from LastPrice.win:length(1) last1, LastPrice.win:length(2) last2, LastPrice.win:length(4) last4, LastPrice.win:length(8) last8 "+
      "group by last2.symbol "+
      "",
      
   };
   
}
