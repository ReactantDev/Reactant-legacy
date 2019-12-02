package dev.reactant.reactant.ui.kits

import dev.reactant.reactant.ui.editing.ReactantUIElementEditing
import dev.reactant.reactant.ui.element.ElementDisplay
import dev.reactant.reactant.ui.element.UIElement
import dev.reactant.reactant.ui.element.UIElementName
import dev.reactant.reactant.ui.element.type.sizing.ResizableElement
import dev.reactant.reactant.ui.element.type.sizing.ResizableElementsEditing
import dev.reactant.reactant.ui.kits.container.ReactantUIContainerElement
import dev.reactant.reactant.ui.kits.container.ReactantUIContainerElementEditing
import dev.reactant.reactant.utils.content.item.createItemStack
import dev.reactant.reactant.utils.delegation.MutablePropertyDelegate
import org.bukkit.inventory.ItemStack

@UIElementName("div")
open class ReactantUIDivElement : ReactantUIContainerElement("div"), ResizableElement {
    override fun edit() = ReactantUIDivElementEditing(this)

    override var height: Int = UIElement.WRAP_CONTENT
    override var width: Int = UIElement.MATCH_PARENT
    override var display = ElementDisplay.BLOCK

    var fillPattern: (relativeX: Int, relativeY: Int) -> ItemStack = { _, _ -> createItemStack() }

    override fun getBackgroundItemStack(x: Int, y: Int): ItemStack = fillPattern(x, y)
}

open class ReactantUIDivElementEditing<out T : ReactantUIDivElement>(element: T)
    : ReactantUIContainerElementEditing<T>(element), ResizableElementsEditing<T> {
    var overflowHidden by MutablePropertyDelegate(this.element::overflowHidden)
    var fillPattern by MutablePropertyDelegate(this.element::fillPattern)
    fun fill(itemStack: ItemStack) {
        fillPattern = { _, _ -> itemStack.clone() }
    }
}

fun ReactantUIElementEditing<UIElement>.div(creation: ReactantUIDivElementEditing<ReactantUIDivElement>.() -> Unit) {
    element.children.add(ReactantUIDivElement().also { ReactantUIDivElementEditing(it).apply(creation) })
}
