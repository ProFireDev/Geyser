/*
 * Copyright (c) 2019-2022 GeyserMC. http://geysermc.org
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

package org.geysermc.geyser.translator.inventory.item;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.ItemStack;
import com.github.steveice10.opennbt.tag.builtin.ByteTag;
import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.Tag;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import org.geysermc.geyser.network.MinecraftProtocol;
import org.geysermc.geyser.registry.Registries;
import org.geysermc.geyser.registry.type.ItemMapping;
import org.geysermc.geyser.registry.type.ItemMappings;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ItemRemapper
public class CompassTranslator extends ItemTranslator {

    @Override
    protected ItemData.Builder translateToBedrock(ItemStack itemStack, ItemMapping mapping, ItemMappings mappings) {
        if (isLodestoneCompass(itemStack.getNbt())) {
            // NBT will be translated in nbt/LodestoneCompassTranslator if applicable
            return super.translateToBedrock(itemStack, mappings.getLodestoneCompass(), mappings);
        }
        return super.translateToBedrock(itemStack, mapping, mappings);
    }

    @Override
    protected ItemMapping getItemMapping(int javaId, CompoundTag nbt, ItemMappings mappings) {
        if (isLodestoneCompass(nbt)) {
            return mappings.getLodestoneCompass();
        }
        return super.getItemMapping(javaId, nbt, mappings);
    }

    private boolean isLodestoneCompass(CompoundTag nbt) {
        if (nbt != null) {
            Tag lodestoneTag = nbt.get("LodestoneTracked");
            return lodestoneTag instanceof ByteTag;
        }
        return false;
    }

    @Override
    public ItemStack translateToJava(ItemData itemData, ItemMapping mapping, ItemMappings mappings) {
        if (mapping.getBedrockIdentifier().equals("minecraft:lodestone_compass")) {
            // Revert the entry back to the compass
            mapping = mappings.getStoredItems().compass();
        }

        return super.translateToJava(itemData, mapping, mappings);
    }

    @Override
    public List<ItemMapping> getAppliedItems() {
        return Arrays.stream(Registries.ITEMS.forVersion(MinecraftProtocol.DEFAULT_BEDROCK_CODEC.getProtocolVersion())
                        .getItems())
                .filter(entry -> entry.getJavaIdentifier().endsWith("compass"))
                .collect(Collectors.toList());
    }
}
