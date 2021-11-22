/*
 * Copyright (c) 2019-2021 GeyserMC. http://geysermc.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * @author GeyserMC
 * @link https://github.com/GeyserMC/Geyser
 */

package org.geysermc.connector.inventory;

import com.github.steveice10.mc.protocol.data.game.window.WindowType;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import lombok.Getter;
import lombok.Setter;

/**
 * Used to determine if rename packets should be sent and stores
 * the expected level cost for AnvilInventoryUpdater
 */
@Getter @Setter
public class AnvilContainer extends Container {
    /**
     * Stores the level cost received as a window property from Java
     */
    private int javaLevelCost = 0;
    /**
     * A flag to specify whether javaLevelCost can be used as it can
     * be outdated or not sent at all.
     */
    private boolean useJavaLevelCost = false;

    /**
     * The new name of the item as received from Bedrock
     */
    private String newName = null;

    private GeyserItemStack lastInput = GeyserItemStack.EMPTY;
    private GeyserItemStack lastMaterial = GeyserItemStack.EMPTY;

    private int lastTargetSlot = -1;

    public AnvilContainer(String title, int id, int size, WindowType windowType, PlayerInventory playerInventory) {
        super(title, id, size, windowType, playerInventory);
    }

    public GeyserItemStack getInput() {
        return getItem(0);
    }

    public GeyserItemStack getMaterial() {
        return getItem(1);
    }

    public GeyserItemStack getResult() {
        return getItem(2);
    }
}
