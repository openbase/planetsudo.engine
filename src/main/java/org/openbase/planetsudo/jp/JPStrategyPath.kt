/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.jp

import org.openbase.jps.core.JPService
import org.openbase.jps.exception.JPNotAvailableException
import org.openbase.jps.preset.AbstractJPDirectory
import org.openbase.jps.preset.JPPrefix
import org.openbase.jps.tools.FileHandler
import java.io.File

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class JPStrategyPath :
    AbstractJPDirectory(COMMAND_IDENTIFIERS, FileHandler.ExistenceHandling.CanExist, FileHandler.AutoMode.On) {
    override fun getDescription(): String {
        return "Set the StrategyPath."
    }

    @Throws(JPNotAvailableException::class)
    override fun getPropertyDefaultValue(): File {
        return File(JPService.getProperty(JPPrefix::class.java).value.absolutePath + "/target/classes/org/openbase/planetsudo/game/strategy")
    }

    companion object {
        val COMMAND_IDENTIFIERS: Array<String> = arrayOf("-s", "--strategy")
    }
}
