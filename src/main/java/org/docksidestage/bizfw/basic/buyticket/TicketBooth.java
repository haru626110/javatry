/*
 * Copyright 2019-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.bizfw.basic.buyticket;

/**
 * @author jflute
 */
public class TicketBooth {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final int MAX_QUANTITY = 10;
    private static final int ONE_DAY_PRICE = 7400; // when 2019/06/15
    private static final int TWO_DAY_PRICE = 13200;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private int quantity = MAX_QUANTITY;
    private Integer salesProceeds;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public TicketBooth() {
    }

    // ===================================================================================
    //                                                                          Buy Ticket
    //                                                                          ==========
    public void buyOneDayPassport(int handedMoney) {
        if (quantity <= 0) {//チケット枚数が0なら売り切れ
            throw new TicketSoldOutException("Sold out");
        }
        if (handedMoney >= ONE_DAY_PRICE) {//handedMoneyがONE_DAY_PRICE以上ならチケットを1枚購入
            --quantity;
        }
        if (handedMoney < ONE_DAY_PRICE) {//handedMoneyがONE_DAY_PRICE未満の場合、アナウンス
            throw new TicketShortMoneyException("Short money: " + handedMoney);
        }
        if (salesProceeds != null) {
            salesProceeds = salesProceeds + handedMoney;
        } else {
            salesProceeds = ONE_DAY_PRICE;//売上加算
        }
    }

    public void buyTwoDayPassport(int handedMoney) {
        if (quantity <= 0) {//チケット枚数が0なら売り切れ
            throw new TicketSoldOutException("Sold out");
        }
        if (handedMoney >= TWO_DAY_PRICE) {//handedMoneyがONE_DAY_PRICE以上ならチケットを1枚購入
            --quantity;
        }
        if (handedMoney > TWO_DAY_PRICE) {
            int change = handedMoney - TWO_DAY_PRICE;
            return change;
        }
        if (handedMoney < TWO_DAY_PRICE) {//handedMoneyがONE_DAY_PRICE未満の場合、アナウンス
            throw new TicketShortMoneyException("Short money: " + handedMoney);
        }
        if (salesProceeds != null) {
            salesProceeds = salesProceeds + handedMoney;
        } else {
            salesProceeds = TWO_DAY_PRICE;//売上加算
        }
    }

    public static class TicketSoldOutException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketSoldOutException(String msg) {
            super(msg);
        }
    }

    public static class TicketShortMoneyException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketShortMoneyException(String msg) {
            super(msg);
        }
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getQuantity() {
        return quantity;
    }

    public Integer getSalesProceeds() {
        return salesProceeds;
    }
}
