package dev.reactant.reactant.ui.event

import dev.reactant.reactant.ui.ViewInventoryContainerElement
import dev.reactant.reactant.ui.element.UIElement
import dev.reactant.reactant.ui.eventtarget.UIEventTarget

/**
 * The event which is happening on an element
 */
abstract class UIElementEvent(val target: UIElement) : UIEvent {

    var isPropagating: Boolean = true
        private set

    fun stopPropagation() {
        isPropagating = false
    }

    override fun propagateTo(eventTarget: UIEventTarget<UIEvent>) = eventTarget.event.onNext(this)

    fun propagateTo(element: UIElement) {
        element.event.onNext(this)
        if (!isPropagating) return
        when {
            element.parent != null -> propagateTo(element.parent!!)
            element is ViewInventoryContainerElement -> propagateTo(element.view)
        }
    }


}