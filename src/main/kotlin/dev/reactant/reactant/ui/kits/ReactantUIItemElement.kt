package dev.reactant.reactant.ui.kits

import dev.reactant.reactant.ui.editing.ReactantUIElementEditing
import dev.reactant.reactant.ui.element.ReactantUIElement
import dev.reactant.reactant.ui.element.UIElement
import dev.reactant.reactant.ui.element.UIElementName
import dev.reactant.reactant.ui.rendering.ElementSlot
import dev.reactant.reactant.ui.rendering.RenderedItems
import dev.reactant.reactant.utils.content.item.createItemStack
import dev.reactant.reactant.utils.delegation.MutablePropertyDelegate
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

@UIElementName("item")
open class ReactantUIItemElement : ReactantUIElement("item") {
    override fun edit() = ReactantUIItemElementEditing(this)
    var displayItem: ItemStack = ItemStack(Material.AIR)

    override fun render(parentFreeSpaceWidth: Int, parentFreeSpaceHeight: Int): RenderedItems =
            RenderedItems(hashMapOf((0 to 0) to ElementSlot(this, displayItem))).addMargin(this, parentFreeSpaceWidth, parentFreeSpaceHeight)

    override val width: Int = 1
    override val height: Int = 1
}

open class ReactantUIItemElementEditing<out T : ReactantUIItemElement>(element: T)
    : ReactantUIElementEditing<T>(element) {
    var displayItem: ItemStack by MutablePropertyDelegate(this.element::displayItem)
}


fun ReactantUIElementEditing<UIElement>.item(displayItem: ItemStack = createItemStack(),
                                             creation: ReactantUIItemElementEditing<ReactantUIItemElement>.() -> Unit = {}) {
    element.children.add(ReactantUIItemElement()
            .also {
                ReactantUIItemElementEditing(it).also { creation ->
                    creation.displayItem = displayItem
                }.apply(creation)
            })
}
