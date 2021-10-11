/*
 * MIT License
 *
 * Copyright (c) 2021 Fisher2911
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.fisher2911.fishcore.message;

public class Messages {

    public static final Message NOT_ENOUGH_ITEMS = new Message("not-enough-items",
            "You do not have enough items");
    public static final Message NOT_ENOUGH_MONEY = new Message("not-enough-money",
            "You do not have enough money");

    public static final Message INVALID_ITEM = new Message("",
            "<red>" + Placeholder.ITEM + " is an invalid " +
                    Placeholder.TYPE + " in " + Placeholder.FILE + "</red>");
    public static final Message ITEM_NOT_FOUND = new Message("",
            "<red>" + Placeholder.TYPE + " could not be found " +
                    " in " + Placeholder.FILE + "</red>");
    public static final Message NOT_A_NUMBER = new Message("",
            "<red>" + Placeholder.ITEM + " is not a valid number " +
                    " in " + Placeholder.FILE + "</red>");

}
