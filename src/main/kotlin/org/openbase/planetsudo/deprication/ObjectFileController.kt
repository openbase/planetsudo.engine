/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openbase.planetsudo.deprication

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.*

/**
 *
 * @author [Divine Threepwood](mailto:divine@openbase.org)
 */
class ObjectFileController<O>(private val fileURI: String) {
    fun writeObject(`object`: O): Boolean { // TODO: Serialize with json and support spar Data.
        logger.debug("write to $fileURI")
        synchronized(this) {
            var fileOutputStream: FileOutputStream? = null
            try {
                fileOutputStream = FileOutputStream(fileURI)
                val objectOutputStream = ObjectOutputStream(fileOutputStream)
                objectOutputStream.writeObject(`object`)
            } catch (ex: IOException) {
                logger.error("Could not write $`object` in $fileURI", ex)
                return false
            } finally {
                try {
                    fileOutputStream?.close()
                } catch (ex: Exception) {
                    logger.warn("Could not close stream to $fileURI", ex)
                    return false
                }
            }
            return true
        }
    }

    @Throws(IOException::class, ClassNotFoundException::class, FileNotFoundException::class)
    fun readObject(): O? {
        logger.debug("Read $fileURI")

        synchronized(this) {
            var fileInputStream: FileInputStream? = null
            var `object`: O? = null
            try {
                fileInputStream = FileInputStream(fileURI)

                val objectInputStream = ObjectInputStream(fileInputStream)
                `object` = objectInputStream.readObject() as O
                logger.info("Read $fileURI")
            } catch (ex: FileNotFoundException) {
                throw IOException("Could not found File[$fileURI]", ex)
            } catch (ex: ClassNotFoundException) {
                deleteDataFile()
                throw IOException("File[$fileURI] not compatible with progam version!", ex)
            } catch (ex: InvalidClassException) {
                deleteDataFile()
                throw IOException("File[$fileURI] not compatible with progam version! Class struct changed!", ex)
            } catch (ex: Exception) {
                throw IOException("Could not read File[$fileURI]", ex)
            } finally {
                try {
                    fileInputStream?.close()
                } catch (ex: Exception) {
                    throw IOException("Could not close fileStream " + fileURI + " :" + ex.message)
                }
            }
            return `object`
        }
    }

    private fun deleteDataFile() {
        logger.warn("Initiate delete of $fileURI")
        try {
            File(fileURI).delete()
        } catch (ex: Exception) {
            logger.warn("Could not delete $fileURI", ex)
        }
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ObjectFileController::class.java)
    }
}
