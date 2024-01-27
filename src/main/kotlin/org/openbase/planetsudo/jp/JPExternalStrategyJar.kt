/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.jp

import org.openbase.jps.core.JPService
import org.openbase.jps.exception.JPNotAvailableException
import org.openbase.jps.preset.AbstractJPFile
import org.openbase.jps.preset.JPPrefix
import org.openbase.jps.tools.FileHandler
import java.io.File

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class JPExternalStrategyJar :
    AbstractJPFile(COMMAND_IDENTIFIERS, FileHandler.ExistenceHandling.CanExist, FileHandler.AutoMode.Off) {
    override fun getDescription(): String {
        return "Set the external strategy jar."
    }

    @Throws(JPNotAvailableException::class)
    override fun getPropertyDefaultValue(): File {
        return File(JPService.getProperty(JPPrefix::class.java).value.absolutePath + "/ext/Strategy.jar")
    }

    companion object {
        val COMMAND_IDENTIFIERS: Array<String> = arrayOf("--buildTarget")
    }
}
