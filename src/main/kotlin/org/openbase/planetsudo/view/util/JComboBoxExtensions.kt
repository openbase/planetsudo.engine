package org.openbase.planetsudo.view.util

import javax.swing.JComboBox

@Suppress("UNCHECKED_CAST")
fun <T> JComboBox<T>.getSelection(): T? = if (itemCount == 0) null else selectedItem as? T

fun <T> JComboBox<T>.getItems(): List<T> = (0..itemCount).map { getItemAt(it) }
