package org.intellij.markdown.parser.markerblocks.providers

import org.intellij.markdown.parser.LookaheadText
import org.intellij.markdown.parser.MarkerProcessor
import org.intellij.markdown.parser.ProductionHolder
import org.intellij.markdown.parser.markerblocks.MarkdownParserUtil
import org.intellij.markdown.parser.markerblocks.MarkerBlock
import org.intellij.markdown.parser.markerblocks.MarkerBlockProvider
import org.intellij.markdown.parser.markerblocks.impl.CodeBlockMarkerBlock

public class CodeBlockProvider : MarkerBlockProvider<MarkerProcessor.StateInfo> {
    override fun createMarkerBlocks(pos: LookaheadText.Position,
                                   productionHolder: ProductionHolder,
                                   stateInfo: MarkerProcessor.StateInfo): List<MarkerBlock> {
        if (stateInfo.paragraphBlock != null) {
            return emptyList()
        }

        val charsToNonWhitespace = pos.charsToNonWhitespace()
            ?: return emptyList()
        val blockStart = pos.nextPosition(charsToNonWhitespace)
            ?: return emptyList()

        if (MarkdownParserUtil.hasCodeBlockIndent(blockStart, stateInfo.currentConstraints)) {
            return listOf(CodeBlockMarkerBlock(stateInfo.currentConstraints, productionHolder.mark()))
        } else {
            return emptyList()
        }
    }

    override fun interruptsParagraph(pos: LookaheadText.Position): Boolean {
        return false
    }

}