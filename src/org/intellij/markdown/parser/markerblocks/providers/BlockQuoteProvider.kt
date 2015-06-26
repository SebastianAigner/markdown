package org.intellij.markdown.parser.markerblocks.providers

import org.intellij.markdown.parser.LookaheadText
import org.intellij.markdown.parser.MarkerProcessor
import org.intellij.markdown.parser.ProductionHolder
import org.intellij.markdown.parser.markerblocks.MarkerBlock
import org.intellij.markdown.parser.markerblocks.MarkerBlockProvider
import org.intellij.markdown.parser.markerblocks.impl.BlockQuoteMarkerBlock

public class BlockQuoteProvider : MarkerBlockProvider<MarkerProcessor.StateInfo> {
    override fun createMarkerBlocks(pos: LookaheadText.Position,
                                   productionHolder: ProductionHolder,
                                   stateInfo: MarkerProcessor.StateInfo): List<MarkerBlock> {
        val currentConstraints = stateInfo.currentConstraints
        val nextConstraints = currentConstraints.addModifierIfNeeded(pos)
        if (nextConstraints != null && nextConstraints?.getLastType() == '>') {
            return listOf(BlockQuoteMarkerBlock(nextConstraints, productionHolder.mark()))
        } else {
            return emptyList()
        }
    }

    override fun interruptsParagraph(pos: LookaheadText.Position): Boolean {
        // Actually, blockquote may interrupt a paragraph, but we have MarkdownConstraints for these cases
        return false
    }
}